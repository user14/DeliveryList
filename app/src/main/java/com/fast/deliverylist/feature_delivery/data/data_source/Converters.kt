package com.fast.deliverylist.feature_delivery.data.data_source

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.fast.deliverylist.feature_delivery.domain.model.Route
import com.fast.deliverylist.feature_delivery.domain.model.Sender

class Converters {
    @TypeConverter
    fun getRouteFromString(value: String): Route {
        return Gson().fromJson(value, Route::class.java)
    }

    @TypeConverter
    fun routeToString(route: Route): String {
        return Gson().toJson(route)
    }

    @TypeConverter
    fun getSenderFromString(value: String): Sender {
        return Gson().fromJson(value, Sender::class.java)
    }

    @TypeConverter
    fun senderToString(sender: Sender): String {
        return Gson().toJson(sender)
    }


}
