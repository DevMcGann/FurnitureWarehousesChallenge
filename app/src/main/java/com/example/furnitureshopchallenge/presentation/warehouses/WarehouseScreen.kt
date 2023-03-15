package com.example.furnitureshopchallenge.presentation.warehouses


import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.furnitureshopchallenge.domain.model.Response
import com.example.furnitureshopchallenge.domain.model.RoleResponse
import com.example.furnitureshopchallenge.domain.model.Warehouse
import com.example.furnitureshopchallenge.presentation.warehouses.composables.*
import dagger.hilt.android.qualifiers.ApplicationContext


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun WarehouseScreen(
    navHostController: NavHostController,
    viewModel: WarehouseViewModel = hiltViewModel()
) {


    fun logout (){
        viewModel.logout()
        navHostController.navigate("login")
    }

    Scaffold(
        topBar = { TopBar (onClick = { logout() }) },
        content = {
            WarehouseContent(
                warehousesContent = { wh ->
                    WarehouseList(
                        warehouses = wh
                    )
                }
            )

            AddWarehouseDialog(
                openDialog = viewModel.openDialog,
                closeDialog = {
                    viewModel.closeDialog()
                }
            )

        }, //content

    floatingActionButton = {
        when(val roleResponse = viewModel.getRole) {
            is Response.Loading -> CircularProgressIndicator()
            is Response.Success -> {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 40.dp, end = 5.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    FloatingActionButton(onClick = {
                        viewModel.openDialog() }
                    ) {
                        Text("New")
                    }
                    if(roleResponse.data == "admin"){
                        FloatingActionButton(onClick = { /*TODO*/ }) {
                            Text("Map")
                        }
                    }

                }
            }
            is Response.Failure -> print(roleResponse.e)
        }
    }
    )

    AddWarehouse()

}

