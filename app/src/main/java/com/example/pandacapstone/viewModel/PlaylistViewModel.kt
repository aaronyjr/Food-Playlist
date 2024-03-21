package com.example.pandacapstone.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pandacapstone.model.CreatePlaylistDishRequest
import com.example.pandacapstone.model.Playlist
import com.example.pandacapstone.model.repository.PlaylistRepository
import kotlinx.coroutines.launch
import java.net.ConnectException

class PlaylistViewModel() : ViewModel() {
    private val repository = PlaylistRepository()

    private val _playlist = MutableLiveData<List<Playlist>>()
    val playlist: LiveData<List<Playlist>> = _playlist

    fun fetchPlaylist(
        deliveryDateJson: List<CreatePlaylistDishRequest>,
        cuisine: String,
        dietType: String,
        minPrice: Float,
        maxPrice: Float,
        rating: Float,
    ) {
        viewModelScope.launch {
            try {
                val genPlaylist = repository.getRestaurants(deliveryDateJson, cuisine, dietType, minPrice, maxPrice, rating)
                _playlist.value = genPlaylist
            } catch (e: IllegalStateException) {
                Log.e("ViewModel Error", e.toString())
            } catch (e: ConnectException) {
                Log.e("No database loaded", e.toString())
            }
        }
    }
}
