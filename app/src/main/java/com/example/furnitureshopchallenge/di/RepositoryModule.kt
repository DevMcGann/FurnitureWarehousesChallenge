package com.example.furnitureshopchallenge.di

import com.example.furnitureshopchallenge.data.datasource.local.SharedPref
import com.example.furnitureshopchallenge.data.datasource.remote.FirebaseDataSource
import com.example.furnitureshopchallenge.domain.repository.LoginRepository
import com.example.furnitureshopchallenge.data.repository.login.LoginRepositoryImpl
import com.example.furnitureshopchallenge.data.repository.warehouse.WarehouseRepositoryImpl
import com.example.furnitureshopchallenge.domain.repository.WarehouseRepository
import com.example.furnitureshopchallenge.domain.useCase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton



@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(
        api: FirebaseDataSource,
        sharePreferencesManager: SharedPref
    ): LoginRepository {
        return LoginRepositoryImpl(
            api = api,
            sharedPref = sharePreferencesManager
        )
    }

    @Provides
    @Singleton
    fun provideWarehouseRepository(
        api: FirebaseDataSource,

    ): WarehouseRepository {
        return WarehouseRepositoryImpl(
            api = api
        )
    }

    @Provides
    fun provideUseCases(
        repo: WarehouseRepository
    ) = Usecases(
        getWarehouses = GetWarehouses(repo),
        addWarehouse =  AddWarehouse(repo),
        deleteWarehouse= DeleteWarehouse(repo),
        getRole = GetRole(repo)
    )


}