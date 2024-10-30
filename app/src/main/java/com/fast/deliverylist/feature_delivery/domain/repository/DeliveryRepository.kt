package com.fast.deliverylist.feature_delivery.domain.repository

import androidx.paging.PagingSource
import com.fast.deliverylist.feature_delivery.domain.model.Delivery

interface DeliveryRepository {

    suspend fun getMyDeliveriesFromNetwork(page: Int, limit: Int): List<Delivery>

    suspend fun insertMyDeliveries(deliveries: List<Delivery>)

    fun pagingSource(): PagingSource<Int, Delivery>

    suspend fun updateDelivery(delivery: Delivery)

}