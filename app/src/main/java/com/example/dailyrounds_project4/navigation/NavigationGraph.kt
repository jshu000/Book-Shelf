package com.example.dailyrounds_project4.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dailyrounds_project4.SignInScreen
import com.example.dailyrounds_project4.presentation.signup_screen.SignUpScreen
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun NavigationGraph(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,               //The NavController is the central API for the Navigation component. It is stateful and keeps track of the back stack of composables that make up the screens in your app and the state of each screen.
        startDestination = Screens.SignUpScreen.route
    ) {
        composable(route = Screens.SignInScreen.route) {
            SignInScreen(navController)

        }
        composable(route = Screens.SignUpScreen.route) {
            SignUpScreen(navController)

        }
    }

}