package com.example.lol_project

import android.content.Context
import android.util.Log
import com.example.lol_project.models.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object APIFunctions {

    fun getChampionRotations(
        context: Context,
        onSuccess: (List<Int>) -> Unit,
        onError: (String) -> Unit
    ) {
        val apiService = RetrofitService.getRetrofitService().getChampionRotations()
        apiService.enqueue(object : Callback<PostModelRotation> {
            override fun onResponse(
                call: Call<PostModelRotation>,
                response: Response<PostModelRotation>
            ) {
                if (response.isSuccessful) {
                    val rotationsResponse = response.body()
                    rotationsResponse?.let {
                        val freeChampionIds = it.freeChampionIds
                        if (freeChampionIds != null) {
                            onSuccess.invoke(freeChampionIds)
                        } else {
                            onError.invoke("Free Champion IDs is null")
                        }
                    }
                } else {
                    onError.invoke("Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<PostModelRotation>, t: Throwable) {
                Log.e("MainActivity", "Retrofit request failed", t)
                t.printStackTrace()
                onError.invoke("Failure: ${t.message}")
            }
        })
    }

    fun getChampionDetails(
        key: String,
        onSuccess: (ChampionDetails) -> Unit,
        onError: (String) -> Unit
    ) {
        val apiService = RetrofitService.getRetrofitService().getChampionDetails()
        apiService.enqueue(object : Callback<PostModelChampion> {
            override fun onResponse(
                call: Call<PostModelChampion>,
                response: Response<PostModelChampion>
            ) {
                if (response.isSuccessful) {
                    val championDetails = response.body()?.data?.values?.find { it.key == key }
                    if (championDetails != null) {
                        onSuccess.invoke(championDetails)
                    } else {
                        onError.invoke("Champion details not found for key: $key")
                    }
                } else {
                    onError.invoke("Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<PostModelChampion>, t: Throwable) {
                Log.e("MainActivity", "Retrofit request failed", t)
                t.printStackTrace()
                onError.invoke("Failure: ${t.message}")
            }
        })
    }

    fun getProfileDetails(
        summonerName: String,
        onSuccess: (PostModelProfile) -> Unit,
        onError: (String) -> Unit
    ) {
        val apiService = RetrofitService.getRetrofitService().getProfileDetails(summonerName)
        Log.d("ProfileActivity", "Request URL: ${apiService.request().url}")
        apiService.enqueue(object : Callback<PostModelProfile> {
            override fun onResponse(
                call: Call<PostModelProfile>,
                response: Response<PostModelProfile>
            ) {
                if (response.isSuccessful) {
                    val ProfileDetails = response.body()
                    if(ProfileDetails != null) {
                        onSuccess.invoke(ProfileDetails)
                    } else {
                        onError.invoke("Profile details not FOUND U DUMBASS")
                    }
                } else {
                    onError.invoke("Error ${response.message()}")
                }
            }

            override fun onFailure(call: Call<PostModelProfile>, t: Throwable) {
                Log.e("ProfileActivity", "Retrofit request failed", t)
                t.printStackTrace()
                onError.invoke("Failure: ${t.message}")
            }
        })
    }

    fun getChampionsTop(
        encryptedPUUID : String,
        onSuccess: (PostModelChampionMastery) -> Unit,
        onError: (String) -> Unit
    ) {
        val apiService = RetrofitService.getRetrofitService().getChampionMastery(encryptedPUUID)
        Log.d("ProfileActivity", "Request URL: ${apiService.request().url}")
        apiService.enqueue(object : Callback<PostModelChampionMastery> {
            override fun onResponse(
                call: Call<PostModelChampionMastery>,
                response: Response<PostModelChampionMastery>
            ) {
                if (response.isSuccessful) {
                    val ChampionMastery = response.body()
                    if (ChampionMastery != null) {
                        onSuccess.invoke(ChampionMastery)
                    } else {
                        onError.invoke("ChampionMastery not FOUND")
                    }
                } else {
                    onError.invoke("Error ${response.message()}")
                }
            }

            override fun onFailure(call: Call<PostModelChampionMastery>, t: Throwable) {
                Log.e("ProfileActivity", "Retrofit ChapionMastery request failed", t)
                t.printStackTrace()
                onError.invoke("Failure: ${t.message}")
            }
        })
    }
}
