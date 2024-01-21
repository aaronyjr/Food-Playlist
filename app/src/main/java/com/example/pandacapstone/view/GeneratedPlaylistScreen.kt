package com.example.pandacapstone.view

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import coil.compose.rememberImagePainter
import com.example.pandacapstone.R
import com.example.pandacapstone.model.Playlist
import com.example.pandacapstone.viewModel.PlaylistViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeneratedPlaylistScreen(viewModel: PlaylistViewModel) {

    val playlists by viewModel.playlist.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        viewModel.fetchPlaylist()
    }


    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    var clicked by remember { mutableStateOf(Playlist()) }
    var countiueDeliveries by remember { mutableStateOf(true) }


    LazyColumn() {
        item {
            Box(contentAlignment = Alignment.BottomStart)
            {
                Image(
                    painter = painterResource(id = R.drawable.burger_example),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                )
                Text(
                    "Burgers",
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
                            painter = rememberImagePainter(data = item.image),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                        )

                        var weekExpanded by remember { mutableStateOf(false) }
                        var nWeek by rememberSaveable { mutableStateOf("1") }
                        var weekTextFieldSize by remember { mutableStateOf(Size.Zero) }
                        val icon = if (weekExpanded)
                            null
                        else
                            Icons.Filled.KeyboardArrowDown
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(start = 10.dp, end = 10.dp)
                        ) {
                            Column(Modifier.padding(end = 8.dp)) {
                                OutlinedTextField(
                                    textStyle = TextStyle(fontWeight = FontWeight.Bold, fontSize = 12.sp),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        disabledBorderColor = Color.LightGray,
                                        disabledTextColor = Color.Black
                                    ),
                                    readOnly = true,
                                    value = nWeek,
                                    onValueChange = { nWeek = it },
                                    modifier = Modifier
                                        .width(40.dp)
                                        .height(20.dp)
                                        .onGloballyPositioned { coordinates ->
                                            weekTextFieldSize = coordinates.size.toSize()
                                        }
                                        .clickable { weekExpanded = !weekExpanded },
                                    trailingIcon = {
                                        if (icon != null) {
                                            Icon(
                                                icon,
                                                "contentDescription",
                                                Modifier
                                                    .clickable { weekExpanded = !weekExpanded }
                                                    .size(8.dp),
                                                tint = colorResource(id = R.color.party_pink)
                                            )
                                        }
                                    },
                                    enabled = false
                                )
                            }
                            Column() {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(text = item.name, fontWeight = FontWeight.Bold)
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
                                Row(
                                    Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column() {
                                        Text(text = item.gender)
                                        Text(text = "Delivery on", color = colorResource(id = R.color.party_pink))
                                    }
                                    Column() {
                                        Icon(
                                            Icons.Filled.MoreVert,
                                            contentDescription = "More vertical",
                                            Modifier.clickable {
                                                showBottomSheet = true
                                                clicked = item
                                            })
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
                                                .clickable { Log.i("foodpanda", "Clicked Change delivery date") }
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
                                                .clickable { Log.i("foodpanda", "Clicked Change delivery date") })
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

