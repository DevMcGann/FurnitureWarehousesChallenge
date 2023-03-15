package com.example.furnitureshopchallenge.di

import com.example.furnitureshopchallenge.data.datasource.remote.FirebaseDataSource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideFirestoreInstance(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }


    @Provides
    @Singleton
    fun provideStorageInstance(): FirebaseStorage {
        return FirebaseStorage.getInstance()
    }


    @Provides
    @Singleton
    fun provideAuthInstance(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseAPI() =  FirebaseDataSource(provideFirestoreInstance(), provideStorageInstance(), provideAuthInstance())
}