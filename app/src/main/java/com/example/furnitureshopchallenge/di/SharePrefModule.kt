package com.example.furnitureshopchallenge.di

import android.app.Application
import com.example.furnitureshopchallenge.data.datasource.local.SharedPref
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SharePrefModule {

    @Provides
    @Singleton
    fun provideSharePreference(application: Application): SharedPref {
        return SharedPref(application)
    }

}