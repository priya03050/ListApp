package com.example.catfact.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.catfact.models.Item
import com.example.catfact.ui.theme.DarkGreen
import com.example.catfact.ui.theme.Purple80

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavBar(
    itemsList: MutableState<List<Item>>,
    navController: NavHostController,

) {

    Column(
        modifier = Modifier
            .background(color = Purple80)
            .fillMaxWidth()
            .padding(22.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Explore",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.Black
                ),
                modifier = Modifier.padding(15.dp)
            )

            Text(
                text = "Filter",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = DarkGreen
                ),
                modifier = Modifier
                    .padding(13.dp)
                    .clickable {


                    }
            )
        }

        AppSearchBar(itemsList = itemsList, navController = navController)



    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppSearchBar(itemsList: MutableState<List<Item>>, navController: NavHostController) {
    var queryString by remember { mutableStateOf("") }
    val context = LocalContext.current

    // Function to find item by name in the list
    fun findItemByName(name: String): Item? {
        return itemsList.value.find { it.name.equals(name, ignoreCase = true) }
    }

    val textFieldColors = TextFieldDefaults.textFieldColors(
        textColor=Color.Black ,
        disabledTextColor=Color.White,
        containerColor=Color.White,
        cursorColor=Color.White,
        errorCursorColor=Color.White,
        //  selectionColors=Color.White,
        focusedIndicatorColor=Color.White,
        unfocusedIndicatorColor=Color.White,
        disabledIndicatorColor=Color.White,
        errorIndicatorColor=Color.White,
        focusedLeadingIconColor=Color.White,
        unfocusedLeadingIconColor=Color.White,
        disabledLeadingIconColor=Color.White,
        errorLeadingIconColor=Color.White,
        focusedTrailingIconColor=Color.White,
        unfocusedTrailingIconColor=Color.White,
        disabledTrailingIconColor=Color.White,
        errorTrailingIconColor=Color.White,
        focusedLabelColor=Color.Black,
        unfocusedLabelColor=Color.Black,
        disabledLabelColor=Color.Black,
        errorLabelColor=Color.White,
        placeholderColor=Color.Black,
        disabledPlaceholderColor=Color.White,

        )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(CircleShape)
            .background(Color.White)
            .border(1.dp, Color.White, CircleShape) // Set border shape and color
    ) {
        TextField(
            value = queryString,
            onValueChange = { newQueryString ->
                queryString = newQueryString
            },
            label = { Text("Search") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    val foundItem = findItemByName(queryString)
                    if (foundItem != null) {
                        navController.navigate(
                            "DetailsScreen/${foundItem.name}?price=${foundItem.price}&image=${foundItem.image}"
                        )

                        Toast.makeText(
                            context,
                            "Item found: ${foundItem.name}",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            context,
                            "Item not found for query: $queryString",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            ),
            modifier = Modifier
                .fillMaxWidth(0.8f) // Decrease width by 80% of the available space
                .background(Color.White) // Set background color for the TextField area
                .padding(horizontal = 8.dp), // Adjust padding inside the TextField
            colors = textFieldColors
        )
    }
}




