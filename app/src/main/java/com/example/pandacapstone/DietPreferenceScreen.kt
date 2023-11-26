package com.example.pandacapstone

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pandacapstone.ui.theme.PandaCapstoneTheme
import com.maryamrzdh.stepper.Stepper


@Composable
fun DietPreferenceScreen(
    onNextButtonClicked: () -> Unit = {},
) {
    var isPressed = remember { mutableStateOf(false) }

    Column {
        EverythingCard(onNextButtonClicked)
        VegetarianCard(onNextButtonClicked)
        VeganCard(onNextButtonClicked)
        HalalCard(onNextButtonClicked)
    }
}

// Everything Option
@Composable
fun EverythingCard(onNextButtonClicked: () -> Unit) {
    var isPressed = remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = {
                onNextButtonClicked()
                isPressed.value = !isPressed.value
            }),
        colors = CardDefaults.cardColors(Color.White),
        border = if (isPressed.value) BorderStroke(
            2.dp,
            colorResource(id = R.color.party_pink)
        ) else BorderStroke(
            2.dp,
            colorResource(id = R.color.cool_grey)
        )
    ) {
        Row(modifier = Modifier.padding(16.dp, 20.dp)) {
            Column {
                Text(
                    modifier = Modifier.padding(bottom = 3.dp),
                    text = stringResource(R.string.diet_everything),
                    style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold)
                )
                Text(
                    text = stringResource(R.string.diet_everything_desc),
                    style = TextStyle(fontSize = 14.sp, color = Color.Gray)
                )
            }
            Box(modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(id = R.drawable.everything_icon),
                    contentDescription = "Everything logo",
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

// Vegetarian Option
@Composable
fun VegetarianCard(onNextButtonClicked: () -> Unit) {
    var isPressed = remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
            .clickable(onClick = {
                onNextButtonClicked()
                isPressed.value = !isPressed.value
            }),
        colors = CardDefaults.cardColors(Color.White),
        border = if (isPressed.value) BorderStroke(
            2.dp,
            colorResource(id = R.color.party_pink)
        ) else BorderStroke(
            2.dp,
            color = colorResource(id = R.color.cool_grey)
        )
    ) {
        Row(modifier = Modifier.padding(16.dp, 20.dp)) {
            Column {
                Text(
                    modifier = Modifier.padding(bottom = 3.dp),
                    text = stringResource(R.string.diet_vegetarian),
                    style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold)
                )
                Text(
                    text = stringResource(R.string.diet_vegetarian_desc),
                    style = TextStyle(fontSize = 14.sp, color = Color.Gray)
                )
            }
            Box(modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(id = R.drawable.vegetarian_icon),
                    contentDescription = "Vegetarian logo",
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

// Vegan Option
@Composable
fun VeganCard(onNextButtonClicked: () -> Unit) {
    val isPressed = remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
            .clickable(onClick = {
                onNextButtonClicked()
                isPressed.value = !isPressed.value
            }),
        colors = CardDefaults.cardColors(Color.White),
        border = if (isPressed.value) BorderStroke(
            2.dp,
            colorResource(id = R.color.party_pink)
        ) else BorderStroke(
            2.dp,
            color = colorResource(id = R.color.cool_grey)
        )
    ) {
        Row(modifier = Modifier.padding(16.dp, 20.dp)) {
            Column {
                Text(
                    modifier = Modifier.padding(bottom = 3.dp),
                    text = stringResource(R.string.diet_vegan),
                    style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold)
                )
                Text(
                    text = stringResource(R.string.diet_vegan_desc),
                    style = TextStyle(fontSize = 14.sp, color = Color.Gray)
                )
            }
            Box(modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(id = R.drawable.vegan_icon),
                    contentDescription = "Vegan logo",
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

// Halal Option
@Composable
fun HalalCard(onNextButtonClicked: () -> Unit) {
    val isPressed = remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
            .clickable(onClick = {
                onNextButtonClicked()
                isPressed.value = !isPressed.value
            }),
        colors = CardDefaults.cardColors(Color.White),
        border = if (isPressed.value) BorderStroke(
            2.dp,
            colorResource(id = R.color.party_pink)
        ) else BorderStroke(
            2.dp,
            color = colorResource(id = R.color.cool_grey)
        )
    ) {
        Row(modifier = Modifier.padding(16.dp, 20.dp)) {
            Column {
                Text(
                    modifier = Modifier.padding(bottom = 3.dp),
                    text = stringResource(R.string.diet_halal),
                    style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold)
                )
                Text(
                    text = stringResource(R.string.diet_halal_desc),
                    style = TextStyle(fontSize = 14.sp, color = Color.Gray)
                )
            }
            Box(modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(id = R.drawable.halal_icon),
                    contentDescription = "Halal logo",
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
