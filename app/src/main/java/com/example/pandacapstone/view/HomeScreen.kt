package com.example.pandacapstone.view

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.pandacapstone.R
import com.example.pandacapstone.model.CompletedPlaylist
import com.example.pandacapstone.model.UpcomingDelivery
import com.example.pandacapstone.viewModel.HomeScreenViewModel
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Response
import java.text.DecimalFormat

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun HomeScreen(
    onNextButtonClicked: () -> Unit = {},
    onPlaylistClicked: (Int) -> Unit = {},
    viewModel: HomeScreenViewModel,
) {
    val viewState by viewModel.viewState.collectAsState()
    val completedPlaylists: StateFlow<List<CompletedPlaylist>> = viewModel.completedPlaylists
    val playlists by completedPlaylists.collectAsState()
    val upcomingDelivery by viewModel.upcomingDelivery.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchCompletedPlaylists()
        viewModel.fetchUpcomingDelivery()
    }

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

        UpcomingDeliveryCard(upcomingDelivery = upcomingDelivery)

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
                    text = "No more meal dilemmas! Share your preferences, and we'll create a personalised restaurant playlist, just for you!",
                    color = Color.Gray,
                    style = TextStyle(fontSize = 14.sp)
                )
            }
        }

        Spacer(modifier = Modifier.size(18.dp))

        when (viewState) {
            is HomeScreenViewModel.HomeScreenState.Empty ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.pau_singing_cropped),
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                            .padding(bottom = 8.dp)
                    )
                    Text(
                        text = "You haven't added anything to your playlist!",
                        style = TextStyle(fontSize = 14.sp),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    CreatePlaylistBtn(onNextButtonClicked)
                }

            is HomeScreenViewModel.HomeScreenState.Loaded -> {
                CreatePlaylistBtn(onNextButtonClicked)

                Spacer(modifier = Modifier.padding(bottom = 16.dp))

                LazyVerticalGrid(columns = GridCells.Adaptive(180.dp)) {
                    itemsIndexed(playlists) { index, playlist ->
                        var size by remember { mutableStateOf(IntSize.Zero) }
                        BoxWithConstraints {

                            Box(Modifier.onSizeChanged { size = it }) {

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
                                        .clickable { onPlaylistClicked(index + 1) }
                                )
                                Text(
                                    text = playlist.playlistName.capitalize(),
                                    modifier = Modifier
                                        .run {
                                            when {
                                                index % 2 == 0 -> this.padding(start = 0.dp)
                                                else -> this.padding(start = 8.dp)
                                            }
                                        }
                                        .align(Alignment.BottomStart)
                                        .offset(y = (-4).dp),
                                    color = Color.Black,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            if (!playlist.isActive) {
                                Box(Modifier
                                    .then(
                                        with(LocalDensity.current) {
                                            Modifier.size(
                                                width = size.width.toDp(),
                                                height = size.height.toDp(),
                                            )
                                        }
                                    )
                                    .clip(RoundedCornerShape(10.dp))
                                    .padding(bottom = 28.dp)
                                    .background(Color(0xFFeeeeee).copy(alpha = 0.5f))
                                ) {
                                    Row(
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .then(
                                                with(LocalDensity.current) {
                                                    Modifier.size(
                                                        width = size.width.toDp(),
                                                        height = size.height.toDp(),
                                                    )
                                                }
                                            )
                                    ) {
                                        Text(
                                            text = "Paused".uppercase(),
                                            modifier = Modifier
                                                .background(colorResource(id = R.color.party_pink))
                                                .padding(5.dp),
                                            color = Color.White
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CreatePlaylistBtn(onNextButtonClicked: () -> Unit) {
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
            style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight(500))
        )
    }
}

@Composable
fun UpcomingDeliveryCard(
    upcomingDelivery: Response<UpcomingDelivery>
) {
    val price = upcomingDelivery.body()?.dishPrice ?: 0.0
    val decimalFormat = DecimalFormat("#.00")
    val formattedPrice = "$" + decimalFormat.format(price)

    if (upcomingDelivery != null) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(175.dp),
            colors = CardDefaults.cardColors(Color.White),
            border = BorderStroke(2.dp, Color.Gray),
            shape = RoundedCornerShape(40.dp)
        ) {
            Box(Modifier.fillMaxSize()) {
                val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.riding_animation))
                val progress by animateLottieCompositionAsState(
                    composition = composition,
                    iterations = LottieConstants.IterateForever
                )

                LottieAnimation(
                    composition = composition,
                    progress = { progress },
                    modifier = Modifier.offset(x = (-135).dp)
                )

                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .offset(x = 56.dp)
                ) {
                    Row {
                        Text(
                            modifier = Modifier.fillMaxWidth(0.5f),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            text = upcomingDelivery.body()?.restaurantName.toString(),
                            style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        )

                        Text(
                            modifier = Modifier.padding(end = 8.dp),
                            text = formattedPrice,
                            style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        )
                    }

                    Text(
                        text = upcomingDelivery.body()?.dishName.toString(),
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                    Text(
                        text = upcomingDelivery.body()?.dateToBeDelivered.toString(),
                        color = colorResource(id = R.color.party_pink)
                    )

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
