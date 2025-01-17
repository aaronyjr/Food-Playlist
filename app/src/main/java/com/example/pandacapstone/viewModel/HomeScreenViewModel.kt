package com.example.pandacapstone.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pandacapstone.model.CompletedPlaylist
import com.example.pandacapstone.model.IndividualPlaylist
import com.example.pandacapstone.model.UpcomingDelivery
import com.example.pandacapstone.model.repository.PlaylistRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import java.net.ConnectException
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor() : ViewModel() {
    private val repository = PlaylistRepository()

    val _viewState = MutableStateFlow<HomeScreenState>(HomeScreenState.Empty)
    val viewState: StateFlow<HomeScreenState> = _viewState

    val _individualPlaylistViewState = MutableStateFlow<IndividualScreenState>(IndividualScreenState.LoadingScreen)
    val individualPlaylistViewState: StateFlow<IndividualScreenState> = _individualPlaylistViewState

    private val _completedPlaylists: MutableStateFlow<List<CompletedPlaylist>> = MutableStateFlow(emptyList())
    val completedPlaylists: StateFlow<List<CompletedPlaylist>> = _completedPlaylists

    val _individualPlaylists: MutableStateFlow<List<IndividualPlaylist>> = MutableStateFlow(emptyList())
    val individualPlaylists: StateFlow<List<IndividualPlaylist>> = _individualPlaylists

    val _upcomingDelivery: MutableStateFlow<Response<UpcomingDelivery>> = MutableStateFlow(Response.success(null))
    val upcomingDelivery: StateFlow<Response<UpcomingDelivery>> = _upcomingDelivery

    val _index  = MutableStateFlow(1)
    val index: StateFlow<Int> = _index

    val _isActive: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isActive: StateFlow<Boolean> = _isActive

    fun fetchCompletedPlaylists() {
        viewModelScope.launch {
            _viewState.value = HomeScreenState.LoadingScreen
            try {
                val completedPlaylist = repository.getCompletedPlaylists()
                _completedPlaylists.value = completedPlaylist
                delay(500)
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
            _individualPlaylistViewState.value = IndividualScreenState.LoadingScreen
            delay(500)
            val individualPlaylist = repository.getIndividualPlaylist(id)
            _individualPlaylists.value = individualPlaylist
            _isActive.value = individualPlaylist.firstOrNull()?.isActive == true
            _individualPlaylistViewState.value = IndividualScreenState.Loaded
        }
    }

    fun updateActiveStatus(id: Int, isActive: Boolean) {
        viewModelScope.launch {
            try {
                repository.updateActiveStatus(id, isActive)
            } catch (e: Exception) {
                Log.e("e", e.toString())
            }
        }
    }

    fun deletePlaylistDish(id: Int): Job {
        return viewModelScope.launch {
            repository.deletePlaylistDish(id)
        }
    }

    fun fetchUpcomingDelivery() {
        viewModelScope.launch {
            val upcomingDelivery = repository.getUpcomingDelivery()
            try {
                _upcomingDelivery.value = upcomingDelivery
            } catch (e: Exception) {
                throw e
            }
        }
    }

    sealed interface HomeScreenState {
        object Empty : HomeScreenState
        object Loaded : HomeScreenState
        object LoadingScreen: HomeScreenState
    }

    sealed interface IndividualScreenState {
        object LoadingScreen: IndividualScreenState
        object Loaded: IndividualScreenState
    }

}

