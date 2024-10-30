package com.fast.deliverylist.feature_delivery.data.repository

import androidx.paging.PagingSource
import com.fast.deliverylist.feature_delivery.data.data_source.DeliveryDao
import com.fast.deliverylist.feature_delivery.data.network.NetworkApiService
import com.fast.deliverylist.feature_delivery.domain.model.Delivery
import com.fast.deliverylist.feature_delivery.domain.repository.DeliveryRepository

class DeliveryRepositoryImpl(
    private var networkApiService: NetworkApiService, private val dao: DeliveryDao
) : DeliveryRepository {

    override suspend fun getMyDeliveriesFromNetwork(page: Int, limit: Int): List<Delivery> {
        try {
            val list = networkApiService.getMyDeliveries(page, limit)
            insertMyDeliveries(list)
            return list
        } catch (e: Exception) {
            throw Exception(e)
        }
    }

    // region Offline Mode
    override suspend fun insertMyDeliveries(deliveries: List<Delivery>) {
        dao.upsertMyDeliveries(deliveries)
    }

    override fun pagingSource(): PagingSource<Int, Delivery> {
        return dao.pagingSource()
    }

    override suspend fun updateDelivery(delivery: Delivery) {
        dao.updateDelivery(delivery)
    }


}