package com.example.pandacapstone.view

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PauseCircle
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.pandacapstone.R
import com.example.pandacapstone.model.IndividualPlaylist
import com.example.pandacapstone.model.Playlist
import com.example.pandacapstone.viewModel.HomeScreenViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@SuppressLint("UnusedBoxWithConstraintsScope")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IndividualPlaylist(
    viewModel: HomeScreenViewModel,
) {
    val individualPlaylists: StateFlow<List<IndividualPlaylist>> = viewModel.individualPlaylists
    val playlists by individualPlaylists.collectAsState()
    var cuisineName by remember { mutableStateOf("") }
    var imageBanner by remember { mutableStateOf("") }

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    var clicked by remember { mutableStateOf(Playlist()) }
    var isActive by remember { mutableStateOf(false) }
    var numOfWeeks by remember { mutableIntStateOf(0) }
    var dayOfWeeks by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }

    var id by remember { mutableIntStateOf(0) }

    LazyColumn() {
        item {
            Box(
                contentAlignment = Alignment.BottomStart,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            {
                LaunchedEffect(individualPlaylists) {
                    for ((index, playlist) in playlists.withIndex()) {
                        if (index == 0) {
                            imageBanner = playlist.imageUrl
                            Log.i("switchState1", isActive.toString())
                        }
                        cuisineName = playlist.playlistName.capitalize()
                        id = playlist.playlistId
                        numOfWeeks = playlist.numberOfWeeks
                        dayOfWeeks = playlist.dayOfWeek
                        time = playlist.deliveryDate.substring(8)

                        if (playlist.isActive) {
                            isActive = true
                        }
                    }
                }

                AsyncImage(
                    model = imageBanner,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
            }

            Row(modifier = Modifier.padding(bottom = 16.dp)) {
                Row(
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        Text(
                            text = cuisineName,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 16.dp, bottom = 5.dp)
                        )
                        Text(
                            text = buildAnnotatedString {
                                append("Every ")
                                withStyle(
                                    style = SpanStyle(fontWeight = FontWeight.Bold)
                                ) {
                                    if (numOfWeeks == 1)
                                        append("$numOfWeeks week ")
                                    else
                                        append("$numOfWeeks weeks ")
                                }
                                append("on ")
                                withStyle(
                                    style = SpanStyle(fontWeight = FontWeight.Bold)
                                ) {
                                    append(dayOfWeeks)
                                }
                                append(" at ")
                                withStyle(
                                    style = SpanStyle(fontWeight = FontWeight.Bold)
                                ) {
                                    append(time)
                                }
                            },
                            fontSize = 12.sp,
                            modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
                        )
                    }
                    Column(
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.End,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 5.dp, end = 5.dp)
                    ) {
                        IconButton(
                            onClick = {
                                isActive = !isActive
                                viewModel.updateActiveStatus(id, !isActive)
                            },
                            modifier = Modifier.size(60.dp)
                        ) {
                            Icon(
                                imageVector = if (isActive) Icons.Default.PauseCircle else Icons.Default.PlayCircle,
                                contentDescription = "Play",
                                tint = colorResource(id = R.color.party_pink),
                                modifier = Modifier.size(100.dp)
                            )
                        }
                    }
                }
            }

//            Row(
//                modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 16.dp),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Text("Continue deliveries")
//                Spacer(modifier = Modifier.weight(1f))
//                IconButton(onClick = {
//                    isActive = !isActive
//                    viewModel.updateActiveStatus(id, !isActive)
//                }) {
//                    Icon(
//                        imageVector = if (isActive) Icons.Default.PauseCircle else Icons.Default.PlayCircle,
//                        contentDescription = "Play",
//                        tint = colorResource(id = R.color.party_pink),
//                        modifier = Modifier.size(200.dp)
//                    )
//                }
////                Switch(
////                    checked = isActive,
////                    onCheckedChange = {
//////                        viewModel.updateActiveStatus(id, !it)
////                        isActive = it
////                    },
////                    colors = SwitchDefaults.colors(
////                        checkedTrackColor = colorResource(id = R.color.party_pink)
////                    )
////                )
//            }
        }
        itemsIndexed(items = playlists) { index, item ->
            BoxWithConstraints {
                var size by remember { mutableStateOf(IntSize.Zero) }

                Box {
                    Column(
                        modifier = Modifier
                            .padding(16.dp, 0.dp, 16.dp, 0.dp)
                            .onSizeChanged { size = it }
                    ) {
                        Card(
                            border = BorderStroke(width = 2.dp, color = colorResource(id = R.color.cool_grey)),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Column(modifier = Modifier.padding(bottom = 15.dp)) {
                                Image(
                                    painter = rememberAsyncImagePainter(model = item.imageUrl),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(160.dp),
                                    contentScale = ContentScale.Crop,
                                )

                                var quantity by remember { mutableIntStateOf(1) }
                                var quantityState by remember { mutableStateOf(false) }

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(top = 10.dp, start = 10.dp, end = 10.dp),
                                ) {
                                    Column(Modifier.padding(end = 8.dp)) {
                                        OutlinedTextField(
                                            textStyle = TextStyle(fontWeight = FontWeight.Bold, fontSize = 10.sp),
                                            colors = OutlinedTextFieldDefaults.colors(
                                                disabledBorderColor = Color.LightGray,
                                                disabledTextColor = Color.Black
                                            ),
                                            readOnly = true,
                                            value = quantity.toString(),
                                            onValueChange = { quantity = it.toInt() },
                                            modifier = Modifier
                                                .width(45.dp)
                                                .height(45.dp)
                                                .clickable {
                                                    quantityState = !quantityState
                                                    if (quantityState) {
                                                        scope.launch {
                                                            delay(5000)
                                                            quantityState = false
                                                        }
                                                    }
                                                },
                                            enabled = false
                                        )
                                    }
                                    BoxWithConstraints {
                                        Box() {
                                            Column {
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.SpaceBetween,
                                                    modifier = Modifier.fillMaxWidth()
                                                ) {
                                                    Text(
                                                        text = item.restaurantName,
                                                        fontWeight = FontWeight.Bold,
                                                        overflow = TextOverflow.Ellipsis,
                                                        maxLines = 1,
                                                        modifier = Modifier.fillMaxWidth(0.6f)
                                                    )
                                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                                        Image(
                                                            painter = painterResource(id = R.drawable.rating_icon),
                                                            contentDescription = "Rating",
                                                            modifier = Modifier.size(26.dp)
                                                        )
                                                        Text(
                                                            text = item.rating.toString(),
                                                            style = TextStyle(fontSize = 13.sp)
                                                        )
                                                        Text(
                                                            text = " (" + item.numberOfReviews.toString() + ")",
                                                            style = TextStyle(fontSize = 13.sp)
                                                        )
                                                    }
                                                }
                                                Row(
                                                    Modifier.fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.SpaceBetween,
                                                    verticalAlignment = Alignment.Bottom
                                                ) {
                                                    Text(text = item.name)
                                                    Text(text = "$" + item.price)
                                                }
                                                Row(
                                                    Modifier.fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.SpaceBetween,
                                                    verticalAlignment = Alignment.Bottom
                                                ) {
                                                    Text(
                                                        modifier = Modifier.padding(top = 4.dp),
                                                        text = "Delivers on ${item.deliveryDate}",
                                                        color = colorResource(id = R.color.party_pink),
                                                        fontSize = 14.sp
                                                    )
                                                    Icon(
                                                        Icons.Filled.MoreVert,
                                                        contentDescription = "More vertical",
                                                        Modifier
                                                            .size(16.dp)
                                                            .clickable {
                                                                showBottomSheet = true
                                                                // clicked = item
                                                            }
                                                    )
                                                }
                                            }
                                        }
                                        if (quantityState && isActive) {
                                            Box() {
                                                Column(
                                                    verticalArrangement = Arrangement.Center,
                                                    modifier = Modifier.height(75.dp)
                                                ) {
                                                    Row(
                                                        verticalAlignment = Alignment.CenterVertically,
                                                    ) {
                                                        OutlinedIconButton(
                                                            onClick = {
                                                                if (quantity > 1) {
                                                                    quantity -= 1
                                                                }
                                                            },
                                                            border = BorderStroke(
                                                                1.dp,
                                                                colorResource(id = R.color.cool_grey)
                                                            ),
                                                            shape = RectangleShape,
                                                            modifier = Modifier
                                                                .size(width = 40.dp, height = 33.dp)
                                                                .background(Color.White),
                                                        ) {
                                                            Icon(
                                                                imageVector = Icons.Default.Remove,
                                                                contentDescription = "Remove",
                                                                tint = colorResource(id = R.color.dark_pink)
                                                            )
                                                        }
                                                        OutlinedIconButton(
                                                            onClick = { quantity += 1 },
                                                            border = BorderStroke(
                                                                1.dp,
                                                                colorResource(id = R.color.cool_grey)
                                                            ),
                                                            shape = RectangleShape,
                                                            modifier = Modifier
                                                                .size(width = 40.dp, height = 33.dp)
                                                                .background(Color.White),
                                                        ) {
                                                            Icon(
                                                                imageVector = Icons.Default.Add,
                                                                contentDescription = "Add",
                                                                tint = colorResource(id = R.color.dark_pink)
                                                            )
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }

                                    if (showBottomSheet) {
                                        ModalBottomSheet(
                                            onDismissRequest = {
                                                showBottomSheet = false
                                            },
                                            sheetState = sheetState,
                                            scrimColor = Color.Transparent
                                        ) {
                                            // Sheet content
                                            Column(
                                                Modifier
                                                    .fillMaxWidth()
                                                    .padding(bottom = 15.dp)
                                            ) {
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .height(50.dp)
                                                        .clickable {
//                                                            viewModel.deletePlaylistDish()
                                                            Log.i(
                                                                "food2panda",
                                                                "Clicked Remove from playlist $clicked"
                                                            )
                                                        }
                                                ) {
                                                    Text(
                                                        "Remove from playlist",
                                                        modifier = Modifier.padding(start = 30.dp, end = 30.dp)
                                                    )
                                                }
                                                Row(verticalAlignment = Alignment.CenterVertically,
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .height(50.dp)
                                                        .clickable {
                                                            Log.i(
                                                                "foodpanda",
                                                                "Clicked Change delivery date"
                                                            )
                                                        }
                                                ) {
                                                    Text(
                                                        "Change delivery date",
                                                        modifier = Modifier.padding(start = 30.dp, end = 30.dp)
                                                    )
                                                }
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .height(50.dp)
                                                        .clickable {
                                                            Log.i(
                                                                "foodpanda",
                                                                "Clicked Change delivery date"
                                                            )
                                                        })
                                                {
                                                    Text(
                                                        "Do not recommend this",
                                                        modifier = Modifier.padding(start = 30.dp, end = 30.dp)
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.padding(15.dp))
                    }
                }
                Box {
                    Column(
                        modifier = Modifier
                            .padding(16.dp, 0.dp, 16.dp, 0.dp)
                            .then(
                                with(LocalDensity.current) {
                                    Modifier.size(
                                        width = size.width.toDp(),
                                        height = size.height.toDp(),
                                    )
                                }
                            )
                            .background(
                                if (!isActive) {
                                    Color(0xFFeeeeee).copy(alpha = 0.5f)
                                } else {
                                    Color.Transparent
                                }
                            )
                    ) {
                    }
                }
            }
        }
    }
}
