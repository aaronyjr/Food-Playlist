package com.example.pandacapstone.model.repository

import com.example.pandacapstone.model.CompletedPlaylist
import com.example.pandacapstone.model.IndividualPlaylist
import com.example.pandacapstone.model.Playlist
import com.example.pandacapstone.model.SetDeliveryDate
import com.example.pandacapstone.model.service.RetrofitInstance

class PlaylistRepository {
    private val playlistService = RetrofitInstance.playlistService

    suspend fun getRestaurants(
        postDeliveryDate: List<SetDeliveryDate>,
        cuisine: String,
        dietType: String,
        minPrice: Float,
        maxPrice: Float,
        rating: Float,

        ): List<Playlist> {
        return playlistService.getFiltered(postDeliveryDate, cuisine, dietType, minPrice, maxPrice, rating)
    }

    suspend fun getCompletedPlaylists(): List<CompletedPlaylist> {
        return playlistService.getCompletedPlaylists()
    }

    suspend fun getIndividualPlaylist(id: Int) : List<IndividualPlaylist> {
        return playlistService.getIndividualPlaylist(id)
    }
}
