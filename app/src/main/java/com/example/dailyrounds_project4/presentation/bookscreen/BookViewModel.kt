package com.example.dailyrounds_project4.presentation.bookscreen

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailyrounds_project4.Retrofit.BooksAPI
import com.example.dailyrounds_project4.Retrofit.RetrofitHelper
import com.example.dailyrounds_project4.data.AuthRepository
import com.example.dailyrounds_project4.models.BooksItem
import com.example.dailyrounds_project4.models.CountryItem
import com.example.dailyrounds_project4.presentation.login_screen.GoogleSignInState
import com.example.dailyrounds_project4.presentation.login_screen.SignInState
import com.example.dailyrounds_project4.util.Resource
import com.google.firebase.auth.AuthCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.json.JSONArray
import java.io.InputStream
import javax.inject.Inject
@HiltViewModel
class BookViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    // Full books list
    private val _booksList = mutableStateOf<List<BooksItem>>(emptyList())
    val booksList: State<List<BooksItem>> = _booksList

    // Selected year for filtering
    private val _selectedYear = mutableStateOf(2018) // Default year
    val selectedYear: State<Int> = _selectedYear

    // Filtered books list based on the selected year
    private val _filteredBooksList = mutableStateOf<List<BooksItem>>(emptyList())
    val filteredBooksList: State<List<BooksItem>> = _filteredBooksList

    // Function to load the book list
    fun loadBookList(context: Context) {
        val booksAPI = RetrofitHelper.getInstance().create(BooksAPI::class.java)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = async { booksAPI.getBooks() }.await()

                if (result.isSuccessful && result.body() != null) {
                    val bookList = result.body()!!
                    Log.d("BookViewModel", "Books retrieved: ${bookList.size} books")

                    // Update the books list in the state
                    _booksList.value = bookList

                    // Trigger filtering after the data is loaded
                    filterBooksByYear()
                } else {
                    Log.e("BookViewModel", "Error: ${result.errorBody()?.string() ?: "Unknown error"}")
                }
            } catch (e: Exception) {
                Log.e("BookViewModel", "Exception occurred: ${e.message}")
            }
        }
    }

    // Function to set selected year and filter the books list
    fun onYearSelected(year: Int) {
        _selectedYear.value = year
        filterBooksByYear() // Filter the books immediately after year selection
    }

    // Function to filter books based on the selected year
    private fun filterBooksByYear() {
        val year = _selectedYear.value
        val allBooks = _booksList.value

        // Log the year and books before filtering
        Log.d("BookViewModel", "Filtering books for year: $year")
        Log.d("BookViewModel", "Total books available: ${allBooks.size}")

        _filteredBooksList.value = allBooks

        // Log the filtered list size
        Log.d("BookViewModel", "Books after filtering: ${_filteredBooksList.value.size}")
    }
}
