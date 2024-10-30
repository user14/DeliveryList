package com.fast.deliverylist.feature_delivery.presentation.my_deliveries.viewmodels

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.fast.deliverylist.feature_delivery.domain.model.Delivery
import com.fast.deliverylist.feature_delivery.domain.use_case.DeliveryUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyDeliveriesViewModel @Inject constructor(
    private val deliveryUseCases: DeliveryUseCases
) : ViewModel() {

    private var _delivery: Delivery? = null

    var llState: LazyListState by mutableStateOf(LazyListState(0, 0))

    fun updateDelivery() {
        viewModelScope.launch {
            _delivery!!.isFavourite = !_delivery!!.isFavourite
            deliveryUseCases.updateDelivery.execute(_delivery!!)
        }
    }

    fun setDelivery(delivery: Delivery) {
        if (_delivery == null) {
            _delivery = delivery
        }
    }

    fun getDelivery(): Delivery? {
        return _delivery
    }

    val deliveryPagingFlow = deliveryUseCases.getDeliveries.execute().cachedIn(viewModelScope)

}