package com.example.furnitureshopchallenge.domain.repository

import com.google.firebase.auth.FirebaseUser


interface LoginRepository {
   suspend fun login(email:String, pass:String) : FirebaseUser?

    fun logout()
}