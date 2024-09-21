package com.example.dailyrounds_project4.presentation.signup_screen

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailyrounds_project4.data.AuthRepository
import com.example.dailyrounds_project4.models.CountryItem
import com.example.dailyrounds_project4.presentation.login_screen.SignInState
import com.example.dailyrounds_project4.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.json.JSONArray
import java.io.InputStream
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    val _signUpState  = Channel<SignInState>()
    val signUpState  = _signUpState.receiveAsFlow()


    fun registerUser(email:String, password:String) = viewModelScope.launch {
        repository.registerUser(email, password).collect{result ->
            when(result){
                is Resource.Success ->{
                    _signUpState.send(SignInState(isSuccess = "Sign Up Success "))
                }
                is Resource.Loading ->{
                    _signUpState.send(SignInState(isLoading = true))
                }
                is Resource.Error ->{

                    _signUpState.send(SignInState(isError = result.message))
                }
            }

        }
    }
    fun loadCountries(context: Context): List<CountryItem> {
        val countryList = mutableListOf<CountryItem>()
        try {
            val inputStream: InputStream = context.assets.open("countrylist.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()

            val json = String(buffer, Charsets.UTF_8)
            val jsonArray = JSONArray(json)

            for (i in 0 until jsonArray.length()) {
                val countryJson = jsonArray.getJSONObject(i)
                countryList.add(CountryItem(countryJson.getString("country"), countryJson.getString("region")))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        Log.d("jashwant", "Loaded countries: $countryList")
        return countryList
    }

    fun isPasswordValid(password: String): Boolean {
        val passwordPattern = Regex("^(?=.*[0-9])(?=.*[!@#$%^&*()])(?=.*[a-z])(?=.*[A-Z]).{8,}$")
        return passwordPattern.matches(password)
    }

}