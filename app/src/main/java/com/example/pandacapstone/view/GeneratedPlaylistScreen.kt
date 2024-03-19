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
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.pandacapstone.R
import com.example.pandacapstone.model.Playlist
import com.example.pandacapstone.model.UserPreferences
import com.example.pandacapstone.viewModel.PlaylistViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("UnusedBoxWithConstraintsScope")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeneratedPlaylistScreen(
    viewModel: PlaylistViewModel,
    userPreferences: UserPreferences,
) {

    val playlists by viewModel.playlist.observeAsState(emptyList())
    val deliveryDate = userPreferences.deliveryDate
    var imageBanner by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.fetchPlaylist(
            deliveryDate,
            userPreferences.foodPreference,
            userPreferences.dietType,
            userPreferences.minPrice.toFloat(),
            userPreferences.maxPrice.toFloat(),
            userPreferences.rating.toFloat()
        )
    }

    Log.i("vegetarian", "${userPreferences.dietType}")
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    var clicked by remember { mutableStateOf(Playlist()) }
    var countiueDeliveries by remember { mutableStateOf(true) }

    LazyColumn() {
        item {
            Box(contentAlignment = Alignment.BottomStart)
            {
                for ((index, imageUrl) in playlists.withIndex()) {
                    if (index == 0) {
                        imageBanner = imageUrl.first.imageUrl
                    }
                }
                AsyncImage(
                    model = imageBanner,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                )
                Text(
                    text = userPreferences.foodPreference,
                    color = Color.White,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
            }

            Row(
                modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Continue deliveries", fontSize = 24.sp)
                Spacer(modifier = Modifier.weight(1f))
                Switch(
                    checked = countiueDeliveries,
                    onCheckedChange = { switchState -> countiueDeliveries = switchState },
                    colors = SwitchDefaults.colors(
                        checkedTrackColor = colorResource(id = R.color.party_pink)
                    )
                )
            }
        }
        itemsIndexed(items = playlists) { index, item ->
            Column(modifier = Modifier.padding(16.dp, 0.dp, 16.dp, 0.dp)) {
                Card(
                    border = BorderStroke(width = 2.dp, color = colorResource(id = R.color.cool_grey)),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(modifier = Modifier.padding(bottom = 15.dp)) {
                        Image(
                            painter = rememberAsyncImagePainter(model = item.first.imageUrl),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp),
                            contentScale = ContentScale.Crop,
                        )

                        var quantity by remember { mutableIntStateOf(1) }
                        var quantityState by remember { mutableStateOf(false) }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(start = 10.dp, end = 10.dp),
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
                                            Text(text = item.first.restaurantName, fontWeight = FontWeight.Bold)
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Image(
                                                    painter = painterResource(id = R.drawable.rating_icon),
                                                    contentDescription = "Rating",
                                                    modifier = Modifier.size(30.dp)
                                                )
                                                Text(text = item.first.rating.toString())
                                                Text("(100+)")
                                            }
                                        }
                                        Row(
                                            Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.Bottom
                                        ) {
                                            Column() {
                                                Text(
                                                    text = item.first.name,
                                                    color = Color.Gray
                                                )
                                                Text(
                                                    modifier = Modifier.padding(top = 4.dp),
                                                    text = "Delivers on ${item.second}",
                                                    color = colorResource(id = R.color.party_pink),
                                                    fontSize = 14.sp
                                                )
                                            }
                                            Column() {
                                                Icon(
                                                    Icons.Filled.MoreVert,
                                                    contentDescription = "More vertical",
                                                    Modifier
                                                        .clickable {
                                                            showBottomSheet = true
                                                            clicked = item.first
                                                        }
                                                )
                                            }
                                        }
                                    }
                                }
                                if (quantityState) {
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
    }
}

