package com.mirkojovanovic.thelordoftheringsjourney.di

import com.mirkojovanovic.thelordoftheringsjourney.data.prefs_store.PrefsStoreImpl
import com.mirkojovanovic.thelordoftheringsjourney.domain.prefs_store.PrefsStore
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class StoreModule {

    @Binds
    abstract fun bindPrefsStore(prefsStoreImpl: PrefsStoreImpl): PrefsStore
}