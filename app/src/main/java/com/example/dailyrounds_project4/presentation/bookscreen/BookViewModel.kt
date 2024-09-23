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

    // MutableState to hold the books list
    private val _booksList = mutableStateOf<List<BooksItem>>(emptyList())
    val booksList: State<List<BooksItem>> = _booksList

    // Function to load the book list
    fun loadBookList(context: Context) {
        val booksAPI = RetrofitHelper.getInstance().create(BooksAPI::class.java)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = async { booksAPI.getBooks() }.await()

                if (result.isSuccessful && result.body() != null) {
                    val bookList = result.body()!!
                    Log.d("BookViewModel", "Books retrieved: $bookList")

                    // Update the books list in the state
                    _booksList.value = bookList
                } else {
                    Log.e("BookViewModel", "Error: ${result.errorBody()?.string() ?: "Unknown error"}")
                }
            } catch (e: Exception) {
                Log.e("BookViewModel", "Exception occurred: ${e.message}")
            }
        }
    }
}
