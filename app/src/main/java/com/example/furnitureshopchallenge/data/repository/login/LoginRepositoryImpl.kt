package com.example.furnitureshopchallenge.data.repository.login

import android.util.Log
import com.example.furnitureshopchallenge.data.datasource.local.Constants
import com.example.furnitureshopchallenge.data.datasource.local.SharedPref
import com.example.furnitureshopchallenge.data.datasource.remote.FirebaseDataSource
import com.example.furnitureshopchallenge.domain.model.Response
import com.example.furnitureshopchallenge.domain.repository.LoginRepository
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

open class LoginRepositoryImpl @Inject constructor(
    private val api: FirebaseDataSource,
    private val sharedPref: SharedPref
) : LoginRepository {

    override suspend fun login(email: String, pass: String): FirebaseUser? {
        val user = api.Login(email, pass)
        if (user != null){
            sharedPref.savePref(Constants.LOGGED_USER, user.email.toString())
        }
        return user
    }

    override fun logout() {
        try {
            api.logout()
            sharedPref.clearSharedPreference()
        } catch (e: java.lang.Exception) {
            Log.d("ERROR", e.message.toString())
        }
    }

}