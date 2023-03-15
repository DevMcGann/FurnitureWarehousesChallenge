package com.example.furnitureshopchallenge.data.repository.warehouse


import android.net.Uri
import androidx.lifecycle.LiveData
import com.example.furnitureshopchallenge.data.datasource.remote.FirebaseDataSource
import com.example.furnitureshopchallenge.domain.model.*
import com.example.furnitureshopchallenge.domain.repository.WarehouseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WarehouseRepositoryImpl @Inject constructor(
    private val api:FirebaseDataSource,
        ) : WarehouseRepository {


    override fun getWarehousesFromFirestore(): Flow<WarehouseResponse> {
        return api.getWarehousesFromFirestore()
    }

    override suspend fun addWarehouseToFirestore(warehouse: Warehouse): AddWarehouseResponse {
        return api.addWarehouseToFirestore(warehouse)
    }

    override suspend fun deleteWarehoouseFromFirestore(code: String): DeleteWarehouseResponse {
        return api.deleteWarehouseFromFirestore(code)
    }

    override fun getUserRoleFromFirebase(email: String): Flow<RoleResponse> {
        return api.getUserRoleFromFirebase(email)
    }


    override fun logout() {
        api.logout()
    }

    override suspend fun checkIfCodeExists(code: String): Boolean {
        return api.checkIfWarehouseExists(code)
    }


    override suspend fun uploadFileAndGetUrl(uri: Uri, code: String): Flow<FileUploadResponde> {
        return api.uploadImagesAndGetURL(uri, code)
    }

    override suspend fun deleteFile(code: String) {
        api.deleteFile(code)
    }


}