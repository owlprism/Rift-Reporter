package com.example.lol_project.models

import java.io.Serializable

data class MasteryItem(
    val puuid: String,
    val championId: Int,
    val championLevel: Int,
    val championPoints: Int,
    val lastPlayTime: Long,
    val championPointsSinceLastLevel: Int,
    val championPointsUntilNextLevel: Int,
    val chestGranted: Boolean,
    val tokensEarned: Int,
    val summonerId: String
) : Serializable

class PostModelChampionMastery : ArrayList<MasteryItem>()