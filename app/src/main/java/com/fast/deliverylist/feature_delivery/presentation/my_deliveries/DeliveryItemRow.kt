package com.fast.deliverylist.feature_delivery.presentation.my_deliveries

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.gson.Gson
import com.fast.deliverylist.R
import com.fast.deliverylist.feature_delivery.domain.model.Delivery

@Composable
fun DeliveryItemRow(
    delivery: Delivery,
    itemClickListener: (Delivery) -> Unit
) {
    val isFavourite by remember { mutableStateOf(delivery.isFavourite) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                itemClickListener(delivery)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)// Adjust size as needed
                .clip(RoundedCornerShape(8.dp)) // Set the corner radius here
        ) {
            AsyncImage(
                model = delivery.goodsPicture,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(8.dp)),
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp)
        ) {
            Text(text = delivery.route.start, fontSize = 16.sp)
            Text(text = delivery.route.end, fontSize = 16.sp)
        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End
        ) {

            if (isFavourite) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_favorite_24),
                    contentDescription = "Favourite Icon",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(24.dp)

                )
            } else {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                ) {}
            }
            Text(
                text = delivery.getTotalDeliveryPrice(),
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun DeliveryItemRowPreview() {
    DeliveryItemRow(
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
                    "  }", Delivery::class.java
        )
    ) {

    }
}