package com.skillbox.compose.network


import com.skillbox.compose.data.Characters
import com.skillbox.compose.data.DetailCharacter
import com.skillbox.compose.data.Episode
import com.skillbox.compose.data.Locations
import com.skillbox.compose.utils.ServerItemsWrapper
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CharacterApi {
    @GET("character")
    suspend fun loadCharacter(): ServerItemsWrapper<Characters>

    @GET("character/{id}")
    fun loadDetail(@Path("id") id: Long): Call<DetailCharacter>

    @GET("location")
    suspend fun loadLocation(): ServerItemsWrapper<Locations>

    @GET("episode/{id}")
    fun loadEpisode(@Path("id") id: Long): Call<Episode>

}
