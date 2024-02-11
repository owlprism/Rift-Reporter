package com.example.lol_project

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.lol_project.models.ChampionDetails
import com.example.lol_project.models.MasteryItem
import com.example.lol_project.models.PostModelProfile
import com.example.lol_project.models.Utilisateur
import com.example.lol_project.utils.Constants
import com.squareup.picasso.Picasso


class ProfileActivity : AppCompatActivity(){
    @RequiresApi(Build.VERSION_CODES.N) // for Html.fromHtml in displayChampionDetails
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        //Action retour
        val actionBar = supportActionBar
        actionBar !!.title = "Profil"
        actionBar.setDisplayHomeAsUpEnabled(true)

        //Récupération du nom utilisateur
        val intent = intent
        if (intent.hasExtra("utilisateur")) {
            val utilisateur = intent.getSerializableExtra("utilisateur") as Utilisateur

            // Affichage du nom d'utilisateur
            val textViewUsername = findViewById<TextView>(R.id.textViewUsername)
            textViewUsername.text = "${utilisateur.nom}"
        }

        //Import de MenuActivity
        val profileDetails = intent.getSerializableExtra("profileDetails") as? PostModelProfile
        val masteryDetailsList = intent.getSerializableExtra("masteryDetails") as? Array<MasteryItem>
        val championDetailsList = intent.getSerializableExtra("championMasteryDetails") as? ArrayList<ChampionDetails>

        Log.d("ProfileActivity", "Print out : $championDetailsList")

        // Récupérer les vues du layout
        val imageProfileIcon: ImageView = findViewById(R.id.menuImage)
        val textName: TextView = findViewById(R.id.textViewUsername)
        val textSummonerLevel: TextView = findViewById(R.id.ProfilNiveau)

        // Afficher les informations dans les vues
        Picasso.get().load(getProfileImageUrl(profileDetails?.profileIconId)).into(imageProfileIcon)
        textName.text = profileDetails?.name
        textSummonerLevel.text = profileDetails?.summonerLevel.toString()

        //Test log
        /*Log.e("ProfileActivity", "PROFILE")
        Log.d("ProfileActivity", "textName ${profileDetails?.name}")
        Log.d("ProfileActivity", "Lvl${profileDetails?.summonerLevel}")*/

        displayMasteryDetails(masteryDetailsList, championDetailsList)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun displayMasteryDetails(championDetailsList: Array<MasteryItem>?, championDetailsArrayList: ArrayList<ChampionDetails>?) {
        val container = findViewById<LinearLayout>(R.id.CardChampionMastery)

        if (championDetailsList != null && championDetailsArrayList != null) {
            for (championDetails in championDetailsList) {

                // on fait correspondre les clés champion dans chaque requête pour afficher la card du bon champion
                val correspondingChampionDetails = championDetailsArrayList.find { it.key == championDetails.championId.toString() }

                if (correspondingChampionDetails != null) {

                    val cardView = LayoutInflater.from(this)
                        .inflate(R.layout.card_champion_mastery, container, false)

                    val championLevel = cardView.findViewById<TextView>(R.id.ChampionLevel)
                    val championPoints = cardView.findViewById<TextView>(R.id.PointsUntilNextLevel)

                    val formattedChampionLevel = "Level : <b>${championDetails.championLevel}</b>"
                    val formattedChampionPoints = "Points Until Next Level : <b>${championDetails.championPointsUntilNextLevel}</b>"

                    championLevel.text = Html.fromHtml(formattedChampionLevel, Html.FROM_HTML_MODE_COMPACT)


                    if(championDetails.championPointsUntilNextLevel != 0){
                        championPoints.text = Html.fromHtml(formattedChampionPoints, Html.FROM_HTML_MODE_COMPACT)
                    } else {
                        championPoints.text = "Niveau maximal atteint"
                    }

                    val championImageView = cardView.findViewById<ImageView>(R.id.ImageChampion)
                    val championName = cardView.findViewById<TextView>(R.id.ChampionName)

                    championName.text = correspondingChampionDetails.name

                    loadImageForChampion(championImageView, correspondingChampionDetails)

                    container.addView(cardView)
                }
            }
        }
    }

    private fun loadImageForChampion(imageView: ImageView, championDetails: ChampionDetails) {
        // Association de l'ImageView avec championDetails
        imageView.tag = championDetails
        //on utilise championDetails.id et non pas championDetails.name pour éviter les espaces, accents, qui ne fonctionnent pas dans la requête
        Picasso.get().load(getChampionImageUrl(championDetails.id)).into(imageView)
    }

    private fun getProfileImageUrl(ProfileIconId: Int?): String {
        return "${Constants.ProfilePNGURL}$ProfileIconId.png"
    }

    private fun getChampionImageUrl(championName: String): String {
        return "${Constants.ChampionPNGURL}${championName}.png"
    }
}



