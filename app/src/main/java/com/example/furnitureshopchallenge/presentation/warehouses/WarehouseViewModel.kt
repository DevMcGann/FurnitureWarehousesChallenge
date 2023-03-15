package com.example.furnitureshopchallenge.presentation.warehouses


import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import com.example.furnitureshopchallenge.data.datasource.local.Constants
import com.example.furnitureshopchallenge.data.datasource.local.SharedPref
import com.example.furnitureshopchallenge.data.repository.warehouse.WarehouseRepositoryImpl
import com.example.furnitureshopchallenge.domain.model.*
import com.example.furnitureshopchallenge.domain.useCase.Usecases
import com.google.firebase.storage.ktx.storageMetadata
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class WarehouseViewModel @Inject constructor(
    private val repo : WarehouseRepositoryImpl,
    private val sharedPref: SharedPref,
    private val useCases: Usecases

)  : ViewModel() {

    val isLoading = mutableStateOf(false)
    val isError = mutableStateOf(false)
    val errorMessage = mutableStateOf("")

    val uriFile = mutableStateOf<Uri?>(null)
    val uriFileName = mutableStateOf<String?>(null)
    val urlString = mutableStateOf("")

    var openDialog by mutableStateOf(false)
        private set

    var warehouseResponse by mutableStateOf<WarehouseResponse>(Response.Loading)
        private set
    var addWarehouseResponse by mutableStateOf<AddWarehouseResponse>(Response.Success(false))
        private set
    var deleteWarehouseResponse by mutableStateOf<DeleteWarehouseResponse>(Response.Success(false))
        private set


    var getRole by mutableStateOf<RoleResponse>(Response.Loading)
        private set

    var fileUrl by mutableStateOf<FileUploadResponde>(Response.Loading)
        private set

    var codeExists by mutableStateOf<Boolean>(false)

    init {
        getWarehouses()
        getUserRole()
    }





    fun openDialog() {
        openDialog = true
    }

    fun closeDialog() {
        openDialog = false
    }

    private fun getWarehouses() = viewModelScope.launch {
        useCases.getWarehouses().collect { response ->
            warehouseResponse = response
        }
    }

    private fun getUserRole() = viewModelScope.launch {
        val email = sharedPref.getFromPrefs(Constants.LOGGED_USER)
        if(email != null){
            useCases.getRole(email).collect { response ->
                getRole = response
            }
        }
    }


     suspend fun uploadFileAndGetUrl(uri: Uri, code: String, warehouse: Warehouse)=
        viewModelScope.launch {
          repo.uploadFileAndGetUrl(uri, code).collect { response ->
              fileUrl = response
              when (val res = fileUrl){
                  is Response.Loading -> {
                      isError.value = false
                      isLoading.value = true
                  }
                  is Response.Success -> {
                      isError.value = false
                      isLoading.value = false
                      urlString.value = res.data.orEmpty()
                      warehouse.priceList = urlString.value
                      addWarehouseResponse = useCases.addWarehouse(warehouse)
                  }
                  is Response.Failure -> {
                      isError.value = true
                      isLoading.value = false
                  }
          }

    }
        }

    private suspend fun deleteFile(code: String){
        try {
            repo.deleteFile(code)
            isError.value = false
        }catch (e:Exception){
            isError.value = true
            errorMessage.value = "An Error ocurred deleting the ListPrice of this Warehouse"
        }

    }


    suspend fun checkIfCodeAlreadyExists(code:String):Boolean{
        return repo.checkIfCodeExists(code)
    }

    fun deleteWarehouse(code: String) = viewModelScope.launch {
        deleteFile(code)
        deleteWarehouseResponse = Response.Loading
        deleteWarehouseResponse = useCases.deleteWarehouse(code)
    }


    fun logout(){
        repo.logout()
        sharedPref.clearSharedPreference()
    }

    fun saveUriFile (uri:Uri){
        uriFile.value = uri
    }





}