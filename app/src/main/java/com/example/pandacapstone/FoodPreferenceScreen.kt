package com.example.pandacapstone

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.pandacapstone.model.UserPreferences

@Composable
fun FoodPreferenceScreen(
    userPreferences: UserPreferences
) {
    Column {
        Text("Food Preference")
        Text(text = "${userPreferences.dietType}")
    }
}
