package com.example.pandacapstone.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pandacapstone.R

@Composable
fun PlaylistIntro(
    onNextButtonClicked: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp, 0.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Image(
            painter = painterResource(id = R.drawable.playlistintro),
            contentDescription = "Playlist Intro header",
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = stringResource(id = R.string.playlistIntro_title),
            style = TextStyle(fontSize = 32.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
        )
        Text(
            text = stringResource(id = R.string.playlistIntro_desc),
            style = TextStyle(fontSize = 20.sp),
            modifier = Modifier.padding(bottom = 26.dp)
        )
        Row(modifier = Modifier.padding(bottom = 22.dp)) {
            Image(
                painter = painterResource(id = R.drawable.diet_icon),
                contentDescription = "Dietary Preferences",
                modifier = Modifier.size(35.dp)
            )
            Text(
                text = stringResource(id = R.string.dietary_preference),
                modifier = Modifier
                    .align(CenterVertically)
                    .padding(start = 10.dp),
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
            )
        }

        Row(modifier = Modifier.padding(bottom = 22.dp)) {
            Image(
                painter = painterResource(id = R.drawable.dish_icon),
                contentDescription = "Dish Preferences",
                modifier = Modifier.size(35.dp)
            )
            Text(
                text = stringResource(id = R.string.dish_preference),
                modifier = Modifier
                    .align(CenterVertically)
                    .padding(start = 10.dp),
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
            )
        }

        Row(modifier = Modifier.padding(bottom = 22.dp)) {
            Image(
                painter = painterResource(id = R.drawable.freqtime_icon),
                contentDescription = "Frequency and Time Preferences",
                modifier = Modifier.size(35.dp)
            )
            Text(
                text = stringResource(id = R.string.freq_time_preference),
                modifier = Modifier
                    .align(CenterVertically)
                    .padding(start = 10.dp),
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onNextButtonClicked,
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(
                    id = R.color.party_pink
                )
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 30.dp)
                .shadow(8.dp, RoundedCornerShape(8.dp))
        ) {
            Text(
                text = stringResource(id = R.string.btn_get_started),
                modifier = Modifier.padding(10.dp),
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight(700))
            )
        }

//        Column(
//            verticalArrangement = Arrangement.Bottom,
//            modifier = Modifier
//                .fillMaxHeight()
//                .padding(bottom = 30.dp)
//        ) {
//            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
//
//            }
//        }
    }
}
