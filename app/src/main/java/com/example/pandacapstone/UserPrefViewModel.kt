package com.example.pandacapstone

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pandacapstone.model.UserPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjuster
import java.time.temporal.TemporalAdjusters

class UserPrefViewModel : ViewModel() {
    private val userPref = MutableStateFlow(UserPreferences())
    val userPrefState: StateFlow<UserPreferences> = userPref.asStateFlow()

    var numofWeeks: String = "1"

    // diet type
    private val _selectedDietType = MutableLiveData<DietType?>()
    val selectedDietType: LiveData<DietType?> = _selectedDietType

    // freq and time
    private val _selectedDays = mutableStateOf<Int?>(null)
    val selectedDays: MutableState<Int?> = _selectedDays


    fun setDietType(dietType: String) {
        userPref.update { currentState ->
            currentState.copy(
                dietType = dietType
            )
        }
    }

    fun setNumOfWeeks(selNumofWeeks: String) {
        numofWeeks = selNumofWeeks
        userPref.update { currentState ->
            currentState.copy(
                nWeek = numofWeeks
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun calDeliveryDate(day: String) {
        Log.i("foodpanda", day)
        Log.i("foodpanda", numofWeeks)

        var ld: LocalDate = LocalDate.of(LocalDate.now().year, LocalDate.now().month, LocalDate.now().dayOfMonth)

        for (i in 1..10) {
            var nextDate = ld.with(TemporalAdjusters.next(DayOfWeek.valueOf(day.uppercase())))
            var everynWeek = nextDate.plusWeeks(numofWeeks.toLong())
            ld = everynWeek
            Log.i("foodpanda", nextDate.toString())
            Log.i("foodpanda", everynWeek.toString())
        }
    }

    fun setFoodPref(foodPreference: String) {
        userPref.update { currentState ->
            currentState.copy(
                foodPreference = foodPreference
            )
        }
    }

    fun setDeliveryTime(deliveryTime: String) {
        userPref.update { currentState ->
            currentState.copy(deliveryTime = deliveryTime)
        }
    }

    fun onDietTypeSelected(dietType: DietType) {
        _selectedDietType.value = dietType
    }


}
