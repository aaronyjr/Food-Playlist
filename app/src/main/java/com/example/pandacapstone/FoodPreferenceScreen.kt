package com.example.pandacapstone

import android.graphics.drawable.Icon
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pandacapstone.model.UserPreferences

@Composable
fun FoodPreferenceScreen(
    userPreferences: UserPreferences,
    onNextButtonClicked: (String) -> Unit
) {
    SearchViewCuisine(onNextButtonClicked, list = generateCuisineList())
}


@Composable
fun SearchViewCuisine(
    onNextButtonClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
    list: List<String>
) {

    Column(modifier.fillMaxSize()) {
        var inputFoodPreference = remember { mutableStateOf("") }
        val textState = remember { mutableStateOf(TextFieldValue("")) }

        SearchViewCuisine(
            state = textState,
            placeHolder = stringResource(id = R.string.txt_cuisine),
            modifier = modifier,
            icon = Icons.Default.Search
        )

        val searchedText = textState.value.text

        LazyColumn() {
            items(items = list.filter {
                it.contains(searchedText, ignoreCase = true)
            }, key = { it }) { item ->
                ColumnItem(foodItem = item) {
                    // TO DO: Click menu to pass information
                    onNextButtonClicked(item)
                }
            }
        }
    }
}

@Composable
fun ColumnItem(
    foodItem: String,
    onClick: () -> Unit
) {
    var isSelected by remember { mutableStateOf(false) }
    var inputCuisinePreference by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(2.dp)
            .clickable {
                // When the item is clicked, toggle isSelected and call onClick
                isSelected = !isSelected
                inputCuisinePreference = foodItem
                Log.i("foodpanda", inputCuisinePreference)
                onClick()
            }
            // Set the background color based on whether the item is selected
            .background(if (isSelected) Color.LightGray else Color.Transparent)
    ) {
        Text(text = foodItem, modifier = Modifier.padding(top = 16.dp, bottom = 16.dp), fontSize = 14.sp)
        Divider(color = colorResource(id = R.color.cool_grey))
    }
}

@Composable
fun SearchViewCuisine(
    state: MutableState<TextFieldValue>,
    placeHolder: String,
    modifier: Modifier,
    icon: ImageVector
) {
    TextField(
        value = state.value,
        onValueChange = { value ->
            state.value = value
        },
        modifier
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 10.dp)
            .clip(RoundedCornerShape(5.dp))
            .border(2.dp, colorResource(id = R.color.cool_grey), RoundedCornerShape(5.dp)),
        placeholder = {
            Text(text = placeHolder)
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.White,
        ),
        maxLines = 1,
        singleLine = true,
        textStyle = TextStyle(
            color = Color.Black, fontSize = 18.sp
        )
    )
}

fun generateCuisineList(): List<String> {
    val cuisineList = listOf(
        "Mochi",
        "Waffle",
        "African",
        "American",
        "Southern American",
        "Cajun",
        "Tex-Mex",
        "Native American",
        "Chinese",
        "Indian",
        "Japanese",
        "Korean",
        "Thai",
        "Vietnamese",
        "Indonesian",
        "Malaysian",
        "Filipino",
        "Singaporean",
        "Sri Lankan",
        "Bangladeshi",
        "Nepali",
        "Burmese",
        "Mongolian",
        "Cambodian",
        "Laotian",
        "Jamaican",
        "Cuban",
        "Haitian"
    )
    return cuisineList
}


