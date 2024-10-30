package com.fast.deliverylist.feature_delivery.data.data_source

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.fast.deliverylist.feature_delivery.domain.model.Delivery

@Dao
interface DeliveryDao {

    @Upsert
    suspend fun upsertMyDeliveries(list: List<Delivery>)

    @Query("SELECT * FROM Delivery ")
    fun pagingSource(): PagingSource<Int, Delivery>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateDelivery(delivery: Delivery)

    @Query("SELECT * FROM Delivery WHERE uId = :id")
    suspend fun getDeliveryById(id: Int): Delivery?


}