package com.example.furnitureshopchallenge.presentation.warehouses.composables

import android.net.Uri
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.furnitureshopchallenge.domain.model.Response
import com.example.furnitureshopchallenge.presentation.warehouses.WarehouseViewModel


@Composable
fun AddWarehouse(
    viewModel: WarehouseViewModel = hiltViewModel()
) {
    when(val addWarehouseResponse = viewModel.addWarehouseResponse) {
        is Response.Loading -> CircularProgressIndicator()
        is Response.Success -> Unit
        is Response.Failure -> print(addWarehouseResponse.e)
    }
}
