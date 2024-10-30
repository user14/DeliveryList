package com.fast.deliverylist.feature_delivery.domain.use_case

import com.fast.deliverylist.feature_delivery.domain.model.Delivery
import com.fast.deliverylist.feature_delivery.domain.repository.DeliveryRepository
import javax.inject.Inject

class UpdateDelivery @Inject constructor(
    private val repository: DeliveryRepository
) {

    suspend fun execute(delivery: Delivery) {
        repository.updateDelivery(delivery)
    }

}