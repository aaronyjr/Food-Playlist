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

    @SerializedName("restaurant_name")
    var restaurantName: String = "",

    @SerializedName("rating")
    var rating: Float = 0.0F,

    @SerializedName("image_url")
    var imageUrl: String = "0",
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

data class DeliveryDate(val dateTobeDelivered: List<String>)

