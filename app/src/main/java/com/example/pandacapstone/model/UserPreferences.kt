package com.example.pandacapstone.model

data class UserPreferences(
    var dietType: String = "",
    var foodPreference: String = "",
    var week: String = "1",
    var day: String = "",
    var time: String = "",
    var month: Int = 0,
    var monthDay: Int = 0,
    var quantity: Int = 0,
    var meal: Boolean = true,
    var rating: Int = 0,
    var priceRange: String = ""
)




