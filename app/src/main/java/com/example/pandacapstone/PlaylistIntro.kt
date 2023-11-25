package com.example.pandacapstone

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

@Composable
fun PlaylistIntro(
    onNextButtonClicked: () -> Unit = {},
) {
    Column {
        Button(
            onClick = onNextButtonClicked
        ) {
            Text(stringResource(id = R.string.btn_get_started))
        }
    }
}