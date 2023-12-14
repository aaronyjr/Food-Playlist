package com.example.pandacapstone

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pandacapstone.model.UserPreferences

@Composable
fun UserInputScreen(userPreferences: UserPreferences) {

    Column {
        Result("Diet Type: ${userPreferences.dietType}")
        Result("Food Preference: ${userPreferences.foodPreference}")
        Result("Delivery Dates (calculated based on user's selected frequency and day):\n${userPreferences.deliveryDate}")
        Result("Delivery Time: ${userPreferences.deliveryTime}")
        Result("Quantity: ${userPreferences.quantity}")
        Result("Meal: ${userPreferences.meal}")
        Result("Rating: ${userPreferences.rating}")
        Result("Price: $${userPreferences.minPrice} - $${userPreferences.maxPrice}")
    }
}

@Composable
fun Result(input: String) {
    Text(text = input, modifier = Modifier.padding(bottom = 16.dp))
}
