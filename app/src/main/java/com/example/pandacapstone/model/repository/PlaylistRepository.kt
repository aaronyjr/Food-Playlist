package com.example.pandacapstone.model.repository

import com.example.pandacapstone.model.Playlist
import com.example.pandacapstone.model.service.RetrofitInstance

class PlaylistRepository {
    private val playlistService = RetrofitInstance.playlistService

    suspend fun getRestaurants(): List<Playlist> {
        return playlistService.getRestaurant()
    }

//    suspend fun getFiltered(gender: String): List<Playlist> {
//        return playlistService.getFiltered(gender)
//    }

}
