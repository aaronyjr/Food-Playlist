package com.example.pandacapstone.view

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import com.example.pandacapstone.R
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
        val textState = remember { mutableStateOf(TextFieldValue("")) }
        val selectedCuisine = rememberSaveable { mutableStateOf<String?>(null) }

        SearchTextField(
            state = textState,
            placeHolder = stringResource(id = R.string.txt_cuisine),
            modifier = modifier,
            icon = Icons.Default.Search
        )

        val searchedText = textState.value.text

        // Show extra text when search does not match any cuisines
        val filteredList = list.filter {
            it.startsWith(searchedText, ignoreCase = true)
        }

        if (filteredList.isEmpty()) {
            CuisineListEmptyState()
        } else {
            LazyColumn {
                items(items = list.filter {
                    it.startsWith(searchedText, ignoreCase = true)
                }, key = { it }) { item ->
                    ColumnItem(foodItem = item, selectedCuisine = selectedCuisine) {
                        // TO DO: Click menu to pass information
                        onNextButtonClicked(item)
                    }
                }
            }
        }
    }
}

@Composable
fun ColumnItem(
    foodItem: String,
    selectedCuisine: MutableState<String?>,
    onClick: () -> Unit
) {
    var isSelected by rememberSaveable { mutableStateOf(false) }
    isSelected = selectedCuisine.value == foodItem

    Column(
        modifier = Modifier
            .padding(2.dp)
            .clickable {
                isSelected = !isSelected
                if (isSelected) {
                    selectedCuisine.value = foodItem
                    onClick()
                } else {
                    selectedCuisine.value = null
                }
            }
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Text(text = foodItem, modifier = Modifier.padding(top = 16.dp, bottom = 16.dp), fontSize = 14.sp)
            Spacer(modifier = Modifier.width(4.dp))
            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Selected",
                    modifier = Modifier.size(24.dp),
                    tint = colorResource(id = R.color.party_pink)
                )
            }
        }
        Divider(color = colorResource(id = R.color.cool_grey))

    }
}

@Composable
fun SearchTextField(
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
        ),
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = "Search"
            )
        },
        trailingIcon = {
            if (placeHolder.isNotEmpty()) {
                IconButton(onClick = { state.value = TextFieldValue("") }) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Clear button")
                }
            }
        }
    )
}

@Composable
fun CuisineListEmptyState(
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = "No cuisines found",
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = "Try adjusting your search",
            style = MaterialTheme.typography.bodyMedium
        )
    }
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

    val sortedCuisineList = cuisineList.sortedBy { it.firstOrNull() }

    return sortedCuisineList
}


