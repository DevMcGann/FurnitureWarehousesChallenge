package com.example.furnitureshopchallenge.presentation.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.furnitureshopchallenge.data.datasource.local.Constants
import com.example.furnitureshopchallenge.data.datasource.local.SharedPref
import com.example.furnitureshopchallenge.domain.model.Response
import com.example.furnitureshopchallenge.domain.model.RoleResponse
import com.example.furnitureshopchallenge.domain.repository.LoginRepository
import com.example.furnitureshopchallenge.domain.useCase.Usecases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repo : LoginRepository,
    private val sharedPref: SharedPref,
    private val useCases: Usecases
)  : ViewModel() {


    val error = mutableStateOf(false)
    val isLoading = mutableStateOf(false)

    private val currentUser = mutableStateOf("")

    var getRole by mutableStateOf<RoleResponse>(Response.Loading)
        private set


     suspend fun Login(email:String, password:String) =
         flow {
             isLoading.value = true
             try {
                 val user = repo.login(email,password)
                 error.value =false
                 isLoading.value=false
                 emit(user)
             }catch (e:Exception){
                 error.value = true
                 isLoading.value = false
             }
    }

    fun saveUserToPref(email:String){
        sharedPref.savePref(Constants.LOGGED_USER, email)
    }



}