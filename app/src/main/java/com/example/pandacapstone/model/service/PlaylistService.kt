package com.example.pandacapstone.model.service

import com.example.pandacapstone.model.Playlist
import retrofit2.http.GET
import retrofit2.http.Path

interface PlaylistService {
//    @GET("getdishesbyparams/indian/normal/11/12/4")
//    suspend fun getRestaurant(): List<Playlist>

    @GET("getdishesbyparams/{cuisinename}/{type}/{smallnum}/{bignum}/{rating}")
    suspend fun getFiltered(
        @Path("cuisinename") cuisine: String,
        @Path("type") dietType: String,
        @Path("smallnum") minPrice: Float,
        @Path("bignum") maxPrice: Float,
        @Path("rating") rating: Float,
    ): List<Playlist>
}
