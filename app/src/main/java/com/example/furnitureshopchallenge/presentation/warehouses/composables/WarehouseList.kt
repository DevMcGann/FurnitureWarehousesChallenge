package com.example.furnitureshopchallenge.presentation.warehouses.composables

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.furnitureshopchallenge.domain.model.Response
import com.example.furnitureshopchallenge.domain.model.Warehouse
import com.example.furnitureshopchallenge.presentation.warehouses.WarehouseViewModel

@Composable
fun WarehouseContent(
    viewModel: WarehouseViewModel = hiltViewModel(),
    warehousesContent: @Composable (warehouses: List<Warehouse>) -> Unit
    ) {
        when(val warehousesResponse = viewModel.warehouseResponse) {
            is Response.Loading -> CircularProgressIndicator()
            is Response.Success -> warehousesContent(warehousesResponse.data)
            is Response.Failure -> print(warehousesResponse.e)
        }
}

@Composable
fun WarehouseList(
    warehouses: List<Warehouse>
) {
    LazyColumn {
        items(warehouses) { wh ->
            WarehouseListItem(warehouse =  wh)
        }
    }
}
