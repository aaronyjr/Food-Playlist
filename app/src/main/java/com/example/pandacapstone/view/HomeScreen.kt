package com.example.pandacapstone.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.pandacapstone.R

@Composable
fun HomeScreen(onNextButtonClicked: () -> Unit = {},
) {
    Column {
        OutlinedIconButton(onClick = onNextButtonClicked,
            border = BorderStroke(1.dp, colorResource(id = R.color.party_pink))
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add",
                modifier = Modifier.size(width = 20.dp, height = 20.dp),
                tint = colorResource(id = R.color.party_pink)
            )
        }
    }

}
