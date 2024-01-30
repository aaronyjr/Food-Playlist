package com.example.pandacapstone.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pandacapstone.model.Playlist
import com.example.pandacapstone.model.repository.PlaylistRepository
import kotlinx.coroutines.launch

class PlaylistViewModel() : ViewModel() {
    private val repository = PlaylistRepository()

    private val _playlist = MutableLiveData<List<Pair<Playlist, String>>>()
    val playlist: LiveData<List<Pair<Playlist, String>>> = _playlist

    fun fetchPlaylist(deliveryDate: List<String>) {
        viewModelScope.launch {
            try {
                val genPlaylist = repository.getRestaurants()
                var combined: List<Pair<Playlist, String>> = genPlaylist.zip(deliveryDate)
                _playlist.value = combined
                Log.i("foodpanda22", deliveryDate.toString())
                Log.i("foodpanda22", combined.toString())
                Log.i("foodpanda", "success")
                Log.i("foodpanda", _playlist.value.toString())

            } catch (e: IllegalStateException) {
                Log.e("ViewModel Error", e.toString())
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
