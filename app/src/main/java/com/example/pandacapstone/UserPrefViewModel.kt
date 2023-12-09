package com.example.pandacapstone

import androidx.lifecycle.ViewModel
import com.example.pandacapstone.model.UserPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class UserPrefViewModel: ViewModel() {
    private val userPref = MutableStateFlow(UserPreferences())
    val userPrefState: StateFlow<UserPreferences> = userPref.asStateFlow()
    fun setDietType(dietType: String) {
        userPref.update { currentState ->
            currentState.copy(
                dietType = dietType
            )
        }
    }
}
