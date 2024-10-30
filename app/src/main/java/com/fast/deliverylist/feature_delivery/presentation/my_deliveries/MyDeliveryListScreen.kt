package com.fast.deliverylist.feature_delivery.presentation.my_deliveries

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.fast.deliverylist.R
import com.fast.deliverylist.feature_delivery.domain.model.Delivery
import com.fast.deliverylist.feature_delivery.presentation.my_deliveries.viewmodels.MyDeliveriesViewModel
import com.fast.deliverylist.feature_delivery.presentation.utils.items

@Composable
fun MyDeliveryListScreen(
    viewmodel: MyDeliveriesViewModel,
    deliveries: LazyPagingItems<Delivery>,
    itemClickListener: (Delivery) -> Unit,
    recompose: Int = 0
) {

    val deliveryState = remember {
        mutableIntStateOf(recompose)
    }

    Column(
        modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.my_deliveries),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),

            )

        Box(modifier = Modifier.fillMaxSize()) {
            if (deliveries.loadState.refresh is LoadState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                key(deliveryState) {
                    LazyColumn(
                        state = viewmodel.llState,
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        userScrollEnabled = true
                    ) {

                        items(deliveries,
                            key = { delivery -> delivery.id + delivery.isFavourite }) { delivery ->
                            delivery?.let { DeliveryItemRow(it, itemClickListener) }
                        }

                        item {
                            if (deliveries.loadState.append is LoadState.Loading) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                }
            }
        }
    }

}