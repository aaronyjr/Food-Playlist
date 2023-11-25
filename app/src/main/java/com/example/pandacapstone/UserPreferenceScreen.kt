package com.example.pandacapstone

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

// enum values that represent the screens in the app
enum class UserPreferenceScreen(@StringRes val title: Int) {
    Start(title = R.string.playlist_intro),
    DietPreference(title = R.string.diet_preference),
    FoodPreference(title = R.string.food_preference),
    FreqTimePreference(title = R.string.freq_time_preference),
    Custom(title = R.string.custom)
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
        // modifier = modifier,
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
            if (currentScreen != UserPreferenceScreen.Start) {
                IconButton(onClick = onNextButtonClicked) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Select",
                        tint = colorResource(id = R.color.party_pink),
                        modifier = Modifier.fillMaxSize()
                    )
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
                }
            },
            currentScreen = currentScreen
        )
        when(currentScreen) {
             UserPreferenceScreen.DietPreference -> Step(1)
             UserPreferenceScreen.FoodPreference -> Step(2)
             UserPreferenceScreen.FreqTimePreference -> Step(3)
             UserPreferenceScreen.Custom -> Step(4)
            else -> ""
        }

        NavHost(
            navController = navController,
            startDestination = UserPreferenceScreen.Start.name,
            //modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = UserPreferenceScreen.Start.name) {
                PlaylistIntro(onNextButtonClicked = {navController.navigate(UserPreferenceScreen.DietPreference.name)})
            }
            composable(route = UserPreferenceScreen.DietPreference.name) {
                DietPreferenceScreen()
            }
            composable(route = UserPreferenceScreen.FoodPreference.name) {
                FoodPreferenceScreen()
            }
            composable(route = UserPreferenceScreen.FreqTimePreference.name) {
                FreqTimePreferenceScreen()
            }
        }
    }
}