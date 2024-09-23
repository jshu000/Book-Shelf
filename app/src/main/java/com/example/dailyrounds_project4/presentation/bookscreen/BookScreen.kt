package com.example.dailyrounds_project4.presentation.bookscreen


import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.dailyrounds_project4.presentation.signup_screen.SignUpViewModel
import com.example.dailyrounds_project4.ui.theme.RegularFont
import com.example.dailyrounds_project4.ui.theme.lightBlue
import kotlinx.coroutines.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.dailyrounds_project4.models.BooksItem
import com.example.dailyrounds_project4.presentation.login_screen.SignInViewModel


@Composable
fun BookScreen(
    navController: NavController,
    viewModel: BookViewModel = hiltViewModel()
) {
    // Call loadBookList to fetch the books when the screen is loaded
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.loadBookList(context)
    }

    // Observe the booksList from the ViewModel
    val books by viewModel.booksList

    var selectedYear by remember { mutableStateOf(2022) }

    val years = (2022 downTo 2010).toList()

    Column(modifier = Modifier.fillMaxSize()) {
        // Top Bar
        TopAppBar(
            title = { Text("Book Shelf", fontSize = 24.sp, fontWeight = FontWeight.Bold) },
            backgroundColor = Color(0xFF3F51B5),
            contentColor = Color.White
        )

        // Year Selector Tabs
        ScrollableTabRow(
            selectedTabIndex = years.indexOf(selectedYear),
            backgroundColor = Color(0xFF3F51B5),
            contentColor = Color.White,
            edgePadding = 8.dp
        ) {
            years.forEach { year ->
                Tab(
                    selected = selectedYear == year,
                    onClick = { selectedYear = year },
                    text = {
                        Text(
                            text = year.toString(),
                            color = if (selectedYear == year) Color.Black else Color.White
                        )
                    }
                )
            }
        }

        // Book List
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(books) {item ->
                Log.d("jashwant", "BookScreen.kt: books-"+item)
            }
        }
    }
}
