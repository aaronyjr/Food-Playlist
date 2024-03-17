package com.example.pandacapstone.model

import com.google.gson.annotations.SerializedName

data class Playlist(
    @SerializedName("restaurant_id")
    var id: Int = 0,

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
