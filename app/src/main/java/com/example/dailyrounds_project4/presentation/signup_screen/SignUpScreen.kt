package com.example.dailyrounds_project4.presentation.signup_screen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.dailyrounds_project4.R
import com.example.dailyrounds_project4.models.CountryItem
import com.example.dailyrounds_project4.navigation.Screens
import com.example.dailyrounds_project4.ui.theme.RegularFont
import com.example.dailyrounds_project4.ui.theme.lightBlue
import kotlinx.coroutines.launch


@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    var selectedCountry by remember { mutableStateOf<CountryItem?>(null) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val state = viewModel.signUpState.collectAsState(initial = null)
    val countries = viewModel.loadCountries(context = LocalContext.current)
    var expanded by remember { mutableStateOf(false) }

    fun isPasswordValid(password: String): Boolean {
        val passwordPattern = Regex("^(?=.*[0-9])(?=.*[!@#$%^&*()])(?=.*[a-z])(?=.*[A-Z]).{8,}$")
        return passwordPattern.matches(password)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 30.dp, end = 30.dp)
            .verticalScroll(rememberScrollState()),
    verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(bottom = 10.dp),
            text = "Create Account",
            fontWeight = FontWeight.Bold,
            fontSize = 35.sp,
            fontFamily = RegularFont,
        )
        Text(
            text = "Enter your credential's to register",
            fontWeight = FontWeight.Medium,
            fontSize = 15.sp, color = Color.Gray,
            fontFamily = RegularFont,

            )
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = email,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = lightBlue,
                cursorColor = Color.Black,
                disabledLabelColor = lightBlue,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            onValueChange = {
                email = it

            },
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            placeholder = {
                Text(text = "Email")
            }
        )

        Spacer(modifier = Modifier.height(16.dp))
        // Password Input Field
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            onValueChange = { newPassword ->
                password = newPassword
                if (passwordError.isNotEmpty()) {
                    passwordError = "" // Clear error message while typing
                }
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = lightBlue,
                cursorColor = Color.Black,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            placeholder = {
                Text(text = "Password")
            }
        )

        // Show error message if validation fails
        if (passwordError.isNotEmpty()) {
            Text(
                text = passwordError,
                color = Color.Red,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(top = 4.dp) // Add padding for spacing
            )
        }

        //country
        Spacer(modifier = Modifier.height(16.dp))
        // Dropdown Menu
        Box(modifier = Modifier.fillMaxWidth()) {
            TextField(
                readOnly = true,
                value = selectedCountry?.country ?: "Select a country",
                onValueChange = {},
                label = { Text("Country") },
                trailingIcon = {
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Dropdown Icon"
                        )
                    }
                }
            )

            // Dropdown Menu
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                countries.forEach { country ->
                    DropdownMenuItem(onClick = {
                        selectedCountry = country
                        expanded = false
                    }) {
                        Text(text = country.country)
                    }
                }
            }
        }
        Button(
            onClick = {
                if (isPasswordValid(password)) {
                    // Proceed with sign-up logic
                    // Correct
                } else {
                    passwordError = "Password must be at least 8 characters long, include a number, a special character, a lowercase letter, and an uppercase letter."
                }
                scope.launch {
                    viewModel.registerUser(email, password)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 30.dp, end = 30.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Black,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(15.dp)
        ) {
            Text(
                text = "Sign Up",
                color = Color.White,
                modifier = Modifier
                    .padding(7.dp)
            )
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            if (state.value?.isLoading == true) {
                CircularProgressIndicator()
            }
        }
        Text(
            modifier = Modifier
                .padding(15.dp)
                .clickable {
                    navController.navigate(Screens.SignInScreen.route)
                },
            text = "Already Have an account? sign In",
            fontWeight = FontWeight.Bold, color = Color.Black, fontFamily = RegularFont
        )
        Text(
            modifier = Modifier
                .padding(
                    top = 40.dp,
                ),
            text = "Or connect with",
            fontWeight = FontWeight.Medium, color = Color.Gray
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp), horizontalArrangement = Arrangement.Center
        ) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    modifier = Modifier.size(50.dp),
                    painter = painterResource(id = R.drawable.ic_google),
                    contentDescription = "Google Icon", tint = Color.Unspecified
                )
            }


        }
    }

    LaunchedEffect(key1 = state.value?.isSuccess) {
        scope.launch {
            if (state.value?.isSuccess?.isNotEmpty() == true) {
                val success = state.value?.isSuccess
                Toast.makeText(context, "$success", Toast.LENGTH_LONG).show()
            }
        }
    }
    LaunchedEffect(key1 = state.value?.isError) {
        scope.launch {
            if (state.value?.isError?.isNotBlank() == true) {
                val error = state.value?.isError
                Toast.makeText(context, "$error", Toast.LENGTH_LONG).show()
            }
        }
    }
}