package com.fast.deliverylist.feature_delivery.presentation.my_deliveries

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.gson.Gson
import com.fast.deliverylist.feature_delivery.domain.model.Delivery
import com.fast.deliverylist.feature_delivery.presentation.delivery_detail.DeliveryDetailsActivity
import com.fast.deliverylist.feature_delivery.presentation.my_deliveries.viewmodels.MyDeliveriesViewModel
import com.fast.deliverylist.ui.theme.DeliveryListTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeliveryListActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DeliveryListTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {

                    val recomposeIntState = remember {
                        mutableStateOf(0)
                    }
                    val viewModel = hiltViewModel<MyDeliveriesViewModel>()
                    val myDeliveries = viewModel.deliveryPagingFlow.collectAsLazyPagingItems()
                    val startForResult =
                        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                            if (result.resultCode == RESULT_OK) {
                                val intent = result.data
                                val delivery = Gson().fromJson(
                                    intent?.getStringExtra("delivery"),
                                    Delivery::class.java
                                )

                                val index = myDeliveries.itemSnapshotList
                                    .indexOf(myDeliveries.itemSnapshotList.find {
                                        it?.id == delivery.id
                                    })

                                myDeliveries.itemSnapshotList.items[index].isFavourite =
                                    delivery.isFavourite
                                recomposeIntState.value++

                            }
                        }

                    key(recomposeIntState.value) {
                        MyDeliveryListScreen(viewModel, myDeliveries,
                            itemClickListener = {

                                val intent =
                                    android.content.Intent(
                                        this,
                                        DeliveryDetailsActivity::class.java
                                    )
                                intent.putExtra("delivery", Gson().toJson(it))
                                startForResult.launch(intent)


                            })
                    }

                }
            }
        }

    }
}