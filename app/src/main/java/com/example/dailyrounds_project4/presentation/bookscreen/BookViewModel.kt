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
import kotlinx.coroutines.GlobalScope
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

    fun booklist(context: Context): List<BooksItem> {
        val booksList = mutableListOf<BooksItem>()
        val booksAPI = RetrofitHelper.getInstance().create(BooksAPI::class.java)
        GlobalScope.launch {
            val result = booksAPI.getBooks()
            if(result!=null){
                Log.d("jashwant", "onCreate: result.body.tostring -"+result.body().toString())
                val bookList =result.body()
            }
        }
        return booksList
    }
}