package com.fast.deliverylist.feature_delivery.data.network

import android.net.http.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.fast.deliverylist.feature_delivery.data.data_source.DeliveryDatabase
import com.fast.deliverylist.feature_delivery.domain.model.Delivery
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class DeliveryRemoteMediator(
    private val database: DeliveryDatabase, private val networkApiService: NetworkApiService
) : RemoteMediator<Int, Delivery>() {

    private var currentPage = 1
    private var reachedEndOfList = false // Flag to track end of data

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, Delivery>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> {
                    currentPage = 1
                    reachedEndOfList = false // Reset end flag on refresh
                    currentPage
                }

                LoadType.PREPEND -> {
                 return MediatorResult.Success(
                        endOfPaginationReached = true
                    )
                }

                LoadType.APPEND -> {
                    if (reachedEndOfList) {
                        currentPage = 1 // Restart pagination if at end
                    } else {
                        currentPage++
                    }
                    currentPage
                }
            }

            val deliveries = networkApiService.getMyDeliveries(
                page = loadKey, limit = state.config.pageSize
            )

            if (deliveries.isEmpty()) {
                currentPage = 1

                val deliveries = networkApiService.getMyDeliveries(
                    page = currentPage, limit = state.config.pageSize
                )
                database.withTransaction {
                    deliveries.forEach {
                        val oldDeliveries = database.deliveryDao.getDeliveryById(it.uId)
                        if (oldDeliveries != null) {
                            it.isFavourite = oldDeliveries.isFavourite
                        }
                    }
                    database.deliveryDao.upsertMyDeliveries(deliveries)
                }

                return MediatorResult.Success(endOfPaginationReached = false)
            } else {
                database.withTransaction {

                    deliveries.forEach {
                        val oldDeliveries = database.deliveryDao.getDeliveryById(it.uId)
                        if (oldDeliveries != null) {
                            it.isFavourite = oldDeliveries.isFavourite
                        }
                    }
                    database.deliveryDao.upsertMyDeliveries(deliveries)
                }

                // If the end of the list, reset page for infinite scroll
                if (deliveries.isEmpty() && loadType == LoadType.APPEND) {
                    currentPage = 1 // Reset for infinite scroll
                }
                MediatorResult.Success(endOfPaginationReached = false)
            }

        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}