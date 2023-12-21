package com.example.pandacapstone.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pandacapstone.model.Playlist
import com.example.pandacapstone.model.repository.PlaylistRepository
import kotlinx.coroutines.launch

class PlaylistViewModel: ViewModel() {
    private val repository = PlaylistRepository()

    private val _playlist = MutableLiveData<List<Playlist>>()
    val playlist: LiveData<List<Playlist>> = _playlist

    fun fetchPlaylist() {
        viewModelScope.launch {
            try {
                val genPlaylist = repository.getRestaurants()
                    _playlist.value = genPlaylist
                    Log.i("foodpanda", "success")
                    Log.i("foodpanda", _playlist.value.toString())

            } catch (e: IllegalStateException) {
                Log.e("ViewModel Error", e.toString())
            }
        }

    }
}
