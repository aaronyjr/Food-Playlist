package com.example.pandacapstone.model

class UserPreferences {
    var dietType: String = ""
    var foodPreference: String = ""
    var week: Int = 0
    var day: String = ""
    var time: String = ""
    var month: Int = 0
    var monthDay: Int = 0
    var quantity: Int = 0
    var meal: Boolean = true
    var rating: Int = 0
    var priceRange: String = ""

    fun insertUserPreference(dietType: String, foodPreference: String, week: Int, day: String, time: String, month: Int, monthDay: Int, quantity: Int, meal: Boolean, rating: Int, priceRange: String ) {
        this.dietType = dietType
        this.foodPreference = foodPreference
        this.week = week
        this.day = day
        this.time = time
        this.month = month
        this.monthDay = monthDay
        this.quantity = quantity
        this.meal = meal
        this.rating = rating
        this.priceRange = priceRange
    }

    fun insertDietType(dietType: String) {
        this.dietType = dietType
    }

    fun insertFoodPreference(foodPreference: String) {
        this.foodPreference = foodPreference
    }

    fun insertFreqTimeInWeek(week: Int, day: String, time: String) {
        this.week = week
        this.day = day
        this.time = time
    }

    fun insertFreqTimeInMonth(month: Int, monthDay:Int, time:String) {
        this.month = month
        this.monthDay = monthDay
        this.time = time
    }

    fun insertCustomisation(quantity: Int, meal: Boolean, rating: Int, priceRange: String) {
        this.quantity = quantity
        this.meal = meal
        this.rating = rating
        this.priceRange = priceRange
    }
}



