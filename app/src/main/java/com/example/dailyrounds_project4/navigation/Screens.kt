package com.example.dailyrounds_project4.navigation

sealed class Screens(val route: String) {
    object SignInScreen : Screens(route = "SignIn_Screen")
    object SignUpScreen : Screens(route = "SignUp_Screen")
    object BookScreen : Screens(route = "BookScreen")

}