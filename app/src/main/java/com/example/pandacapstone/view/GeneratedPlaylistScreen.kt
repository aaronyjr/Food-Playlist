package com.example.pandacapstone.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.pandacapstone.viewModel.PlaylistViewModel

@Composable
fun GeneratedPlaylistScreen(viewModel: PlaylistViewModel) {

    val playlists by viewModel.playlist.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        viewModel.fetchPlaylist()
    }

    LazyColumn() {
        items(playlists) { playlist ->
            Column {
                Text(text = playlist.name)
                Image(
                    painter = rememberImagePainter(data = playlist.image),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
                Text(text = playlist.foodName)

            }
        }
    }
}
