package com.example.pandacapstone.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pandacapstone.R


@Composable
fun HomeScreen(
    onNextButtonClicked: () -> Unit = {},

    ) {

    val list = listOf(
        "Item 1",
        "Waichin"
    )


    Column {
        Text(text = "Upcoming deliveries",
            modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
            style = TextStyle( fontSize = 24.sp,
            fontWeight = FontWeight.Bold)
        )

        Text(text = "My playlists",
            modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
            style = TextStyle( fontSize = 24.sp,
                fontWeight = FontWeight.Bold)
        )

        Row{
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

        LazyRow {
            items(list) { item ->
                Image(
                    painter = painterResource(
                        id = R.drawable.burger_example
                    ),
                    contentDescription = null,
                    modifier = Modifier.size(180.dp, 180.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.size(8.dp))
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomeScreenpreview() {
    HomeScreen()
}
