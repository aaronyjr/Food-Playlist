package com.example.pandacapstone.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.pandacapstone.R
import com.example.pandacapstone.viewModel.HomeScreenViewModel

@Composable
fun HomeScreen (
    onNextButtonClicked: () -> Unit = {},
    viewModel: HomeScreenViewModel,
    ) {
    val viewState by viewModel.viewState.collectAsState()

    Column {
        Text(
            text = "Upcoming deliveries",
            modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        )

        Text(
            text = "My playlists",
            modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        )

        Row {
            Text(
                text = "No more meal dilemmas! Share your preferences, and " +
                    "we'll create a personalised restaurant playlist, just for you!",
                modifier = Modifier.size(300.dp, 120.dp)
            )

            OutlinedIconButton(
                onClick = onNextButtonClicked, border = BorderStroke(1.dp, colorResource(id = R.color.party_pink))
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    modifier = Modifier.size(width = 20.dp, height = 20.dp),
                    tint = colorResource(id = R.color.party_pink)
                )
            }
        }
        when (viewState) {
            is HomeScreenViewModel.HomeScreenState.Empty ->
                Image(painter = painterResource(id = R.drawable.burger_example), contentDescription = null)

            is HomeScreenViewModel.HomeScreenState.Loaded -> {
                Column {
                    PlaylistContent(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun PlaylistContent(
    viewModel: HomeScreenViewModel
) {
    val completedPlaylists by viewModel.completedPlaylists.collectAsState()

    AsyncImage(
        model = completedPlaylists[0].image,
        contentDescription = null
    )
}

//@Preview(showBackground = true)
//@Composable
//fun HomeScreenpreview() {
//    HomeScreen(viewState = )
//}
