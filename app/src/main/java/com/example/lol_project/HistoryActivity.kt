package com.example.lol_project

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.lol_project.com.example.lol_project.UtilisateurSingleton
import com.example.lol_project.com.example.lol_project.UtilisateurSingleton.utilisateur
import com.example.lol_project.models.PostModelGame
import com.example.lol_project.models.Utilisateur
import com.example.lol_project.utils.Constants
import com.squareup.picasso.Picasso

class HistoryActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        //Action retour
        val actionBar = supportActionBar
        actionBar !!.title = "Historique"
        actionBar.setDisplayHomeAsUpEnabled(true)

        Log.d("HistoryActivity", "Bienvenue sur History")

        val summonerRecherche = utilisateur?.nom
        val container = findViewById<LinearLayout>(R.id.gameDetailsContainer)

        if (intent.hasExtra("gameDetails")) {

            val matchDetailsList = intent.getSerializableExtra("gameDetails") as? ArrayList<PostModelGame>

            if (matchDetailsList != null) {
                for (matchDetails in matchDetailsList) {

                    val cardView = LayoutInflater.from(this).inflate(R.layout.card_game_layout, container, false)

                    val participantRecherche = matchDetails.info.participants.find {it.summonerName == summonerRecherche }

                    val victoryTextView = cardView.findViewById<TextView>(R.id.victoryTextView)
                    val scoreTextView = cardView.findViewById<TextView>(R.id.scoreTextView)
                    val championImageView = cardView.findViewById<ImageView>(R.id.championImageView)
                    val typeTextView = cardView.findViewById<TextView>(R.id.typeTextView)
                    val durationTextView = cardView.findViewById<TextView>(R.id.durationTextView)

                    if(participantRecherche != null){

                        if(participantRecherche?.win == true){
                            victoryTextView.text = "WIN"
                            victoryTextView.setTextColor(Color.GREEN)
                        } else {
                            victoryTextView.text = "DEFEAT"
                            victoryTextView.setTextColor(Color.RED)
                        }

                        val gameDurationInSeconds = matchDetails.info.gameDuration

                        val minutes = gameDurationInSeconds / 60
                        val seconds = gameDurationInSeconds % 60

                        val formattedDuration1 = String.format("%02d:%02d", minutes, seconds)
                        val formattedDuration2 = "Duration: <b>$formattedDuration1</b>"
                        durationTextView.text = Html.fromHtml(formattedDuration2, Html.FROM_HTML_MODE_COMPACT)

                        Picasso.get().load(getChampionImageUrl(participantRecherche.championName)).into(championImageView)
                        typeTextView.text = matchDetails.info.gameMode
                        scoreTextView.text = "${participantRecherche?.kills}/${participantRecherche?.deaths}/${participantRecherche?.assists}"
                    } else {
                        Log.d("HistoryActivity", "perso pas trouve")
                    }
                    container.addView(cardView)
                }
            } else {
                Log.e("HistoryActivity", "Les données de match ne peuvent pas être converties en PostModelGame")
            }
        } else {
            Log.e("HistoryActivity", "Aucun détail de match trouvé dans l'intent.")
        }
    }

    private fun getChampionImageUrl(championName: String): String {
        return "${Constants.ChampionPNGURL}${championName}.png"
    }
}