package com.example.pandacapstone.model

data class UserPreferences(
    var dietType: String = "",
    var foodPreference: String = "",
    var nWeek: String = "1",
    var day: String = "",
    var deliveryTime: String = "",
    var quantity: Int = 0,
    var meal: String = "Yes",
    var rating: Int = 0,
    var minPrice: Int = 0,
    var maxPrice: Int = 0,
    var deliveryDate: List<String> = listOf()
)




