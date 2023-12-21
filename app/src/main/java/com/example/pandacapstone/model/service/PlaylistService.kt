package com.example.pandacapstone.model.service

import com.example.pandacapstone.model.Playlist
import retrofit2.http.GET

interface PlaylistService {
    @GET("api/characters")
    suspend fun getRestaurant(): List<Playlist>
}
