package com.example.pandacapstone.model

import com.google.gson.annotations.SerializedName

data class Playlist(
    @SerializedName("id")
    var id: Int = 0,

    @SerializedName("restaurant_id")
    var restaurantId: Int = 0,

    @SerializedName("name")
    var name: String = "",

    @SerializedName("price")
    var price: Float = 0.0F,

    @SerializedName("cuisine")
    var cuisine: String = "",

    @SerializedName("name_2")
    var restaurantName: String = "",

    @SerializedName("rating")
    var rating: Float = 0.0F,

    @SerializedName("image_url")
    var imageUrl: String = "0",

    @SerializedName("date_to_be_delivered")
    var dateToBeDelivered: String = "",

    @SerializedName("number_of_reviews")
    var numOfReviews: Int = 0
)

data class CompletedPlaylist(
    @SerializedName("id")
    var id: Int = 0,

    @SerializedName("name")
    var playlistName: String = "",

    @SerializedName("image")
    var image: String,

    @SerializedName("food_items")
    var foodItems: String,

    @SerializedName("is_active")
    var isActive: Boolean,

    @SerializedName("created_on")
    var createdOn: Boolean

)

data class SetDeliveryDate(
    val date_to_be_delivered: String
)

data class IndividualPlaylist(
    val id: Int,
    val name: String,
    val price: Double,
    @SerializedName("image_url") val imageUrl: String,
    @SerializedName("name_2") val restaurantName: String,
    @SerializedName("date_to_be_delivered") val deliveryDate: String,
    val cuisine: String,
    @SerializedName("diet_type") val dietType: String,
    @SerializedName("playlist_id") val playlistId: Int,
    val rating: Double,
    @SerializedName("number_of_reviews") val numberOfReviews: Int,
    @SerializedName("playlist_name") val playlistName: String
)
