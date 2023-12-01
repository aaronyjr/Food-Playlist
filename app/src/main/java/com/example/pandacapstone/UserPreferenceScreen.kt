package com.example.pandacapstone

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.maryamrzdh.stepper.Stepper

// enum values that represent the screens in the app
enum class UserPreferenceScreen(@StringRes val title: Int) {
    Start(title = R.string.playlist_intro),
    DietPreference(title = R.string.diet_preference),
    FoodPreference(title = R.string.food_preference),
    FreqTimePreference(title = R.string.freq_time_preference),
    Customisation(title = R.string.customisation_preference)
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AppBar(
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    onNextButtonClicked: () -> Unit,
    currentScreen: UserPreferenceScreen
) {
    val ctx = LocalContext.current

    TopAppBar(
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.White),
        title = { },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = colorResource(id = R.color.party_pink),
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        },
        actions = {
            if (currentScreen != UserPreferenceScreen.Start && currentScreen != UserPreferenceScreen.DietPreference) {
                IconButton(onClick = onNextButtonClicked) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Select",
                        tint = colorResource(id = R.color.party_pink),
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
            if (currentScreen == UserPreferenceScreen.Start) {
                IconButton(onClick = { }) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                }
            }
        },
    )
}

@Composable
fun UserPreference(
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = UserPreferenceScreen.valueOf(
        backStackEntry?.destination?.route ?: UserPreferenceScreen.DietPreference.name
    )

    Column {
        AppBar(
            canNavigateBack = navController.previousBackStackEntry != null,
            navigateUp = { navController.navigateUp() },
            onNextButtonClicked = {
                if (currentScreen == UserPreferenceScreen.DietPreference) {
                    navController.navigate(UserPreferenceScreen.FoodPreference.name)
                } else if (currentScreen == UserPreferenceScreen.FoodPreference) {
                    navController.navigate(UserPreferenceScreen.FreqTimePreference.name)
                } else if (currentScreen == UserPreferenceScreen.FreqTimePreference) {
                    navController.navigate(UserPreferenceScreen.Customisation.name)
                }
            },
            currentScreen = currentScreen
        )
        Column(
            modifier = if (currentScreen == UserPreferenceScreen.Start) Modifier
                .padding(0.dp, 0.dp)
                .fillMaxHeight()
            else Modifier
                .padding(16.dp, 0.dp, 0.dp, 16.dp)
        ) {
            when (currentScreen) {
                UserPreferenceScreen.DietPreference -> {
                    Header("diet_header")
                    Step(1)
                }

                UserPreferenceScreen.FoodPreference -> {
                    Header("food_header")
                    Step(2)
                }

                UserPreferenceScreen.FreqTimePreference -> {
                    Header("freqtime_header")
                    Step(3)
                }

                UserPreferenceScreen.Customisation -> {
                    Header("customisation_header")
                    Step(4)
                }

                else -> ""
            }

            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                NavHost(
                    navController = navController,
                    startDestination = UserPreferenceScreen.Start.name,
                    //modifier = Modifier.padding(innerPadding)
                ) {
                    composable(route = UserPreferenceScreen.Start.name) {
                        PlaylistIntro(onNextButtonClicked = {
                            navController.navigate(
                                UserPreferenceScreen.DietPreference.name
                            )
                        })
                    }
                    composable(route = UserPreferenceScreen.DietPreference.name) {
                        DietPreferenceScreen(onNextButtonClicked = {
                            navController.navigate(
                                UserPreferenceScreen.FoodPreference.name
                            )
                        })
                    }
                    composable(route = UserPreferenceScreen.FoodPreference.name) {
                        FoodPreferenceScreen()
                    }
                    composable(route = UserPreferenceScreen.FreqTimePreference.name) {
                        FreqTimePreferenceScreen()
                    }
                    composable(route = UserPreferenceScreen.Customisation.name) {
                        CustomisationScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun Step(currentStep: Int) {

    val numberStep = 4
    // var currentStep by rememberSaveable { mutableStateOf(1) }
    val titleList = arrayListOf("Step 1", "Step 2", "Step 3", "Step 4")

    Column(modifier = Modifier.padding(bottom = 20.dp)) {
        Stepper(
            numberOfSteps = numberStep,
            currentStep = currentStep,
            stepDescriptionList = titleList,
        )
    }
}

@Composable
fun Header(headerStr: String) {
    Row() {
        Text(
            text = when (headerStr) {
                "diet_header" -> stringResource(R.string.diet_header)
                "food_header" -> stringResource(id = R.string.food_header)
                "freqtime_header" -> stringResource(id = R.string.freqtime_header)
                "customisation_header" -> stringResource(id = R.string.customisation_header)
                else -> ""
            },
            style = TextStyle(
                fontWeight = FontWeight.Bold, fontSize = 26.sp
            ),
            modifier = Modifier
                .padding(bottom = 20.dp, top = 20.dp)
                .align(Alignment.CenterVertically)
                .width(270.dp)
        )
        Box(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = when (headerStr) {
                    "diet_header" -> painterResource(id = R.drawable.pau_pondering)
                    "food_header" -> painterResource(id = R.drawable.pau_yummy)
                    "freqtime_header" -> painterResource(id = R.drawable.pau_happy)
                    "customisation_header" -> painterResource(id = R.drawable.pau_okay)
                    else -> painterResource(id = R.drawable.pau_pondering)
                },
                contentDescription = "Pau Pau pondering",
            )
        }
    }
}

