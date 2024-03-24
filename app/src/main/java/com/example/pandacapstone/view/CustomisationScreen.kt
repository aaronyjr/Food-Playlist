package com.example.pandacapstone.view

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pandacapstone.R
import com.example.pandacapstone.viewModel.UserPrefViewModel
import kotlin.math.roundToInt

@Composable
fun CustomisationScreen(onNextButtonClicked: (Int, Int, Int) -> Unit) {
    var rating = rememberSaveable { mutableIntStateOf(3) }

    val userPrefViewModel: UserPrefViewModel = viewModel()
    var priceRange = userPrefViewModel.priceRange.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(top = 30.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row() {
            Column(modifier = Modifier.padding(end = 16.dp)) {
                Row(
                    modifier = Modifier.height(52.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.txt_rating),
                        style = TextStyle(fontSize = 18.sp),
                        modifier = Modifier.padding(end = 10.dp)
                    )
                }
            }

            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.height(56.dp),
                ) {
                    var maxRating = 5
                    var currentRating: Int = rating.value

                    for (i in 1..maxRating) {
                        Icon(
                            imageVector = if (i <= currentRating) Icons.Filled.Star
                            else Icons.Filled.StarOutline,
                            contentDescription = null,
                            tint = if (i <= currentRating) colorResource(id = R.color.party_pink)
                            else Color.LightGray,
                            modifier = Modifier
                                .clickable {
                                    rating.value = i
                                    Log.i("capstone", rating.value.toString())
                                }
                                .padding(4.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row() {
            Column() {
                Text(
                    text = stringResource(id = R.string.txt_price),
                    style = TextStyle(fontSize = 18.sp),
                    modifier = Modifier.padding(end = 10.dp)
                )
                RangeSlider(
                    value = priceRange,
                    onValueChange = { newValues ->
                        userPrefViewModel.updatePriceRange(newValues)
                    },
                    onValueChangeFinished = {

                    },
                    valueRange = 1f..20f,
                    steps = 0,
                    colors = SliderDefaults.colors(
                        activeTrackColor = colorResource(id = R.color.party_pink),
                        thumbColor = colorResource(id = R.color.party_pink)
                    )
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    Text(text = "$${priceRange.start.roundToInt()}")
                    Text(text = "$${priceRange.endInclusive.roundToInt()}")
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { onNextButtonClicked(rating.value, priceRange.start.roundToInt(), priceRange.endInclusive.roundToInt()) },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(
                    id = R.color.party_pink
                )
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 30.dp)
        ) {
            Text(
                text = stringResource(id = R.string.btn_submit),
                modifier = Modifier.padding(10.dp),
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight(700))
            )
        }
    }
}
