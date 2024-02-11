package com.example.lol_project.api

import com.example.lol_project.models.*
import com.example.lol_project.utils.Constants.Companion.BaseGameURL
import com.example.lol_project.utils.Constants.Companion.ChampionMastery
import com.example.lol_project.utils.Constants.Companion.ChampionsURL
import com.example.lol_project.utils.Constants.Companion.GameURL
import com.example.lol_project.utils.Constants.Companion.MultipleGamesURL
import com.example.lol_project.utils.Constants.Companion.PersonalApiKey
import com.example.lol_project.utils.Constants.Companion.ProfileURL
import com.example.lol_project.utils.Constants.Companion.RotationURL
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

//Retrofit API endpoint who will return the summoner informations in the PostModel_profile object

interface ApiService {

    @GET(RotationURL)
    @Headers("api_key:$PersonalApiKey")
    fun getChampionRotations(): Call<PostModelRotation>

    @GET(ChampionsURL)
    fun getChampionDetails(): Call<PostModelChampion>

    @GET(ProfileURL)
    @Headers("api_key:$PersonalApiKey")
    fun getProfileDetails(@Path("summonerName") summonerName: String): Call<PostModelProfile>

    @GET(ChampionMastery)
    @Headers("api_key:$PersonalApiKey")
    fun getChampionMastery(@Path("encryptedPUUID") encryptedPUUID: String?): Call<PostModelChampionMastery>

    @GET(MultipleGamesURL)
    @Headers("api_key:$PersonalApiKey")
    fun getMultiplesGames(@Path("puuid") puuid: String?): Call<List<String>>

    @GET(GameURL)
    @Headers("api_key:$PersonalApiKey")
    fun getGameDetails(@Path("matchId") matchId: String?): Call<PostModelGame>

}