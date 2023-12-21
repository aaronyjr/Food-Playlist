package com.example.pandacapstone

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
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
import kotlin.math.roundToInt


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomisationScreen(onNextButtonClicked: (Int, String, Int, Int, Int) -> Unit) {
    var quantity = rememberSaveable { mutableIntStateOf(1) }
    var mealOptions: List<String> = listOf("Yes", "No")
    var meal = rememberSaveable { mutableStateOf(mealOptions[0]) }
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
//                Row(
//                    modifier = Modifier.height(52.dp),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Text(
//                        text = stringResource(id = R.string.txt_quantity),
//                        style = TextStyle(fontSize = 18.sp),
//                        modifier = Modifier.padding(end = 10.dp)
//                    )
//                }

//                Spacer(modifier = Modifier.height(10.dp))
//                Row(
//                    modifier = Modifier.height(52.dp),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Text(
//                        text = stringResource(id = R.string.txt_meal),
//                        style = TextStyle(fontSize = 18.sp),
//                        modifier = Modifier.padding(end = 10.dp)
//                    )
//                }
//
//                Spacer(modifier = Modifier.height(10.dp))

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
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    IconButton(
//                        onClick = { quantity.value -= 1 },
//                        colors = IconButtonDefaults.iconButtonColors(
//                            contentColor = colorResource(id = R.color.party_pink),
//                            disabledContainerColor = Color.Transparent,
//                            disabledContentColor = Color.LightGray
//                        ),
//                        enabled = quantity.value >= 2,
//                      modifier = Modifier
//                            .border(1.dp, Color.LightGray, shape = RoundedCornerShape(10.dp))
//                            .size(width = 40.dp, height = 40.dp)
//                    ) {
//                        Icon(
//                            imageVector = Icons.Default.Remove,
//                            contentDescription = "Minus",
//                            modifier = Modifier.size(width = 20.dp, height = 20.dp)
//                        )
//                    }
//
//                    Text(text = quantity.value.toString(), modifier = Modifier.padding(16.dp))
//
//                    IconButton(
//                        onClick = { quantity.value += 1 },
//                        colors = IconButtonDefaults.iconButtonColors(
//                            contentColor = colorResource(id = R.color.party_pink)
//                        ),
//                        modifier = Modifier
//                            .border(1.dp, Color.LightGray, shape = RoundedCornerShape(10.dp))
//                            .size(width = 40.dp, height = 40.dp)
//                    ) {
//                        Icon(
//                            imageVector = Icons.Default.Add,
//                            contentDescription = "Add",
//                            modifier = Modifier.size(width = 20.dp, height = 20.dp)
//                        )
//                    }
//                }
//
//                Spacer(modifier = Modifier.height(10.dp))
//
//                Row(
//                    modifier = Modifier.height(52.dp),
//                ) {
//                    mealOptions.forEach { label ->
//                        Row(
//                            modifier = Modifier
//                                .height(56.dp)
//                                .selectable(
//                                    selected = (meal.value == label),
//                                    onClick = { meal.value = label },
//                                    role = Role.RadioButton
//                                )
//                                .padding(end = 16.dp),
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//                            RadioButton(
//                                modifier = Modifier.padding(end = 6.dp),
//                                selected = (meal.value == label),
//                                onClick = null, // null recommended for accessibility with screen readers
//                                colors = RadioButtonDefaults.colors(
//                                    selectedColor = colorResource(id = R.color.party_pink),
//                                )
//                            )
//                            Text(text = label)
//                        }
//                    }
//                }
//
//                Spacer(modifier = Modifier.height(10.dp))

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
                    valueRange = 1f..100f,
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
            onClick = { onNextButtonClicked(quantity.value, meal.value, rating.value, priceRange.start.roundToInt(), priceRange.endInclusive.roundToInt())},
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





