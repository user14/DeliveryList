package com.fast.deliverylist.feature_delivery.presentation.delivery_detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.fast.deliverylist.R
import com.google.gson.Gson
import com.fast.deliverylist.feature_delivery.domain.model.Delivery

@Composable
@Preview
fun DeliveryDetailsScreenPreview() {
    DeliveryDetailsScreen(
        Gson().fromJson(
            "{\n" +
                    "    \"id\": \"5dd5f3a7156bae72fa5a5d6c\",\n" +
                    "    \"remarks\": \"Minim veniam minim nisi ullamco consequat anim reprehenderit laboris aliquip voluptate sit.\",\n" +
                    "    \"pickupTime\": \"2024-07-31T10:45:38-08:00\",\n" +
                    "    \"goodsPicture\": \"https://loremflickr.com/320/240/cat?lock=9953\",\n" +
                    "    \"deliveryFee\": \"\$92.14\",\n" +
                    "    \"surcharge\": \"\$136.46\",\n" +
                    "    \"route\": {\n" +
                    "      \"start\": \"Noble Street\",\n" +
                    "      \"end\": \"Montauk Court\"\n" +
                    "    },\n" +
                    "    \"sender\": {\n" +
                    "      \"phone\": \"+852 1234 5678\",\n" +
                    "      \"name\": \"Harding Welch\",\n" +
                    "      \"email\": \"hardingwelch@comdom.com\"\n" +
                    "    }\n" +
                    "  }",
            Delivery::class.java
        ), addToFavClick = {

        }, backClick = {

        }
    )
}

@Composable
@Preview(
    device = "spec:width=351dp,height=891dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
fun DeliveryDetailsScreenPreviewHorizontal() {
    DeliveryDetailsScreen(
        Gson().fromJson(
            "{\n" +
                    "    \"id\": \"5dd5f3a7156bae72fa5a5d6c\",\n" +
                    "    \"remarks\": \"Minim veniam minim nisi ullamco consequat anim reprehenderit laboris aliquip voluptate sit.\",\n" +
                    "    \"pickupTime\": \"2024-07-31T10:45:38-08:00\",\n" +
                    "    \"goodsPicture\": \"https://loremflickr.com/320/240/cat?lock=9953\",\n" +
                    "    \"deliveryFee\": \"\$92.14\",\n" +
                    "    \"surcharge\": \"\$136.46\",\n" +
                    "    \"route\": {\n" +
                    "      \"start\": \"Noble Street\",\n" +
                    "      \"end\": \"Montauk Court\"\n" +
                    "    },\n" +
                    "    \"sender\": {\n" +
                    "      \"phone\": \"+852 1234 5678\",\n" +
                    "      \"name\": \"Harding Welch\",\n" +
                    "      \"email\": \"hardingwelch@comdom.com\"\n" +
                    "    }\n" +
                    "  }",
            Delivery::class.java
        ), addToFavClick = {

        }, backClick = {

        }
    )
}

@Composable
fun DeliveryDetailsScreen(
    delivery: Delivery, addToFavClick: (Delivery) -> Unit,
    backClick: () -> Unit
) {
    Scaffold(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(it),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Row(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
                    .height(32.dp)
                    .align(Alignment.Start),
            ) {
                IconButton(
                    onClick = backClick
                ) {
                    Icon(imageVector = Icons.Default.ArrowBackIosNew, contentDescription = "Back")
                }
                Text(
                    text = stringResource(R.string.delivery_details),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(top = 2.dp)
                )
            }

            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .background(Color.White)
                    .padding(top=8.dp, start = 16.dp, end = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                // Delivery details
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = stringResource(id = R.string.from),
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = delivery.route.start,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = stringResource(id = R.string.to),
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(text = delivery.route.end, style = MaterialTheme.typography.bodyLarge)
                    }
                }

                // Goods to deliver
                Text(
                    text = stringResource(R.string.goods_to_deliver),
                    style = MaterialTheme.typography.bodyLarge
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                ) {
                    // Placeholder for image or other content

                    AsyncImage(
                        model = delivery.goodsPicture,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .border(
                                border = BorderStroke(1.dp, Color.Black),
                                shape = RoundedCornerShape(8.dp)
                            ),
                    )
                }

                // Delivery fee
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = R.string.delivery_fee),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = delivery.getTotalDeliveryPrice(),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }


            }

            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                var isFavourite by remember { mutableStateOf(delivery.isFavourite) }
                // Add to favorite button
                TextButton(
                    modifier = Modifier.fillMaxWidth().border(
                        BorderStroke(1.dp, Color.Black),
                        RoundedCornerShape(8.dp)
                    ),
                    onClick = {
                        isFavourite = !isFavourite
                        addToFavClick.invoke(delivery)
                    }) {
                    Text(
                        text = if (isFavourite) {
                            stringResource(id = R.string.remove_from_fav)
                        } else {
                            stringResource(id = R.string.add_to_favorite)
                        },
                        color = if (isFavourite) {
                            Color.Black
                        } else {
                            Color.Gray
                        }
                    )
                    Icon(
                        modifier = Modifier.padding(start = 8.dp),
                        painter = if (isFavourite) painterResource(id = R.drawable.baseline_favorite_24) else painterResource(
                            id = R.drawable.baseline_favorite_border_24
                        ), tint = if (isFavourite) {
                            Color.Black
                        } else {
                            Color.Gray
                        },
                        contentDescription = "Favorite"
                    )
                }
            }

        }
    }
}