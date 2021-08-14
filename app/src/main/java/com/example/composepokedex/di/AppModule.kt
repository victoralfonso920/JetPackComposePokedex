package com.example.composepokedex.di

import com.example.composepokedex.BuildConfig
import com.example.composepokedex.data.remote.PokeApi
import com.example.composepokedex.repository.PokemonRepository
import com.example.composepokedex.utils.Constants.BASE_URL
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providePokemonRepository(
        api: PokeApi
    ) = PokemonRepository(api)

    @Singleton
    @Provides
    fun providerPokeApi(okHttpClient: OkHttpClient): PokeApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()
            .create(PokeApi::class.java)
    }

    @Singleton
    @Provides
    fun providerOkhttp(): OkHttpClient {
        return with(OkHttpClient.Builder()) {
            connectTimeout(300, TimeUnit.SECONDS)
            readTimeout(80, TimeUnit.SECONDS)
            writeTimeout(90, TimeUnit.SECONDS)
            retryOnConnectionFailure(true)
            when {
                BuildConfig.DEBUG -> {
                    addInterceptor(OkHttpProfilerInterceptor())
                }
            }
            build()
        }
    }
}