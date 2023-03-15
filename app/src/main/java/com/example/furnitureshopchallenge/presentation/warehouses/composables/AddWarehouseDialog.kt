package com.example.furnitureshopchallenge.presentation.warehouses.composables



import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.furnitureshopchallenge.domain.model.Warehouse
import com.example.furnitureshopchallenge.presentation.sharedComposables.ShowToast
import com.example.furnitureshopchallenge.presentation.warehouses.WarehouseViewModel
import kotlinx.coroutines.job
import kotlinx.coroutines.launch

@Composable
fun AddWarehouseDialog(
    openDialog: Boolean,
    closeDialog: () -> Unit,
    viewModel: WarehouseViewModel = hiltViewModel()
) {
    if (openDialog) {
        var code by remember { mutableStateOf("") }
        var name by remember { mutableStateOf("") }
        var address by remember { mutableStateOf("") }
        var state by remember { mutableStateOf("") }
        var county by remember { mutableStateOf("") }
        var zip by remember { mutableStateOf("") }
        val priceList by remember { mutableStateOf("") }


        val focusRequester = FocusRequester()
        val coroutineScope = rememberCoroutineScope()

        fun enableCreateButton():Boolean{
            return (!code.isNullOrEmpty() && !name.isNullOrEmpty() && !address.isNullOrEmpty() && !state.isNullOrEmpty() && (viewModel.uriFile.value != null) )
        }


        AlertDialog(
            onDismissRequest = closeDialog,
            title = {
                Text(
                    text = "Create Warehouse"
                )
            },
            text = {
                Column {
                    TextField(
                        value = code,
                        onValueChange = { code = it },
                        placeholder = {
                            Text(
                                text = "Code"
                            )
                        },
                        modifier = Modifier.focusRequester(focusRequester)
                            .verticalScroll(rememberScrollState())
                    )
                    LaunchedEffect(Unit) {
                        coroutineContext.job.invokeOnCompletion {
                            focusRequester.requestFocus()
                        }
                    }
                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )
                    TextField(
                        value = name,
                        onValueChange = { name = it },
                        placeholder = {
                            Text(
                                text = "Name"
                            )
                        },
                        modifier = Modifier.focusRequester(focusRequester)
                    )
                    LaunchedEffect(Unit) {
                        coroutineContext.job.invokeOnCompletion {
                            focusRequester.requestFocus()
                        }
                    }
                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )
                    TextField(
                        value = address,
                        onValueChange = { address = it },
                        placeholder = {
                            Text(
                                text = "Address"
                            )
                        }
                    )
                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )
                    TextField(
                        value = state,
                        onValueChange = { state = it },
                        placeholder = {
                            Text(
                                text = "State"
                            )
                        }
                    )
                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )
                    TextField(
                        value = county,
                        onValueChange = { county = it },
                        placeholder = {
                            Text(
                                text = "County"
                            )
                        }
                    )
                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )
                    TextField(
                        value = zip,
                        onValueChange = { zip = it },
                        placeholder = {
                            Text(
                                text = "Zip"
                            )
                        }
                    )

                    launchFilePicker(viewModel)
                    if(viewModel.uriFile.value != null){
                        viewModel.uriFileName.value?.let { Text(it) }
                    }
                }
            },
            confirmButton = {
                TextButton(
                    enabled = enableCreateButton(),
                    onClick = {
                        coroutineScope.launch {
                            viewModel.codeExists = viewModel.checkIfCodeAlreadyExists(code)
                            if(!viewModel.codeExists){
                                closeDialog()
                                val warehouse = Warehouse(code,  name, address, state, county, zip, priceList)
                                viewModel.uploadFileAndGetUrl(viewModel.uriFile.value!!, code, warehouse)
                            }
                        }

                    }
                ) {
                    Text(
                        text = "Create"
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = closeDialog
                ) {
                    Text(
                        text = "Cancel"
                    )
                }
            }
        )
    }
    if(viewModel.codeExists) ShowToast(message = "There is already a warehouse with that code")
}