package com.example.pandacapstone

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pandacapstone.model.UserPreferences

@Composable
fun UserInputScreen(userPreferences: UserPreferences) {

    Column {
        Text(text = "${userPreferences.dietType}")
        Text(text = "${userPreferences.foodPreference}")
        Text(text = "${userPreferences.deliveryTime}")
        Text(text = "${userPreferences.quantity}")
        Text(text = "${userPreferences.meal}")
        Text(text = "${userPreferences.rating}")
        Text(text = "$${userPreferences.minPrice} - $${userPreferences.maxPrice}")
        Text(text = "${userPreferences.deliveryDate}")
    }
}
