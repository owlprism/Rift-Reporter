package com.example.lol_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.lol_project.com.example.lol_project.UtilisateurSingleton
import com.example.lol_project.models.Utilisateur

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Action retour
        val actionBar = supportActionBar
        actionBar !!.title = "Recherche"
        actionBar.setDisplayHomeAsUpEnabled(true)

        //Récup du texte Username
        val EditText_username = findViewById<EditText>(R.id.EditText_username)

        //DECLARATION DES BOUTONS
        val submit_button = findViewById<Button>(R.id.button_submit)

        submit_button.setOnClickListener{
            val nomUtilisateur = EditText_username.text.toString()

            if(nomUtilisateur.isNotBlank()){
                //Création d'une variable utilisateur
                val utilisateur = Utilisateur(nomUtilisateur)

                // Mettre à jour l'utilisateur dans le singleton
                UtilisateurSingleton.updateUtilisateur(utilisateur)

                //Transmission de la variable au menu pour activiation des buttons
                val intent = Intent(this, MenuActivity::class.java)
                startActivity(intent)
            }
        }
    }
}