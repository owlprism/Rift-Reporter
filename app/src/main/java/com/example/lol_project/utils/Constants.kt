package com.example.lol_project.utils

class Constants {

    companion object{
        const val BaseURL = "https://euw1.api.riotgames.com/"
        const val PersonalApiKey = "RGAPI-4f2788bd-2e42-4ca7-80d4-51dde4112df7"

        const val RotationURL = "/lol/platform/v3/champion-rotations"
        const val ChampionsURL = "https://ddragon.leagueoflegends.com/cdn/14.1.1/data/fr_FR/champion.json"
        const val ChampionPNGURL = "https://ddragon.leagueoflegends.com/cdn/14.1.1/img/champion/"

        const val ProfileURL = "lol/summoner/v4/summoners/by-name/{summonerName}"
        const val ProfilePNGURL = "https://ddragon.leagueoflegends.com/cdn/14.1.1/img/profileicon/"

        const val ChampionMastery = "/lol/champion-mastery/v4/champion-masteries/by-puuid/{encryptedPUUID}/top"

        const val BaseGameURL = "https://europe.api.riotgames.com/"
        const val MultipleGamesURL = "/lol/match/v5/matches/by-puuid/{puuid}/ids"

        const val GameURL = "/lol/match/v5/matches/{matchId}"
    }
}