package com.example.lol_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.lol_project.com.example.lol_project.UtilisateurSingleton
import com.example.lol_project.com.example.lol_project.UtilisateurSingleton.updateUtilisateur
import com.example.lol_project.models.ChampionDetails
import com.example.lol_project.models.PostModelGame
import com.example.lol_project.models.PostModelMultiplesGames
import java.io.Serializable

class MenuActivity : AppCompatActivity() {

    private val utilisateur = UtilisateurSingleton.utilisateur
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        //Action retour
        val actionBar = supportActionBar
        actionBar !!.title = "Menu"

        //Récupération des 4 boutons pour redirection
        val home_button = findViewById<Button>(R.id.buttonAccueil)
        val profil_button = findViewById<Button>(R.id.buttonProfil)
        val rota_button = findViewById<Button>(R.id.buttonRotation)
        val history_button = findViewById<Button>(R.id.buttonHistory)

        // Désactiver/Réactive les boutons de base
        updateButtonState()
        if (utilisateur != null) {
            updateUtilisateur(utilisateur)
        }

        // Transport home
        home_button.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // Transport profil
        profil_button.setOnClickListener {
            Log.d("MenuActivity", "Profile Button Clicked !")
            Log.d("MenuActivity", "User : ${utilisateur?.nom}")

            if (utilisateur != null) {
                APIFunctions.getProfileDetails(
                    utilisateur.nom,
                    onSuccess = { profileDetails ->
                        Log.d("ProfileActivity", "Profile Details: $profileDetails")

                        val intent = Intent(this, ProfileActivity::class.java)
                        intent.putExtra("profileDetails", profileDetails)

                        APIFunctions.getChampionMastery(
                            profileDetails.puuid,
                            onSuccess = { masteryDetails ->
                                Log.d("MenuActivity", "Mastery Details: $masteryDetails")

                                intent.putExtra("utilisateur", utilisateur)
                                intent.putExtra("masteryDetails", masteryDetails?.toTypedArray())
                                
                                // iteration dans la liste des champions Top pour récupérer les infos des champions avec leur id
                                val championDetailsList = mutableListOf<ChampionDetails>()
                                var championDetailsFetchedCount = 0
                                //val championDetailsArrayList = ArrayList(championDetailsList)
                                for(masteryChampion in masteryDetails.orEmpty()){
                                    val championId = masteryChampion.championId

                                    APIFunctions.getChampionDetails(
                                        championId.toString(),
                                        onSuccess = { championDetails ->
                                            championDetailsList.add(championDetails)
                                            Log.d("MenuActivity", "CHAMPION DETAILS : $championDetails")
                                            Log.d("MenuActivity", "CHAMPION LIst : $championDetailsList")

                                            // Check if all champion details are fetched
                                            championDetailsFetchedCount++
                                            if (championDetailsFetchedCount == masteryDetails?.size) {
                                                // All details fetched, now proceed with the intent
                                                Log.d("MenuActivity", "All champion details fetched. List: $championDetailsList")

                                                intent.putExtra("championMasteryDetails", ArrayList(championDetailsList))
                                                startActivity(intent)
                                            }
                                        },
                                        onError = { errorMessage ->
                                            Toast.makeText(this@MenuActivity, errorMessage, Toast.LENGTH_SHORT).show()
                                        }
                                    )
                                    Log.d("MenuActivity","test list : $championDetailsList")
                                }
                                //Log.d("MenuActivity","test list finale : $championDetailsList")
                                //intent.putExtra("championMasteryDetails", ArrayList(championDetailsList))
                                startActivity(intent)
                            },
                            onError = { errorMessage ->
                                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                            }
                        )
                    },
                    onError = { errorMessage ->
                        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                    }
                )
            } else {
                Toast.makeText(this, "Please enter a summoner name", Toast.LENGTH_SHORT).show()
            }
        }

