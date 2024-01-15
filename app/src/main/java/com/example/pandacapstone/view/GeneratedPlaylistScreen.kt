package com.example.pandacapstone.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.pandacapstone.R
import com.example.pandacapstone.viewModel.PlaylistViewModel

@Composable
fun GeneratedPlaylistScreen(viewModel: PlaylistViewModel) {

    val playlists by viewModel.playlist.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        viewModel.fetchPlaylist()
    }



    LazyColumn() {
        items(playlists) { playlist ->
            Column(modifier = Modifier.padding(bottom = 20.dp)) {
                Image(
                    painter = rememberImagePainter(data = playlist.image),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
                Row() {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("test", )
                    }

                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = playlist.name)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Image(
                                    painter = painterResource(id = R.drawable.rating_icon),
                                    contentDescription = "Rating",
                                    modifier = Modifier.size(30.dp)
                                )
                                Text("4.5")
                                Text("(100+)")
                            }
                        }

                        Text(text = playlist.gender)
                        Text(text = "")
                    }
                }

            }
        }
    }
}
