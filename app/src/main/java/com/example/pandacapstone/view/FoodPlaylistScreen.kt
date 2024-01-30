package com.example.pandacapstone.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.pandacapstone.R
import com.example.pandacapstone.viewModel.PlaylistViewModel
import com.example.pandacapstone.viewModel.UserPrefViewModel
import com.maryamrzdh.stepper.Stepper

// enum values that represent the screens in the app
enum class FoodPlaylistScreen(@StringRes val title: Int) {
    Home(title = R.string.home),
    Start(title = R.string.playlist_intro),
    DietPreference(title = R.string.diet_preference),
    FoodPreference(title = R.string.food_preference),
    FreqTimePreference(title = R.string.freq_time_preference),
    Customisation(title = R.string.customisation_preference),
    UserInput(title = R.string.btn_submit),
    Api(title = R.string.generated_playlist)

}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AppBar(
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    currentScreen: FoodPlaylistScreen
) {
    val ctx = LocalContext.current

    TopAppBar(
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.White),
        title = { },
        navigationIcon = {
            if (canNavigateBack && currentScreen != FoodPlaylistScreen.Start) {
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
            if (currentScreen == FoodPlaylistScreen.Start) {
                IconButton(onClick = navigateUp) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                }
            }
        },
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FoodPlaylistApp(
    viewModel: UserPrefViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = FoodPlaylistScreen.valueOf(
        backStackEntry?.destination?.route ?: FoodPlaylistScreen.DietPreference.name
    )

    Column {
        AppBar(
            canNavigateBack = navController.previousBackStackEntry != null,
            navigateUp = { navController.navigateUp() },
            currentScreen = currentScreen
        )
        Column(
            modifier = if (currentScreen == FoodPlaylistScreen.Start || currentScreen == FoodPlaylistScreen.Api) Modifier
                .padding(0.dp, 0.dp)
                .fillMaxHeight()
            else Modifier
                .padding(16.dp, 0.dp, 16.dp, 16.dp)
        ) {
            when (currentScreen) {
                FoodPlaylistScreen.DietPreference -> {
                    Header(
                        stringResource(id = R.string.diet_header),
                        painterResource(id = R.drawable.pau_pondering)
                    )
                    Step(1)
                }

                FoodPlaylistScreen.FoodPreference -> {
                    Header(
                        stringResource(id = R.string.food_header),
                        painterResource(id = R.drawable.pau_yummy)
                    )
                    Step(2)
                }

                FoodPlaylistScreen.FreqTimePreference -> {
                    Header(
                        stringResource(id = R.string.freqtime_header),
                        painterResource(id = R.drawable.pau_happy)
                    )
                    Step(3)
                }

                FoodPlaylistScreen.Customisation -> {
                    Header(
                        stringResource(id = R.string.customisation_header),
                        painterResource(id = R.drawable.pau_okay)
                    )
                    Step(4)
                }

                else -> null
            }
            val userPrefState by viewModel.userPrefState.collectAsState()
            val generatedPlaylistVM: PlaylistViewModel = viewModel()


            NavHost(
                navController = navController,
                startDestination = FoodPlaylistScreen.Home.name,
            ) {
                composable(route = FoodPlaylistScreen.Home.name) {
                    HomeScreen(onNextButtonClicked = {
                        navController.navigate(
                            FoodPlaylistScreen.Start.name
                        )
                    })
                }
                composable(route = FoodPlaylistScreen.Start.name) {
                    PlaylistIntro(onNextButtonClicked = {
                        navController.navigate(
                            FoodPlaylistScreen.DietPreference.name
                        )
                    })
                }
                composable(route = FoodPlaylistScreen.DietPreference.name) {
                    DietPreferenceScreen(
                        onNextButtonClicked = {
                            viewModel.setDietType(it)
                            navController.navigate(FoodPlaylistScreen.FoodPreference.name)
                        },
                    )
                }
                composable(route = FoodPlaylistScreen.FoodPreference.name) {
                    FoodPreferenceScreen(
                        onNextButtonClicked = {
                            viewModel.setFoodPref(it)
                            navController.navigate(
                                FoodPlaylistScreen.FreqTimePreference.name
                            )
                        },
                        userPreferences = userPrefState
                    )
                }
                composable(route = FoodPlaylistScreen.FreqTimePreference.name) {
                    FreqTimePreferenceScreen(
                        onNextButtonClicked = { deliveryTime: String, deliveryDate: String, nWeek: String ->
                            viewModel.setFreqTime(deliveryTime, deliveryDate, nWeek)
                            navController.navigate(
                                FoodPlaylistScreen.Customisation.name
                            )
                        }
                    )
                }
                composable(route = FoodPlaylistScreen.Customisation.name) {
                    CustomisationScreen(
                        onNextButtonClicked = {rating: Int, minPrice: Int, maxPrice: Int ->
                            viewModel.setCustomisation(rating, minPrice, maxPrice)
                            navController.navigate(
                                FoodPlaylistScreen.Api.name
                            )
                        }
                    )
                }
                composable(route = FoodPlaylistScreen.Api.name) {
                    GeneratedPlaylistScreen(generatedPlaylistVM, userPrefState)
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
fun Header(headerStr: String, pauImg: Painter) {
    Row() {
        Text(
            text = headerStr,
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
                painter = pauImg,
                contentDescription = "Pau Pau",
            )
        }
    }
}
