package com.example.lol_project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lol_project.models.ChampionDetails
import com.example.lol_project.utils.Constants
import com.squareup.picasso.Picasso

class ChampionRotationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rota)

        //Action retour
        val actionBar = supportActionBar
        actionBar !!.title = "Rotation des champions"
        actionBar.setDisplayHomeAsUpEnabled(true)

        val championDetailsList =
            intent.getSerializableExtra("ChampionDetailsList") as? ArrayList<ChampionDetails>

        if (championDetailsList != null && championDetailsList.isNotEmpty()) {
            displayChampionDetails(championDetailsList)
        } else {
            Toast.makeText(this, "Champion details not found", Toast.LENGTH_SHORT).show()
        }
    }

    private fun displayChampionDetails(championDetailsList: List<ChampionDetails>) {
        val container = findViewById<LinearLayout>(R.id.championDetailsContainer)

        for (championDetails in championDetailsList) {
            // Inflate the CardView layout
            val cardView = LayoutInflater.from(this)
                .inflate(R.layout.card_champion_details, container, false)

            val championImageView = cardView.findViewById<ImageView>(R.id.championImageView)
            val championNameTextView = cardView.findViewById<TextView>(R.id.championNameTextView)
            val championTagsTextView = cardView.findViewById<TextView>(R.id.championTagsTextView)

            championNameTextView.text = championDetails.name

            Picasso.get().load(getChampionImageUrl(championDetails.id)).into(championImageView)
            championNameTextView.text = championDetails.name

            val championTags = championDetails.tags.joinToString(", ")
            championTagsTextView.text = championTags

            container.addView(cardView)
        }
    }

    private fun getChampionImageUrl(championName: String): String {
        return "${Constants.ChampionPNGURL}$championName.png"
    }


}