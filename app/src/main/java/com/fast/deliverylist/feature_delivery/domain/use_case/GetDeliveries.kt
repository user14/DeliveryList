package com.fast.deliverylist.feature_delivery.domain.use_case

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.fast.deliverylist.feature_delivery.data.network.DeliveryRemoteMediator
import com.fast.deliverylist.feature_delivery.domain.model.Delivery
import com.fast.deliverylist.feature_delivery.domain.repository.DeliveryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class GetDeliveries @Inject constructor(
    private val repository: DeliveryRepository,
    private val deliveryRemoteMediator: DeliveryRemoteMediator
) {

    fun execute(): Flow<PagingData<Delivery>> {
        return Pager(
            config = PagingConfig(pageSize = 25),
            remoteMediator = deliveryRemoteMediator,
        ) {
            repository.pagingSource()
        }.flow
    }

}