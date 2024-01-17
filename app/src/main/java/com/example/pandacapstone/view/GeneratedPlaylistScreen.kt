package com.example.pandacapstone.view

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
                // Declaring a boolean value to store
                // the expanded state of the Text Field
                var weekExpanded by remember { mutableStateOf(false) }
                // Create a list of cities
                val weekList = listOf("1", "2", "3", "4")
                // Create a string value to store the selected city
                var nWeek by rememberSaveable { mutableStateOf("1") }
                var weekTextFieldSize by remember { mutableStateOf(Size.Zero) }

                // Up Icon when expanded and down icon when collapsed
                val icon = if (weekExpanded)
                    null
                else
                    Icons.Filled.KeyboardArrowDown
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.padding(end = 8.dp)) {
                        // Create an Outlined Text Field
                        // with icon and not expanded
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
                                    // This value is used to assign to
                                    // the DropDown the same width
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


                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column() {
                                Text(text = playlist.gender)
                                Text(text = "Delivery on")
                            }
                            Column() {
                                Icon(
                                    Icons.Filled.MoreVert,
                                    contentDescription = "More vertical",
                                    Modifier.clickable { showBottomSheet = true })
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
                                    .padding(30.dp)
                                    .fillMaxWidth()) {
                                Row(verticalAlignment = Alignment.CenterVertically){
                                    Text(
                                        "Remove from playlist",
                                        Modifier
                                            .padding(bottom = 30.dp)
                                            .fillMaxWidth()
                                            .height(50.dp)
//                                        .wrapContentHeight(align = Alignment.CenterVertically)
                                            .clickable { Log.i("foodpanda", "Clicked Remove from playlist") })

                                }
                                Text(
                                    "Change delivery date",
                                    Modifier
                                        .padding(bottom = 30.dp)
                                        .fillMaxWidth()
                                        .clickable { Log.i("foodpanda", "Clicked Change delivery date") })
                                Text(
                                    "Do not recommend this",
                                    Modifier
                                        .padding(bottom = 30.dp)
                                        .fillMaxWidth()
                                        .clickable { Log.i("foodpanda", "Clicked do not recommend this") })
                            }
                        }
                    }
                }
            }
        }
    }
}
