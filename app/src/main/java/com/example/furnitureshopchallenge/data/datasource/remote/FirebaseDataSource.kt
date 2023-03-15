package com.example.furnitureshopchallenge.data.datasource.remote


import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.furnitureshopchallenge.domain.model.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import java.io.File
import java.util.*
import javax.inject.Inject

class FirebaseDataSource @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage,
    private val auth: FirebaseAuth
) {


    fun getWarehousesFromFirestore() = callbackFlow {
        val snapshotListener =
            firestore.collection("warehouse").addSnapshotListener { snapshot, e ->
                val whResponse = if (snapshot != null) {
                    val warehouses = snapshot.toObjects(Warehouse::class.java)
                    Response.Success(warehouses)
                } else {
                    Response.Failure(e)
                }
                trySend(whResponse)
            }
        awaitClose {
            snapshotListener.remove()
        }
    }

    fun getUserRoleFromFirebase(email: String) = callbackFlow {
        val snapshotListener =
            firestore.collection("user").document(email).addSnapshotListener { value, error ->
                val response = if (value != null) {
                    val role = value.data?.get("role")
                    Response.Success(role.toString())
                } else {
                    Response.Failure(error)
                }
                trySend(response)
            }

        awaitClose { snapshotListener.remove() }

    }

    suspend fun uploadImagesAndGetURL(uri: Uri, code:String) = callbackFlow {
        val fileName = "$code.pdf"
        val ref = storage.getReference("/pdf/$fileName")
        ref.putFile(uri).await()
        val response = ref.downloadUrl.await()
        val result = if(response != null){
            val link = response.toString()
            Response.Success(link)
        }else{
            Response.Failure(java.lang.Exception())
        }
        trySend(result)
        awaitClose {  }
    }

    suspend fun deleteFile(code:String){
        val fileName = "$code.pdf"
        val ref = storage.getReference("/pdf/$fileName")
        ref.delete().await()
    }


    suspend fun addWarehouseToFirestore(warehouse: Warehouse): AddWarehouseResponse {
            return try {
                val wh = Warehouse(
                    code = warehouse.code,
                    name = warehouse.name,
                    address = warehouse.address,
                    state = warehouse.state,
                    zip = warehouse.zip,
                    county = warehouse.county,
                    priceList = warehouse.priceList
                )
                firestore.collection("warehouse").document(wh.code).set(wh).await()
                Response.Success(true)
            } catch (e: Exception) {
                Response.Failure(e)
            }
    }


    suspend fun deleteWarehouseFromFirestore(code: String): DeleteWarehouseResponse {
        return try {
            firestore.collection("warehouse").document(code).delete().await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

     suspend fun checkIfWarehouseExists(code: String): Boolean {
        var codeExists = false
        val querySnapshot = firestore.collection("warehouse").document(code).get().await()
        if (querySnapshot.exists()) codeExists = true
        return codeExists
    }





    suspend fun Login(email: String, password: String): FirebaseUser? {
        val authResult =
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).await()
        return authResult.user
    }


    fun logout() {
        auth.signOut()
    }


}