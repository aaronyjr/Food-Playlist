package com.example.pandacapstone

import android.media.Image
import android.util.Log
import androidx.annotation.StringRes
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
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

    Column {
       // Everything
        DietTypeCard(
            onNextButtonClicked,
            dietType = stringResource(id = R.string.diet_everything),
            dietTypeDesc = stringResource(id = R.string.diet_everything_desc),
            dietTypeIcon = painterResource(id = R.drawable.everything_icon)
        )

        // Vegetarian
        DietTypeCard(
            onNextButtonClicked,
            dietType = stringResource(id = R.string.diet_vegetarian),
            dietTypeDesc = stringResource(id = R.string.diet_vegetarian_desc),
            dietTypeIcon = painterResource(id = R.drawable.vegetarian_icon)
        )

        // Vegan
        DietTypeCard(
            onNextButtonClicked,
            dietType = stringResource(id = R.string.diet_vegan),
            dietTypeDesc = stringResource(id = R.string.diet_vegan_desc),
            dietTypeIcon = painterResource(id = R.drawable.vegan_icon)
        )

        // Halal
        DietTypeCard(
            onNextButtonClicked,
            dietType = stringResource(id = R.string.diet_halal),
            dietTypeDesc = stringResource(id = R.string.diet_halal_desc),
            dietTypeIcon = painterResource(id = R.drawable.halal_icon)
        )
    }
}

@Composable
fun DietTypeCard(onNextButtonClicked: () -> Unit,
                 dietType: String ,
                 dietTypeDesc: String,
                 dietTypeIcon: Painter
                 ) {
    var isPressed = remember { mutableStateOf(false) }
    var inputDietType = remember { mutableStateOf("") }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp)
            .clickable(onClick = {
                onNextButtonClicked()
                isPressed.value = !isPressed.value
                inputDietType.value = dietType
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
                    text = dietType,
                    style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold)
                )
                Text(
                    text = dietTypeDesc,
                    style = TextStyle(fontSize = 14.sp, color = Color.Gray)
                )
            }
            Box(modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = dietTypeIcon,
                    contentDescription = dietType + "logo",
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

