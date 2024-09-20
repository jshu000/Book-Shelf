package com.example.dailyrounds_project4.presentation.signup_screen


data class SignUpState(
    val isLoading: Boolean = false,
    val isSuccess: String? = "",
    val isError: String? = ""

)