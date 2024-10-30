package com.fast.deliverylist.feature_delivery.data.network

import com.fast.deliverylist.feature_delivery.domain.model.Delivery
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://66e3a4c1d2405277ed11662b.mockapi.io/"

interface NetworkApiService {

    @GET("deliveries")
    suspend fun getMyDeliveries(
        @Query("page") page: Int, @Query("limit") limit: Int
    ): List<Delivery>

}