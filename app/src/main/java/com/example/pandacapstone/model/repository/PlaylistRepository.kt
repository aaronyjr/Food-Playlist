package com.example.pandacapstone.model.repository

import com.example.pandacapstone.model.CompletedPlaylist
import com.example.pandacapstone.model.Playlist
import com.example.pandacapstone.model.service.RetrofitInstance
import com.example.pandacapstone.view.CreatePlaylistDishRequest

class PlaylistRepository {
    private val playlistService = RetrofitInstance.playlistService

    suspend fun getRestaurants(
        deliveryDate: List<CreatePlaylistDishRequest>,
        cuisine: String,
        dietType: String,
        minPrice: Float,
        maxPrice: Float,
        rating: Float,

        ): List<Playlist> {
        return playlistService.getFiltered(deliveryDate, cuisine, dietType, minPrice, maxPrice, rating)
    }

    suspend fun getCompletedPlaylists(): List<CompletedPlaylist> {
        return playlistService.getCompletedPlaylists()
    }

//    suspend fun getFiltered(gender: String): List<Playlist> {
//        return playlistService.getFiltered(gender)
//    }

//    fun postDeliveryDate(deliveryDate: String) : Call<DeliveryDate> {
//        return playlistService.postDeliveryDate(deliveryDate)
//    }
}
