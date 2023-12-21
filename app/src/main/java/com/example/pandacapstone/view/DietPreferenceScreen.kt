package com.example.pandacapstone.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pandacapstone.R
import com.example.pandacapstone.viewModel.UserPrefViewModel


@Composable
fun DietPreferenceScreen(
    onNextButtonClicked: (String) -> Unit
) {
    Column() {
        val userPrefViewModel: UserPrefViewModel = viewModel()

        DietTypeCard(
            listOf(
                DietType(
                    stringResource(id = R.string.diet_everything),
                    stringResource(id = R.string.diet_everything_desc),
                    painterResource(id = R.drawable.everything_icon)
                ),
                DietType(
                    stringResource(id = R.string.diet_vegetarian),
                    stringResource(id = R.string.diet_vegetarian_desc),
                    painterResource(id = R.drawable.vegetarian_icon)
                ),
                DietType(
                    stringResource(id = R.string.diet_vegan),
                    stringResource(id = R.string.diet_vegan_desc),
                    painterResource(id = R.drawable.vegan_icon)
                ),
                DietType(
                    stringResource(id = R.string.diet_halal),
                    stringResource(id = R.string.diet_halal_desc),
                    painterResource(id = R.drawable.halal_icon)
                )
            ), onNextButtonClicked, viewModel = userPrefViewModel
        )
    }
}

data class DietType(val dietTypeName: String, val dietTypeDesc: String, val dietTypeIcon: Painter)

@Composable
fun DietTypeCard(
    listDietType: List<DietType>,
    onNextButtonClicked: (String) -> Unit,
    viewModel: UserPrefViewModel
) {

    LazyColumn() {
        items(listDietType) { item ->
            DietTypeItem(
                onNextButtonClicked,
                dietType = item,
                isSelected = viewModel.selectedDietType.value?.let {
                    item.dietTypeName == it.dietTypeName
                } ?: false,
                onItemSelected = {
                    viewModel.onDietTypeSelected(it ?: return@DietTypeItem)
                }
            )
        }
    }
}

@Composable
fun DietTypeItem(
    onNextButtonClicked: (String) -> Unit,
    dietType: DietType,
    isSelected: Boolean,
    onItemSelected: (DietType?) -> Unit,
) {

    var inputDietType by rememberSaveable { mutableStateOf("") }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp)
            .clickable {
                onItemSelected(dietType)
                inputDietType = dietType.dietTypeName
                onNextButtonClicked(inputDietType)
            },
        colors = CardDefaults.cardColors(Color.White),
        border = BorderStroke(
            2.dp,
            if (isSelected) colorResource(id = R.color.party_pink) else colorResource(id = R.color.cool_grey)
        )

    ) {
        Row(modifier = Modifier.padding(16.dp, 20.dp)) {
            Column() {
                Text(
                    modifier = Modifier.padding(bottom = 3.dp),
                    text = dietType.dietTypeName,
                    style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold)
                )
                Text(
                    text = dietType.dietTypeDesc,
                    style = TextStyle(fontSize = 14.sp, color = Color.Gray)
                )
            }
            Box(modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = dietType.dietTypeIcon,
                    contentDescription = dietType.dietTypeName + "logo",
                    modifier = Modifier
                        .size(60.dp)
                        .align(
                            Alignment.CenterEnd
                        )
                )
            }
        }
    }
}

