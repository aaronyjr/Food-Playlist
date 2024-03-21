package com.example.pandacapstone.model.service

import com.example.pandacapstone.model.CompletedPlaylist
import com.example.pandacapstone.model.Playlist
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PlaylistService {
//    @GET("getdishesbyparams/indian/normal/11/12/4")
//    suspend fun getRestaurant(): List<Playlist>

    @POST("dishes/{cuisinename}/{type}/{smallnum}/{bignum}/{rating}")
    suspend fun getFiltered(
        @Body body: List<String>,
        @Path("cuisinename") cuisine: String,
        @Path("type") dietType: String,
        @Path("smallnum") minPrice: Float,
        @Path("bignum") maxPrice: Float,
        @Path("rating") rating: Float,
    ): List<Playlist>


    @GET("playlists")
    suspend fun getCompletedPlaylists(): List<CompletedPlaylist>

//    @POST("playlistdishes")
//    fun postDeliveryDate(
//        @Body requestBody: String
//    ): Call<DeliveryDate>
}
