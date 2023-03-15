package com.example.furnitureshopchallenge.presentation.sharedComposables

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
fun ShowToast(message: String) {
    val context = LocalContext.current
    val toast = remember { Toast.makeText(context, message, Toast.LENGTH_SHORT) }
    toast.show()
}