        // Transport history_button
        history_button.setOnClickListener {
            Log.d("MenuActivity", "History Button Clicked !")
            Log.d("MenuActivity", "User : ${utilisateur?.nom}")

            if (utilisateur != null) {
                APIFunctions.getProfileDetails(
                    utilisateur.nom,
                    onSuccess = { profileDetails ->
                        Log.d("MenuActivity", "Profile Details: $profileDetails")
                        val intent = Intent(this, HistoryActivity::class.java)

                        APIFunctions.getMultiplesGames(
                            profileDetails.puuid,
                            onSuccess = { matchIds ->
                                matchIds?.let { it ->
                                    Log.d("MenuActivity", "Ids : $it")
                                    val firstThreeMatchIds = it.take(6 )// réduction de la liste
                                    val totalGamesToFetch = firstThreeMatchIds.size
                                    var gamesFetched = 0

                                    // Liste pour stocker les détails de tous les matchs
                                    val matchDetailsList = mutableListOf<PostModelGame>()

                                    for (eachGame in firstThreeMatchIds) {
                                        Log.d("MenuActivity", "test game it : $eachGame")
                                        APIFunctions.getGameDetails(
                                            eachGame,
                                            onSuccess = { GameDetails ->
                                                if (GameDetails != null) {
                                                    matchDetailsList.add(GameDetails)
                                                    //Log.d("MenuActivity", "GameDetails : $GameDetails")
                                                    //Log.d("MenuActivity", "test utilisateur : ${utilisateur}")
                                                    Log.d("MenuActivity", "test list games : $matchDetailsList")
                                                    gamesFetched++

                                                    if (gamesFetched == totalGamesToFetch) {
                                                        Log.d("MenuActivity", "test hifleds : ${GameDetails.info}")
                                                        Log.d("MenuActivity", "test hiflefefeds : ${GameDetails.info.participants}")
                                                        intent.putExtra("gameDetails", ArrayList(matchDetailsList))
                                                        startActivity(intent)
                                                    }
                                                }
                                            },
                                            onError = { errorMessage ->
                                                Toast.makeText(this@MenuActivity, errorMessage, Toast.LENGTH_SHORT).show()
                                                Log.e("MenuActivity", "ERROR getGameDetails")
                                            }
                                        )
                                    }
                                    Log.d("MenuActivity", "test list games : $matchDetailsList")
                                }
                            },
                            onError = { errorMessage ->
                                Toast.makeText(this@MenuActivity, errorMessage, Toast.LENGTH_SHORT).show()
                                Log.e("MenuActivity", "ERROR getMultiplesGames")
                            }
                        )

                    },
                    onError = { errorMessage ->
                        Toast.makeText(this@MenuActivity, errorMessage, Toast.LENGTH_SHORT).show()
                        Log.e("MenuActivity", "ERROR getProfileDetails")
                    }
                )
            }
        }

        // Transport rota
        rota_button.setOnClickListener{
            Toast.makeText(this@MenuActivity, "Loading...", Toast.LENGTH_SHORT).show()
            Log.d("MenuActivity", "Rotation button clicked")
            APIFunctions.getChampionRotations(
                this@MenuActivity,
                onSuccess = { freeChampionIds ->
                    // For demonstration, assume all champions are selected
                    Log.d("MenuActivity", "onSuccess")
                    if (freeChampionIds.isNotEmpty()) {

                        val championDetailsList = mutableListOf<ChampionDetails>()

                        for (championId in freeChampionIds) {
                            APIFunctions.getChampionDetails(
                                championId.toString(),
                                onSuccess = { championDetails ->
                                    championDetailsList.add(championDetails)
                                    // Check if details for all champions are fetched
                                    if (championDetailsList.size == freeChampionIds.size) {
                                        displayChampionDetails(championDetailsList)
                                    }
                                },
                                onError = { errorMessage ->
                                    Toast.makeText(this@MenuActivity, errorMessage, Toast.LENGTH_SHORT).show()
                                }
                            )
                        }
                    } else {
                        Toast.makeText(this@MenuActivity, "No free champions available", Toast.LENGTH_SHORT).show()
                    }
                },
                onError = { errorMessage ->
                    Toast.makeText(this@MenuActivity, errorMessage, Toast.LENGTH_SHORT).show()
                }
            )
        }
    }

    private fun updateButtonState() {
        val buttonsToDisable = listOf(
            findViewById<Button>(R.id.buttonProfil),
            findViewById<Button>(R.id.buttonHistory)
            //Laisse up la rotation de base ?
            //findViewById<Button>(R.id.buttonRotation)
        )

        for (button in buttonsToDisable) {
            button.isEnabled = utilisateur != null && utilisateur!!.nom.isNotBlank()
        }
    }

    private fun displayChampionDetails(championDetailsList: List<ChampionDetails>) {
        val intent = Intent(this@MenuActivity, ChampionRotationActivity::class.java)
        intent.putExtra("ChampionDetailsList", ArrayList(championDetailsList))
        startActivity(intent)
    }

}