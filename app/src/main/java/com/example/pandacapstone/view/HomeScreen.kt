package com.example.pandacapstone.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.pandacapstone.R
import com.example.pandacapstone.viewModel.HomeScreenViewModel

@Composable
fun HomeScreen(
    onNextButtonClicked: () -> Unit = {},
    onPlaylistClicked: () -> Unit = {},
    viewModel: HomeScreenViewModel,
) {
    val viewState by viewModel.viewState.collectAsState()
    val completedPlaylist = viewModel.completedPlaylists.collectAsState()
    
    Column {
        Spacer(modifier = Modifier.height(64.dp))

        Text(
            text = "Upcoming deliveries",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(Modifier.size(8.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(175.dp),
            colors = CardDefaults.cardColors(Color.White),
            border = BorderStroke(2.dp, Color.Gray),
            shape = RoundedCornerShape(40.dp)
        ) {
            Box(Modifier.fillMaxSize()) {
                Text(
                    text = "On the way", modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        Spacer(modifier = Modifier.size(16.dp))

        Box {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "My playlists",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                )

                Spacer(modifier = Modifier.size(8.dp))

                Text(
                    text = "No more meal dilemmas! Share your preferences, and " +
                        "we'll create a personalised restaurant playlist, just for you!",
                    modifier = Modifier.size(300.dp, 70.dp),
                    color = Color.Gray,
                    style = TextStyle(fontSize = 14.sp)
                )
            }

            Image(
                painter = painterResource(id = R.drawable.pau_singing_cropped),
                contentDescription = "PauPau",
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.CenterEnd),

            )
        }

        Button(
            onClick = onNextButtonClicked,
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(
                    id = R.color.party_pink
                )
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .width(220.dp)

        ) {
            Text(
                text = "Create new playlist!",
                modifier = Modifier.padding(6.dp),
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight(500))
            )
        }

        Spacer(modifier = Modifier.size(18.dp))

        when (viewState) {
            is HomeScreenViewModel.HomeScreenState.Empty ->
                Image(painter = painterResource(id = R.drawable.pau_pondering), contentDescription = null)

            is HomeScreenViewModel.HomeScreenState.Loaded -> {
                LazyVerticalGrid(columns = GridCells.Adaptive(180.dp)) {
                    itemsIndexed(completedPlaylist.value) { index, playlist ->
                        Box {
                            AsyncImage(
                                model = playlist.image,
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .run {
                                        when {
                                            index % 2 == 0 -> this.padding(end = 8.dp)
                                            else -> this.padding(start = 8.dp)
                                        }
                                    }
                                    .padding(bottom = 28.dp)
                                    .size(180.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .clickable { onPlaylistClicked() }
                            )
                            Text(
                                text = playlist.playlistName,
                                modifier = Modifier
                                    .run {
                                        when {
                                            index % 2 == 0 -> this.padding(start = 8.dp)
                                            else -> this.padding(start = 16.dp)
                                        }
                                    }
                                    .align(Alignment.BottomStart)
                                    .offset(y = (-4).dp),
                                color = Color.Black,
                                fontWeight = FontWeight.Bold

                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    HomeScreen(viewModel = HomeScreenViewModel())
}
