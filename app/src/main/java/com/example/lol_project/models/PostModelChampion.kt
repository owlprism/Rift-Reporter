package com.example.lol_project.models

import java.io.Serializable

data class PostModelChampion(
    val type: String,
    val format: String,
    val version: String,
    val data: Map<String, ChampionDetails>
) : Serializable

data class ChampionDetails(
    val version: String,
    val id: String,
    val key: String,
    val name: String,
    val title: String,
    val blurb: String,
    val info: ChampionInfo,
    val image: ChampionImage,
    val tags: List<String>,
    val partype: String,
    val stats: ChampionStats
) : Serializable

data class ChampionInfo(
    val attack: Int,
    val defense: Int,
    val magic: Int,
    val difficulty: Int
) : Serializable

data class ChampionImage(
    val full: String,
    val sprite: String,
    val group: String,
    val x: Int,
    val y: Int,
    val w: Int,
    val h: Int
) : Serializable

data class ChampionStats(
    val hp: Double,
    val hpperlevel: Double,
    val mp: Double,
    val mpperlevel: Double,
    val movespeed: Int,
    val armor: Double,
    val armorperlevel: Double,
    val spellblock: Double,
    val spellblockperlevel: Double,
    val attackrange: Int,
    val hpregen: Double,
    val hpregenperlevel: Double,
    val mpregen: Double,
    val mpregenperlevel: Double,
    val crit: Double,
    val critperlevel: Double,
    val attackdamage: Double,
    val attackdamageperlevel: Double,
    val attackspeedperlevel: Double,
    val attackspeed: Double
) : Serializable