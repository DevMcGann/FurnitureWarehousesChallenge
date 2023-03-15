package com.example.furnitureshopchallenge.presentation.warehouses.composables



import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.furnitureshopchallenge.presentation.warehouses.WarehouseViewModel


@Composable
fun launchFilePicker(viewModel: WarehouseViewModel) {
    //val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            viewModel.uriFileName.value = uri.lastPathSegment
            viewModel.saveUriFile(uri)
        }
    }
    Button(onClick = { launcher.launch("application/pdf") }) {
        Text("Select File")
    }
}

