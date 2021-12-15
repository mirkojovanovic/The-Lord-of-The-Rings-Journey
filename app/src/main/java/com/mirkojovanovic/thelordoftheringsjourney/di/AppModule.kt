package com.mirkojovanovic.thelordoftheringsjourney.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mirkojovanovic.thelordoftheringsjourney.BuildConfig
import com.mirkojovanovic.thelordoftheringsjourney.common.Constants.BASE_URL
import com.mirkojovanovic.thelordoftheringsjourney.data.remote.AuthorizationInterceptor
import com.mirkojovanovic.thelordoftheringsjourney.data.remote.TheOneApi
import com.mirkojovanovic.thelordoftheringsjourney.data.repository.BookRepositoryImpl
import com.mirkojovanovic.thelordoftheringsjourney.data.repository.MovieRepositoryImpl
import com.mirkojovanovic.thelordoftheringsjourney.domain.repository.BookRepository
import com.mirkojovanovic.thelordoftheringsjourney.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder()
        .setLenient()
        .create()

    @Provides
    @Singleton
    fun authorizationInterceptor(): AuthorizationInterceptor =
        AuthorizationInterceptor()

    @Provides
    @Singleton
    fun provideOkHttpClient(
        authorizationInterceptor: AuthorizationInterceptor,
    ): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(authorizationInterceptor)

        if (BuildConfig.DEBUG)
            okHttpClient.addInterceptor(
                HttpLoggingInterceptor()
                    .apply { level = HttpLoggingInterceptor.Level.BODY })

        return okHttpClient.build()
    }

    @Provides
    @Singleton
    fun provideTheOneApi(
        okHttpClient: OkHttpClient,
        gson: Gson,
    ): TheOneApi =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(TheOneApi::class.java)

    @Provides
    @Singleton
    fun provideBookRepository(api: TheOneApi): BookRepository {
        return BookRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(api: TheOneApi): MovieRepository {
        return MovieRepositoryImpl(api)
    }

}
