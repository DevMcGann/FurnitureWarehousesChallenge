package com.example.furnitureshopchallenge.presentation.warehouses.composables

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable

@Composable
fun TopBar(
    onClick : () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = "Furniture Store"
            )
        },
        actions = {
           Button(onClick = { onClick() }) {
               Text("Logout")
           }
        }
    )

}