package com.example.catfact.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.catfact.models.Item

@Composable
fun DetailsScreen(navController: NavController, navArgs: NavBackStackEntry) {
    val itemName = navArgs.arguments?.getString("itemName") ?: "Item Name Not Found"
    val itemPrice = navArgs.arguments?.getString("price") ?: "Price Not Available"
    val itemImage = navArgs.arguments?.getString("image")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.width(IntrinsicSize.Max),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Check if itemImage is not null before displaying the image
            if (itemImage != null) {
                Image(
                    painter = rememberAsyncImagePainter(itemImage),
                    contentDescription = null, // Provide a proper content description
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(bottom = 16.dp)
                )
            }

            // Display item details below the image
            Text(
                text = itemName,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Price: $itemPrice",
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Button to go back to the previous screen
            Button(
                onClick = { navController.popBackStack() },
            ) {
                Text(text = "Go Back")
            }
        }
    }
}
