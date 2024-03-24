package com.example.pandacapstone.viewModel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pandacapstone.model.DeliverySchedule
import com.example.pandacapstone.model.UserPreferences
import com.example.pandacapstone.view.DietType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters

class UserPrefViewModel : ViewModel() {
    private val userPref = MutableStateFlow(UserPreferences())
    val userPrefState: StateFlow<UserPreferences> = userPref.asStateFlow()

    // diet type
    private val _selectedDietType = MutableLiveData<DietType?>()
    val selectedDietType: LiveData<DietType?> = _selectedDietType

    // freq and time
    private val _selectedDays = mutableStateOf<Int?>(null)
    val selectedDays: MutableState<Int?> = _selectedDays

    private val _priceRange = MutableStateFlow(5f..20f)
    val priceRange: StateFlow<ClosedFloatingPointRange<Float>> get() = _priceRange

    private val _deliveryDate = mutableStateOf<List<String>>(emptyList())
    val deliveryDate: MutableState<List<String>> get() = _deliveryDate


    fun setDietType(dietType: String) {
        userPref.update { currentState ->
            currentState.copy(
                dietType = dietType
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setFreqTime(day: String, deliveryTime: String, nWeek: String) {
        var ld: LocalDate = LocalDate.of(LocalDate.now().year, LocalDate.now().month, LocalDate.now().dayOfMonth)
        var nextDate: LocalDate
        var everynWeek: LocalDate
        val deliveryDateList: MutableList<DeliverySchedule.DeliveryDate> = mutableListOf()

        val deliverySchedule: MutableList<DeliverySchedule> = mutableListOf()

        for (i in 1..9) {
            nextDate = ld.with(TemporalAdjusters.nextOrSame(DayOfWeek.valueOf(day.uppercase())))
            when (i) {
                1 -> {
                    val formatted = nextDate.format(DateTimeFormatter.ofPattern("d MMM"))
                    deliveryDateList.add(DeliverySchedule.DeliveryDate("$formatted, ${deliveryTime.subSequence(0, 8)}"))
                }
            }
            everynWeek = nextDate.plusWeeks(nWeek.toLong())
            ld = everynWeek
            val formatted = everynWeek.format(DateTimeFormatter.ofPattern("d MMM"))

            deliveryDateList.add(DeliverySchedule.DeliveryDate("$formatted, ${deliveryTime.subSequence(0, 8)}"))
        }

        deliverySchedule.add(DeliverySchedule(nWeek.toLong(), day, deliveryDateList))
        Log.i("deliverySchedule", deliverySchedule.toString())

        userPref.update { currentState ->
            currentState.copy(
                nWeek = nWeek
            )
        }

        userPref.update { currentState ->
            currentState.copy(
                day = day
            )
        }

        userPref.update { currentState ->
            currentState.copy(
                deliveryDate = deliverySchedule
            )
        }

        userPref.update { currentState ->
            currentState.copy(
                deliveryTime = deliveryTime
            )
        }
    }

    fun setFoodPref(foodPreference: String) {
        userPref.update { currentState ->
            currentState.copy(
                foodPreference = foodPreference
            )
        }
    }

    fun onDietTypeSelected(dietType: DietType) {
        _selectedDietType.value = dietType
    }

    fun setCustomisation(rating: Int, minPrice: Int, maxPrice: Int) {
        userPref.update { currentState ->
            currentState.copy(
                rating = rating,
                minPrice = minPrice,
                maxPrice = maxPrice
            )
        }
    }

    fun updatePriceRange(newRange: ClosedFloatingPointRange<Float>) {
        _priceRange.value = newRange
    }
}
