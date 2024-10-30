package com.fast.deliverylist.feature_delivery.presentation.delivery_detail.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fast.deliverylist.feature_delivery.domain.model.Delivery
import com.fast.deliverylist.feature_delivery.domain.use_case.DeliveryUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeliveryDetailsViewModel @Inject constructor(
    private val deliveryUseCases: DeliveryUseCases
) : ViewModel() {

    fun updateDelivery(delivery: Delivery) {
        viewModelScope.launch {
            deliveryUseCases.updateDelivery.execute(delivery)
        }
    }

}