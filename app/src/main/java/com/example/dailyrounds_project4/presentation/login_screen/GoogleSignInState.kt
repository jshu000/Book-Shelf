package com.example.dailyrounds_project4.presentation.login_screen


import com.google.firebase.auth.AuthResult

data class GoogleSignInState(
    val success: AuthResult? = null,
    val loading: Boolean = false,
    val error: String = ""
)