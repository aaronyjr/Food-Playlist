package com.example.pandacapstone

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
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
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pandacapstone.model.UserPreferences
import java.time.LocalTime
import java.time.format.DateTimeFormatter

// Requires API 26 because of function generateDeliveryTime()
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FreqTimePreferenceScreen() {
    var userPrefViewModel: UserPrefViewModel = viewModel()

    Column(Modifier.fillMaxSize()) {
        // Select
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
            ExposedDropDownMenuWeeks(userPrefViewModel)

            Text(
                text = stringResource(id = R.string.txt_weeks),
                fontSize = 18.sp
            )
        }

        DaySelection(userPrefViewModel)

        Spacer(modifier = Modifier.height(16.dp))

        ExposedDropDownMenuTime()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DaySelection(userPrefViewModel: UserPrefViewModel) {
    val listOfdays = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
    val booleanSelectedDay = remember { mutableStateListOf(false, false, false, false, false, false, false) }

    FlowRow(
        modifier = Modifier.fillMaxWidth()
    ) {
        listOfdays.forEachIndexed { index, day ->
            Box(modifier = Modifier.padding(2.dp)) {
                ToggleDaysButton(
                    text = day,
                    isSelected = booleanSelectedDay[index],
                    onToggle = {
                        booleanSelectedDay[index] = it
                        var selectedDay = when (day) {
                            "Mon" -> "Monday"
                            "Tue" -> "Tuesday"
                            "Wed" -> "Wednesday"
                            "Thu" -> "Thursday"
                            "Fri" -> "Friday"
                            "Sat" -> "Saturday"
                            "Sun" -> "Sunday"
                            else -> ""
                        }
                        userPrefViewModel.calDeliveryDate(selectedDay)
                    },
                )
            }
        }
    }
}

// Button to toggle days
@Composable
fun ToggleDaysButton(text: String, isSelected: Boolean, onToggle: (Boolean) -> Unit) {
    Button(
        modifier = Modifier
            .width(90.dp),
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
        Text(text = text, fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
}

// Menu to set number of weeks
@Composable
fun ExposedDropDownMenuWeeks(userPrefViewModel: UserPrefViewModel) {

    // Declaring a boolean value to store
    // the expanded state of the Text Field
    var mExpanded by remember { mutableStateOf(false) }
    // Create a list of cities
    val weekList = listOf("1", "2", "3", "4")
    // Create a string value to store the selected city
    var mSelectedText by rememberSaveable { mutableStateOf("1") }
    var mTextFieldSize by remember { mutableStateOf(Size.Zero) }

    // Up Icon when expanded and down icon when collapsed
    val icon = if (mExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

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
            value = mSelectedText,
            onValueChange = { mSelectedText = it },
            modifier = Modifier
                .width(80.dp)
                .onGloballyPositioned { coordinates ->
                    // This value is used to assign to
                    // the DropDown the same width
                    mTextFieldSize = coordinates.size.toSize()
                }
                .clickable { mExpanded = !mExpanded },
            trailingIcon = {
                Icon(
                    icon, "contentDescription",
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
            modifier = Modifier
                .width(with(LocalDensity.current) { mTextFieldSize.width.toDp() })
        ) {
            weekList.forEach { label ->
                DropdownMenuItem(onClick = {
                    mSelectedText = label
                    userPrefViewModel.setNumOfWeeks(mSelectedText)
                    mExpanded = false
                }, text = { Text(label) }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ExposedDropDownMenuTime() {

    // Declaring a boolean value to store
    // the expanded state of the Text Field
    var mExpanded by remember { mutableStateOf(false) }
    // Create a list of cities
    val deliveryTimeList = generateDeliveryTime()
    // Create a string value to store the selected city
    var mSelectedText by rememberSaveable { mutableStateOf("12:00 pm - 12:15pm") }
    var mTextFieldSize by remember { mutableStateOf(Size.Zero) }

    // Up Icon when expanded and down icon when collapsed
    val icon = if (mExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown
    Column {
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
                value = mSelectedText,
                onValueChange = { mSelectedText = it },
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
                        icon, "contentDescription",
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
                        mSelectedText = label
                        mExpanded = false
                    }, text = { Text(text = label, fontSize = 16.sp, fontWeight = FontWeight.Bold) }
                    )
                }
            }
        }
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
        // sorry i got OCD for the capitalization of the AM and PM -gw
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
