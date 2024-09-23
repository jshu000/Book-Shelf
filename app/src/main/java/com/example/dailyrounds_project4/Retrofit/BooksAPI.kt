package com.example.dailyrounds_project4.Retrofit

import com.example.dailyrounds_project4.models.Books
import com.example.dailyrounds_project4.models.BooksItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
interface BooksAPI {
    @GET("CNGI")
    suspend fun getBooks(): Response<List<BooksItem>>

}