package com.fast.deliverylist.feature_delivery.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.NumberFormat
import java.util.Locale

@Entity
data class Delivery(
    @PrimaryKey(autoGenerate = true) val uId: Int,
    val deliveryFee: String,
    val goodsPicture: String,
    val id: String,
    val pickupTime: String,
    val remarks: String,
    val route: Route,
    val sender: Sender,
    val surcharge: String
) {
    var isFavourite = false

    fun getTotalDeliveryPrice(): String {
        return showAsPrice(deliveryFee.convertToFloat() + surcharge.convertToFloat())
    }

    private fun showAsPrice(amount: Float): String {
        val format = NumberFormat.getCurrencyInstance(Locale.US)
        return format.format(amount)
    }

    fun String.convertToFloat(): Float {
        return this.replace("$", "").toFloat()
    }

}

data class Route(
    val end: String, val start: String
)

data class Sender(
    val email: String, val name: String, val phone: String
)