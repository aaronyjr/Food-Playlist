package com.example.pandacapstone.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pandacapstone.model.CompletedPlaylist
import com.example.pandacapstone.model.IndividualPlaylist
import com.example.pandacapstone.model.repository.PlaylistRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.net.ConnectException
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor() : ViewModel() {
    private val repository = PlaylistRepository()

    val _viewState = MutableStateFlow<HomeScreenState>(HomeScreenState.Empty)
    val viewState: StateFlow<HomeScreenState> = _viewState

    private val _completedPlaylists: MutableStateFlow<List<CompletedPlaylist>> = MutableStateFlow(emptyList())
    val completedPlaylists: StateFlow<List<CompletedPlaylist>> = _completedPlaylists

    val _individualPlaylists: MutableStateFlow<List<IndividualPlaylist>> = MutableStateFlow(emptyList())
    val individualPlaylists: StateFlow<List<IndividualPlaylist>> = _individualPlaylists

    init {
        fetchCompletedPlaylists()
    }

    fun fetchCompletedPlaylists() {
        viewModelScope.launch {
            try {
                val completedPlaylist = repository.getCompletedPlaylists()
                _completedPlaylists.value = completedPlaylist
                _viewState.value = if (completedPlaylist.isEmpty()) HomeScreenState.Empty else HomeScreenState.Loaded
            } catch (e: IllegalStateException) {
                Log.e("ViewModel Error", e.toString())
            } catch (e: ConnectException) {
                Log.e("No database loaded", e.toString())
            }
        }
    }

    fun fetchIndividualPlaylist(id: Int) {
        viewModelScope.launch {
            _individualPlaylists.value = repository.getIndividualPlaylist(id)
        }
    }

    sealed interface HomeScreenState {
        object Empty: HomeScreenState
        object Loaded: HomeScreenState
    }

}

