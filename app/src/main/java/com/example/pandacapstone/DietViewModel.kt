package com.example.pandacapstone

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DietViewModel : ViewModel() {
    private val _selectedDietType = MutableLiveData<DietType?>()
    val selectedDietType: LiveData<DietType?> = _selectedDietType

    fun onDietTypeSelected(dietType: DietType) {
        _selectedDietType.value = dietType
    }
}
