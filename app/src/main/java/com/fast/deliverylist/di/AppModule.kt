package com.fast.deliverylist.di

import android.app.Application
import androidx.room.Room
import com.fast.deliverylist.feature_delivery.data.data_source.DeliveryDatabase
import com.fast.deliverylist.feature_delivery.data.network.BASE_URL
import com.fast.deliverylist.feature_delivery.data.network.DeliveryRemoteMediator
import com.fast.deliverylist.feature_delivery.data.network.NetworkApiService
import com.fast.deliverylist.feature_delivery.data.repository.DeliveryRepositoryImpl
import com.fast.deliverylist.feature_delivery.domain.repository.DeliveryRepository
import com.fast.deliverylist.feature_delivery.domain.use_case.DeliveryUseCases
import com.fast.deliverylist.feature_delivery.domain.use_case.GetDeliveries
import com.fast.deliverylist.feature_delivery.domain.use_case.UpdateDelivery
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

    @Singleton
    @Provides
    fun provideHttpLogging(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient.Builder().addNetworkInterceptor(httpLoggingInterceptor)
        .callTimeout(1, TimeUnit.MINUTES).readTimeout(1, TimeUnit.MINUTES).build()

    @Provides
    @Singleton
    fun provideNetworkApiService(okHttpClient: OkHttpClient): NetworkApiService {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build()
            .create(NetworkApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDeliveryListDatabase(app: Application): DeliveryDatabase {
        return Room.databaseBuilder(
            app, DeliveryDatabase::class.java, DeliveryDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideDeliveryRemoteMediator(
        deliveryDatabase: DeliveryDatabase, networkApiService: NetworkApiService
    ): DeliveryRemoteMediator = DeliveryRemoteMediator(
        deliveryDatabase, networkApiService
    )

    @Provides
    @Singleton
    fun provideDeliveryRepo(
        networkApiService: NetworkApiService, db: DeliveryDatabase
    ): DeliveryRepository {
        return DeliveryRepositoryImpl(networkApiService, db.deliveryDao)
    }

    @Provides
    @Singleton
    fun provideDeliveryUseCases(
        repository: DeliveryRepository,
        deliveryRemoteMediator: DeliveryRemoteMediator,
    ): DeliveryUseCases {
        return DeliveryUseCases(
            getDeliveries = GetDeliveries(
                repository,
                deliveryRemoteMediator
            ),
            updateDelivery = UpdateDelivery(repository)
        )
    }
}