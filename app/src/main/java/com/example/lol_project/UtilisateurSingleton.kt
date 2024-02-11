package com.example.lol_project.com.example.lol_project

import com.example.lol_project.models.Utilisateur

object UtilisateurSingleton {
    //L'utilisateur Singleton permet de sauvegarder l'utilisateur (commme une session)
    //On peut le récup partout
    var utilisateur: Utilisateur? = null

    fun updateUtilisateur(newUtilisateur: Utilisateur) {
        utilisateur = newUtilisateur
    }
}
