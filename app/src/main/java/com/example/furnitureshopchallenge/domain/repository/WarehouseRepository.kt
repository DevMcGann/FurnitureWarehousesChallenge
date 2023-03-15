package com.example.furnitureshopchallenge.domain.repository

import android.net.Uri
import androidx.lifecycle.LiveData
import com.example.furnitureshopchallenge.domain.model.*
import kotlinx.coroutines.flow.Flow

interface WarehouseRepository {
    fun getWarehousesFromFirestore(): Flow<WarehouseResponse>
    suspend fun addWarehouseToFirestore(warehouse: Warehouse): AddWarehouseResponse
    suspend fun deleteWarehoouseFromFirestore(code: String): DeleteWarehouseResponse
    fun getUserRoleFromFirebase(email:String) : Flow<RoleResponse>
    suspend fun checkIfCodeExists(code:String):Boolean
    suspend fun uploadFileAndGetUrl(uri:Uri, code:String) : Flow<FileUploadResponde>
    suspend fun deleteFile(code: String)
    fun logout()
}