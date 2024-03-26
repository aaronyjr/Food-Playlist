package com.example.pandacapstone.model.service

import com.example.pandacapstone.model.CompletedPlaylist
import com.example.pandacapstone.model.DeletedDish
import com.example.pandacapstone.model.DeliverySchedule
import com.example.pandacapstone.model.IndividualPlaylist
import com.example.pandacapstone.model.Playlist
import com.example.pandacapstone.model.UpcomingDelivery
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PlaylistService {
    @POST("dishes/{cuisinename}/{type}/{smallnum}/{bignum}/{rating}")
    suspend fun getFiltered(
        @Body body: List<DeliverySchedule>,
        @Path("cuisinename") cuisine: String,
        @Path("type") dietType: String,
        @Path("smallnum") minPrice: Float,
        @Path("bignum") maxPrice: Float,
        @Path("rating") rating: Float,
    ): List<Playlist>

    @GET("playlists")
    suspend fun getCompletedPlaylists(): List<CompletedPlaylist>

    @GET("playlist/{id}")
    suspend fun getIndividualPlaylist(
        @Path("id") playlistId: Int
    ) : List<IndividualPlaylist>

    @PUT("updateisactive/{id}/{is_active}")
    suspend fun updateActiveStatus(
        @Path("id") playlistId: Int,
        @Path("is_active") active: Boolean,
    ) : List<CompletedPlaylist>

    @DELETE("playlistdish/{id}")
    suspend fun deletePlaylistDish(
        @Path("id") id: Int,
    ) : DeletedDish

    @GET("upcomingdelivery")
    suspend fun getUpcomingDelivery(): Response<UpcomingDelivery>
}
