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
import androidx.compose.foundation.lazy.LazyRow
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
    val booksList by viewModel.booksList
    val context = LocalContext.current

    // This will be used to indicate loading state
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        viewModel.loadBookList(context)
        // After loading, we can check the state and set loading to false
        isLoading = false
    }

    val filteredBooksList by viewModel.filteredBooksList
    val selectedYear by viewModel.selectedYear

    // Create a set of years based on the filtered books list
    val publishedYears = booksList.map { it.getPublishedYear() }.toSet()
    val years = (2014 downTo 2000).filter { it in publishedYears }

    Log.d("BookScreen", "Filtered Book List: $filteredBooksList")
    Log.d("BookScreen", "Books List: $booksList")

    Column {
        // Title
        Text(
            text = "Book Shelf",
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 8.dp).align(Alignment.CenterHorizontally)
        )
        // Show loading UI if still loading or if the filtered list is empty
        if (isLoading || filteredBooksList.isEmpty()) {
            // Display a loading spinner
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            ) {
                CircularProgressIndicator()
            }
        } else {
            // Year Filter Bar
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                items(years) { year ->
                    Text(
                        text = year.toString(),
                        modifier = Modifier
                            .padding(12.dp)
                            .clickable {
                                viewModel.onYearSelected(year)
                            }
                            .then(if (year == selectedYear) Modifier.background(Color.LightGray) else Modifier),
                        color = if (year == selectedYear) Color.Blue else Color.Black,
                        fontWeight = if (year == selectedYear) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }

            // Display the filtered list of books
            LazyColumn {
                items(filteredBooksList) { book ->
                    BookItem(book)
                }
            }
        }
    }
}

@Composable
fun BookItem(book: BooksItem) {
    Row(modifier = Modifier.padding(8.dp)) {
        // Load image using Coil or similar library
        Image(
            painter = rememberImagePainter(book.image),
            contentDescription = book.title,
            modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = book.title, fontWeight = FontWeight.Bold)
            Text(text = "Score: ${book.score}")
            Text(text = "Popularity: ${book.popularity}")
            Text(text = "Published on: ${book.getPublishedYear()}") // Format this as needed
        }
    }
}