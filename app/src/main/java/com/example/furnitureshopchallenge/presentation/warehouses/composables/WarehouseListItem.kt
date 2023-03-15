package com.example.furnitureshopchallenge.presentation.warehouses.composables


import android.content.Intent
import android.graphics.drawable.Icon
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.furnitureshopchallenge.domain.model.Warehouse
import com.example.furnitureshopchallenge.presentation.warehouses.WarehouseViewModel

@Composable
fun WarehouseListItem(warehouse: Warehouse, viewModel: WarehouseViewModel = hiltViewModel()){

    val context = LocalContext.current
    val intent = remember { Intent(Intent.ACTION_VIEW, Uri.parse(
        warehouse.priceList
    )) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(10.dp),
        backgroundColor = Color.Gray,
        elevation = 10.dp
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(vertical = 20.dp, horizontal = 10.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.background(Color.Green)
                    .fillMaxWidth()
            ) {
                Text(warehouse.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(warehouse.address)
                Text(warehouse.state)
                if (warehouse.zip != ""){
                    Text("CP " + warehouse.zip)
                }
                if(warehouse.county != null){
                    Text(warehouse.county)
                }
            }
            Row() {
                Button(onClick = {
                    warehouse.priceList?.let {
                        context.startActivity(intent) } }) {
                    Text(text = "Price List")
                }
                IconButton(
                    onClick = { viewModel.deleteWarehouse(warehouse.code) },
                    enabled = true, // set to false to disable button
                    content = {
                        Icon(Icons.Filled.Delete, contentDescription = "Delete", tint = Color.Red)
                    }
                )
            }
        }
    }
}


