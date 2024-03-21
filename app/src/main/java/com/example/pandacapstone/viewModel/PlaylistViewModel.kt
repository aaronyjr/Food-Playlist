package com.example.pandacapstone.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pandacapstone.model.Playlist
import com.example.pandacapstone.model.repository.PlaylistRepository
import kotlinx.coroutines.launch
import java.net.ConnectException

class PlaylistViewModel() : ViewModel() {
    private val repository = PlaylistRepository()

    private val _playlist = MutableLiveData<List<Pair<Playlist, String>>>()
    val playlist: LiveData<List<Pair<Playlist, String>>> = _playlist

    fun fetchPlaylist(
        deliveryDate: List<String>,
        cuisine: String,
        dietType: String,
        minPrice: Float,
        maxPrice: Float,
        rating: Float,
    ) {
        viewModelScope.launch {
            try {
                val genPlaylist = repository.getRestaurants(cuisine, dietType, minPrice, maxPrice, rating)
                Log.i("playlist", genPlaylist.toString())
                var combined: List<Pair<Playlist, String>> = genPlaylist.zip(deliveryDate)
                _playlist.value = combined
            } catch (e: IllegalStateException) {
                Log.e("ViewModel Error", e.toString())
            } catch (e: ConnectException) {
                Log.e("No database loaded", e.toString())
            }
        }
    }

//    fun fetchFiltered(gender: String) {
//        viewModelScope.launch {
//            try {
//                val genPlaylist = repository.getFiltered(gender)
//                Log.i("foodpanda", genPlaylist.toString())
//                _playlist.value = genPlaylist
//                Log.i("foodpanda", "success")
//                Log.i("foodpanda", _playlist.value.toString())
//
//            } catch (e: IllegalStateException) {
//                Log.e("ViewModel Error", e.toString())
//            }
//        }
//    }
}
