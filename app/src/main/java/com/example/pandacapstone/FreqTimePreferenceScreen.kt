package com.example.pandacapstone

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.zIndex
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FreqTimePreferenceScreen() {
    val monthlyView = remember { mutableStateOf(true) }
    val weeklyView = remember { mutableStateOf(false) }

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
                // test test test germaine is very pro
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
            MonthlyField()
        }
        if (weeklyView.value) {
            WeeklyField()
        }
    }
}

@Composable
fun MonthlyField() {
    Text("Monthly")
}

// Requires API 26 because of function generateDeliveryTime()
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeeklyField() {
    Column(Modifier.fillMaxSize()) {
        // Select
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.every),
            )
            ExposedDropDownMenuWeeks()

            Text(
                text = stringResource(id = R.string.weeks),
            )
        }

        DaySelection()

        Spacer(modifier = Modifier.height(16.dp))

        ExposedDropDownMenuTime()
    }
}

@Composable
fun DaySelection() {
    val weekdays = listOf("Mon", "Tue", "Wed", "Thu")
    val weekends = listOf("Fri", "Sat", "Sun")

    val selectedWeekdays = remember { mutableStateListOf(false, false, false, false) }
    val selectedWeekends = remember { mutableStateListOf(false, false, false) }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 8.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            weekdays.forEachIndexed { index, day ->
                ToggleDaysButton(
                    text = day,
                    isSelected = selectedWeekdays[index],
                    onToggle = { selectedWeekdays[index] = it },
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 8.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            weekends.forEachIndexed { index, day ->
                ToggleDaysButton(
                    text = day,
                    isSelected = selectedWeekends[index],
                    onToggle = { selectedWeekends[index] = it }
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}

// Button to toggle days
@Composable
fun ToggleDaysButton(text: String, isSelected: Boolean, onToggle: (Boolean) -> Unit) {
    Button(
        modifier = Modifier
            .width(80.dp),
        shape = RoundedCornerShape(10.dp),
        onClick = { onToggle(!isSelected) },
        border = if (isSelected) BorderStroke(1.dp, colorResource(id = R.color.party_pink)) else BorderStroke(1.dp, Color.LightGray),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) colorResource(id = R.color.party_pink) else Color.White,
            contentColor = if (isSelected) Color.White else colorResource(id = R.color.black)
        ),
    ) {
        Text(text = text)
    }
}

// Menu to set number of weeks
@Composable
fun ExposedDropDownMenuWeeks(){

    // Declaring a boolean value to store
    // the expanded state of the Text Field
    var mExpanded by remember { mutableStateOf(false) }
    // Create a list of cities
    val weekList = listOf("1", "2", "3", "4")
    // Create a string value to store the selected city
    var mSelectedText by remember { mutableStateOf("1") }
    var mTextFieldSize by remember { mutableStateOf(Size.Zero)}

    // Up Icon when expanded and down icon when collapsed
    val icon = if (mExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column(Modifier.padding(20.dp)) {
        // Create an Outlined Text Field
        // with icon and not expanded
        OutlinedTextField(
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.LightGray,
                unfocusedBorderColor = Color.LightGray,
            ),
            readOnly = true,
            value = mSelectedText,
            onValueChange = { mSelectedText = it },
            modifier = Modifier
                .width(80.dp)
                .onGloballyPositioned { coordinates ->
                    // This value is used to assign to
                    // the DropDown the same width
                    mTextFieldSize = coordinates.size.toSize()
                },
//            label = {Text("Label")},
            trailingIcon = {
                Icon(icon,"contentDescription",
                    Modifier.clickable { mExpanded = !mExpanded })
            }
        )

        // Create a drop-down menu with list of cities,
        // when clicked, set the Text Field text as the city selected
        DropdownMenu(
            expanded = mExpanded,
            onDismissRequest = { mExpanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current){mTextFieldSize.width.toDp()})
        ) {
            weekList.forEach { label ->
                DropdownMenuItem(onClick = {
                    mSelectedText = label
                    mExpanded = false
                }, text = { Text(label) }
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ExposedDropDownMenuTime(){

    // Declaring a boolean value to store
    // the expanded state of the Text Field
    var mExpanded by remember { mutableStateOf(false) }
    // Create a list of cities
    val deliveryTimeList = generateDeliveryTime()
    // Create a string value to store the selected city
    var mSelectedText by remember { mutableStateOf("12:00 pm - 12:15pm") }
    var mTextFieldSize by remember { mutableStateOf(Size.Zero)}

    // Up Icon when expanded and down icon when collapsed
    val icon = if (mExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column {
        // Create an Outlined Text Field
        // with icon and not expanded
        OutlinedTextField(
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.LightGray,
                unfocusedBorderColor = Color.LightGray,
            ),
            readOnly = true,
            value = mSelectedText,
            onValueChange = { mSelectedText = it },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    // This value is used to assign to
                    // the DropDown the same width
                    mTextFieldSize = coordinates.size.toSize()
                },
            label = { Text("Delivery Time") },
            trailingIcon = {
                Icon(icon, "contentDescription",
                    Modifier.clickable { mExpanded = !mExpanded })
            }
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
                    mSelectedText = label
                    mExpanded = false
                }, text = { Text(label) }
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun generateDeliveryTime(): List<String> {
    @RequiresApi(Build.VERSION_CODES.O)
    fun generateTimeRangeList(startTime: LocalTime, endTime: LocalTime, intervalMinutes: Int): List<String> {
        val timeList = mutableListOf<String>()
        var currentTime = startTime

        while (currentTime.isBefore(endTime) || currentTime == endTime) {
            val endTimeOfRange = currentTime.plusMinutes(intervalMinutes.toLong())
            var formattedTimeRange = "${currentTime.format(DateTimeFormatter.ofPattern("hh:mm a"))} " +
                "- ${endTimeOfRange.format(DateTimeFormatter.ofPattern("hh:mm a"))}"
            // sorry i got OCD for the capitalization of the AM and PM -gw
            formattedTimeRange = formattedTimeRange.replace("AM", "am").replace("PM", "pm")
            timeList.add(formattedTimeRange)
            currentTime = endTimeOfRange
        }

        return timeList
    }
    // Change start and end time for delivery timing selection here
    val startTime = LocalTime.of(8, 0)
    val endTime = LocalTime.of(23, 0)
    val intervalMinutes = 15             // Interval in minutes

    return generateTimeRangeList(startTime, endTime, intervalMinutes)
}

