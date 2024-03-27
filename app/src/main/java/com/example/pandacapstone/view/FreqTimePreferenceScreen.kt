package com.example.pandacapstone.view

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pandacapstone.R
import com.example.pandacapstone.viewModel.UserPrefViewModel
import java.time.LocalTime
import java.time.format.DateTimeFormatter

// Requires API 26 because of function generateDeliveryTime()
@OptIn(ExperimentalLayoutApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FreqTimePreferenceScreen(
    onNextButtonClicked: (String, String, String) -> Unit
) {
    var userPrefViewModel: UserPrefViewModel = viewModel()

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Declaring a boolean value to store
        // the expanded state of the Text Field
        var weekExpanded by remember { mutableStateOf(false) }
        // Create a list of cities
        val weekList = listOf("1", "2", "3", "4")
        // Create a string value to store the selected city
        var nWeek by rememberSaveable { mutableStateOf("1") }
        var weekTextFieldSize by remember { mutableStateOf(Size.Zero) }

        // Up Icon when expanded and down icon when collapsed
        val icon = if (weekExpanded)
            Icons.Filled.KeyboardArrowUp
        else
            Icons.Filled.KeyboardArrowDown

        // Menu to set number of weeks
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.txt_every),
                fontSize = 18.sp
            )

            Column(Modifier.padding(20.dp)) {
                // Create an Outlined Text Field
                // with icon and not expanded
                OutlinedTextField(
                    textStyle = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp),
                    colors = OutlinedTextFieldDefaults.colors(
                        disabledBorderColor = Color.LightGray,
                        disabledTextColor = Color.Black
                    ),
                    readOnly = true,
                    value = nWeek,
                    onValueChange = { nWeek = it },
                    modifier = Modifier
                        .width(80.dp)
                        .onGloballyPositioned { coordinates ->
                            // This value is used to assign to
                            // the DropDown the same width
                            weekTextFieldSize = coordinates.size.toSize()
                        }
                        .clickable { weekExpanded = !weekExpanded },
                    trailingIcon = {
                        Icon(
                            icon,
                            "contentDescription",
                            Modifier.clickable { weekExpanded = !weekExpanded },
                            tint = colorResource(id = R.color.party_pink)
                        )
                    },
                    enabled = false
                )

                // Create a drop-down menu with list of cities,
                // when clicked, set the Text Field text as the city selected
                DropdownMenu(
                    expanded = weekExpanded,
                    onDismissRequest = { weekExpanded = false },
                    modifier = Modifier
                        .width(with(LocalDensity.current) { weekTextFieldSize.width.toDp() })
                ) {
                    weekList.forEach { label ->
                        DropdownMenuItem(onClick = {
                            nWeek = label
                            weekExpanded = false
                        }, text = { Text(label) }
                        )
                    }
                }
            }
            Text(
                text = stringResource(id = R.string.txt_weeks),
                fontSize = 18.sp
            )
        }

        // Select days
        val listOfdays = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
        val selectedDays = userPrefViewModel.selectedDays
        var selectedDay by rememberSaveable { mutableStateOf("") }
        Log.i("selected", selectedDay)

        FlowRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            listOfdays.forEachIndexed { index, day ->
                Box(modifier = Modifier.padding(2.dp)) {
                    ToggleDaysButton(
                        text = day,
                        isSelected = selectedDays.value == index,
                        onToggle = {
                            selectedDays.value = if (selectedDays.value == index) null else index
                            selectedDay = when (day) {
                                "Mon" -> "Monday"
                                "Tue" -> "Tuesday"
                                "Wed" -> "Wednesday"
                                "Thu" -> "Thursday"
                                "Fri" -> "Friday"
                                "Sat" -> "Saturday"
                                "Sun" -> "Sunday"
                                else -> ""
                            }
                        },
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // select delivery time
        var mExpanded by remember { mutableStateOf(false) }
        // Create a list of cities
        val deliveryTimeList = generateDeliveryTime()
        // Create a string value to store the selected city
        var deliveryTime by rememberSaveable { mutableStateOf("12:00 pm - 12:15 pm") }
        var mTextFieldSize by remember { mutableStateOf(Size.Zero) }

        // Up Icon when expanded and down icon when collapsed
        val deliveryIcon = if (mExpanded)
            Icons.Filled.KeyboardArrowUp
        else
            Icons.Filled.KeyboardArrowDown
        Column(modifier = Modifier.fillMaxHeight()) {
            Text(text = "Delivery Time", fontSize = 18.sp)

            BoxWithConstraints(
                modifier = Modifier
                    .clipToBounds()
            ) {
                // Create an Outlined Text Field
                // with icon and not expanded
                TextField(
                    textStyle = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp),
                    colors = TextFieldDefaults.colors(
                        disabledContainerColor = Color.White,
                        disabledTextColor = Color.Black,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    readOnly = true,
                    value = deliveryTime,
                    onValueChange = { deliveryTime = it },
                    modifier = Modifier
                        .requiredWidth(maxWidth + 16.dp)
                        .offset(x = (-8).dp, y = (-8).dp)
                        .onGloballyPositioned { coordinates ->
                            // This value is used to assign to
                            // the DropDown the same width
                            mTextFieldSize = coordinates.size.toSize()
                        }
                        .clickable { mExpanded = !mExpanded },
                    trailingIcon = {
                        Icon(
                            deliveryIcon, "contentDescription",
                            Modifier.clickable { mExpanded = !mExpanded }, tint = colorResource(id = R.color.party_pink)
                        )
                    },
                    enabled = false
                )

                // Create a drop-down menu with list of cities,
                // when clicked, set the Text Field text as the city selected
                DropdownMenu(
                    expanded = mExpanded,
                    onDismissRequest = { mExpanded = false },
                    // Set expanded list maxHeight
                    modifier = Modifier
                        .requiredSizeIn(maxHeight = 300.dp)
                        .width(with(LocalDensity.current) { mTextFieldSize.width.toDp() })
                ) {
                    deliveryTimeList.forEach { label ->
                        DropdownMenuItem(onClick = {
                            deliveryTime = label
                            mExpanded = false
                        }, text = { Text(text = label, fontSize = 16.sp, fontWeight = FontWeight.Bold) }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // next button
        Button(
            onClick = { onNextButtonClicked(selectedDay, deliveryTime, nWeek) },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(
                    id = R.color.party_pink
                )
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 30.dp)
                .let { if (selectedDays.value != null) it.shadow(8.dp) else it }, // Add shadow here
            enabled = selectedDays.value != null,
        ) {
            Text(
                text = stringResource(id = R.string.btn_next),
                modifier = Modifier.padding(10.dp),
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight(700))
            )
        }
    }
}


// Button to toggle days
@Composable
fun ToggleDaysButton(text: String, isSelected: Boolean, onToggle: (Boolean) -> Unit) {
    Button(
        modifier = Modifier
            .width(85.dp),
        shape = RoundedCornerShape(10.dp),
        onClick = { onToggle(!isSelected) },
        border = if (isSelected) BorderStroke(1.dp, colorResource(id = R.color.party_pink)) else BorderStroke(
            1.dp,
            Color.LightGray
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) colorResource(id = R.color.party_pink) else Color.White,
            contentColor = if (isSelected) Color.White else colorResource(id = R.color.black)
        ),
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun generateTimeRangeList(startTime: LocalTime, endTime: LocalTime, intervalMinutes: Int): List<String> {
    val timeList = mutableListOf<String>()
    var currentTime = startTime

    while (currentTime.isBefore(endTime) || currentTime == endTime) {
        val endTimeOfRange = currentTime.plusMinutes(intervalMinutes.toLong())
        var formattedTimeRange = "${currentTime.format(DateTimeFormatter.ofPattern("hh:mm a"))} " +
            "- ${endTimeOfRange.format(DateTimeFormatter.ofPattern("hh:mm a"))}"
        formattedTimeRange = formattedTimeRange.replace("AM", "am").replace("PM", "pm")
        timeList.add(formattedTimeRange)
        currentTime = endTimeOfRange
    }

    return timeList
}

@RequiresApi(Build.VERSION_CODES.O)
fun generateDeliveryTime(): List<String> {
    // Change start and end time for delivery timing selection here
    val startTime = LocalTime.of(8, 0)
    val endTime = LocalTime.of(23, 0)
    val intervalMinutes = 15             // Interval in minutes

    return generateTimeRangeList(startTime, endTime, intervalMinutes)
}
