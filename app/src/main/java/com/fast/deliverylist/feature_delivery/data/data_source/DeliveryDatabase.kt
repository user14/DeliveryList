package com.fast.deliverylist.feature_delivery.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.fast.deliverylist.feature_delivery.domain.model.Delivery

@Database(
    entities = [Delivery::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class DeliveryDatabase: RoomDatabase() {

    abstract val deliveryDao: DeliveryDao

    companion object {
        const val DATABASE_NAME = "delivery_db"
    }
}