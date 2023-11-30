package com.example.pandacapstone

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

@Composable
fun FreqTimePreferenceScreen() {
    var monthlyView = remember { mutableStateOf(true) }
    var weeklyView = remember { mutableStateOf(false) }

    Column {
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = {
                    monthlyView.value = true
                    weeklyView.value = false
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (monthlyView.value) colorResource(
                        id = R.color.party_pink
                    ) else colorResource(id = R.color.cool_grey),
                    contentColor = if (monthlyView.value) Color.White else Color.LightGray
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = (if (monthlyView.value) Modifier.zIndex(1F) else Modifier.zIndex(0F))
            ) {
                Text("Monthly")
                Modifier.padding(12.dp)
            }
            Button(
                onClick = {
                    weeklyView.value = true
                    monthlyView.value = false
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor =  colorResource(
                        id = if (weeklyView.value) R.color.party_pink
                     else R.color.cool_grey),
                    contentColor = if (weeklyView.value) Color.White else Color.LightGray
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.offset((-10).dp)
            )
            {
                Text("Weekly")
                Modifier.padding(12.dp)
            }
        }
        if (monthlyView.value) {
            Text("Monthly")
        }
        if (weeklyView.value) {
            Text("Weekly")
        }
    }
}

@Composable
fun monthlyField() {
    Text("Monthly")
}
