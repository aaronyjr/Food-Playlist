package com.example.pandacapstone.model

import com.google.gson.annotations.SerializedName

data class Playlist(
    @SerializedName("name")
    var name:String = "",

    @SerializedName("gender")
    var gender: String = "",
//
//    @SerializedName("dateOfBirth")
//    var deliveryOn: String = "",
//
//    @SerializedName("yearOfBirth")
//    var rating: Int = 0,
//
//    @SerializedName("yearOfBirth")
//    var numOfReviews: Int = 0,
//
    @SerializedName("image")
    var image: String = ""
)
