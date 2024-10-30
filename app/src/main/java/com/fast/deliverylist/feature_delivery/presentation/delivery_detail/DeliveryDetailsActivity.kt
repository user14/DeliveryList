package com.fast.deliverylist.feature_delivery.presentation.delivery_detail

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.gson.Gson
import com.fast.deliverylist.feature_delivery.domain.model.Delivery
import com.fast.deliverylist.feature_delivery.presentation.my_deliveries.viewmodels.MyDeliveriesViewModel
import com.fast.deliverylist.ui.theme.DeliveryListTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeliveryDetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val delivery: Delivery =
            Gson().fromJson(intent.getStringExtra("delivery").toString(), Delivery::class.java)


        setContent {
            DeliveryListTheme {
                val viewModel = hiltViewModel<MyDeliveriesViewModel>()
                viewModel.setDelivery(delivery)
                DeliveryDetailsScreen(delivery = viewModel.getDelivery()!!,
                    addToFavClick = {
                        viewModel.updateDelivery()
                    }, backClick = {
                        setResultAndFinish(viewModel.getDelivery()!!)
                    })

                BackHandler {
                    setResultAndFinish(viewModel.getDelivery()!!)
                }
            }
        }

    }

    fun setResultAndFinish(delivery: Delivery) {

        //set result okay here and pass delivery object in intent
        val resultIntent = Intent()
        resultIntent.putExtra("delivery", Gson().toJson(delivery))
        setResult(RESULT_OK, resultIntent)
        finish()
    }

}