package com.example.pandacapstone.model.repository

import com.example.pandacapstone.model.CompletedPlaylist
import com.example.pandacapstone.model.DeletedDish
import com.example.pandacapstone.model.DeliverySchedule
import com.example.pandacapstone.model.IndividualPlaylist
import com.example.pandacapstone.model.Playlist
import com.example.pandacapstone.model.UpcomingDelivery
import com.example.pandacapstone.model.service.RetrofitInstance
import retrofit2.Response

class PlaylistRepository {
    private val playlistService = RetrofitInstance.playlistService

    suspend fun getRestaurants(
        postDeliveryDate: List<DeliverySchedule>,
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

    suspend fun updateActiveStatus(id: Int, isActive: Boolean): List<CompletedPlaylist> {
        return playlistService.updateActiveStatus(id, isActive)
    }

    suspend fun deletePlaylistDish(id: Int) : DeletedDish {
        return playlistService.deletePlaylistDish(id)
    }

    suspend fun getUpcomingDelivery() : Response<UpcomingDelivery> {
        return playlistService.getUpcomingDelivery()
    }
}
