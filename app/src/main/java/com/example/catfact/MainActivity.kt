package com.example.catfact

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material.BottomNavigation
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.sharp.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.catfact.models.Item
import com.example.catfact.screens.DetailsScreen
import com.example.catfact.screens.GridScreen
import com.example.catfact.screens.ListScreen
import com.example.catfact.ui.theme.CatFactTheme
import com.example.catfact.utills.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import androidx.compose.material.BottomNavigationItem as BottomNavigationItem1

class MainActivity : ComponentActivity() {
    private var itemsList = mutableStateOf(listOf<Item>())

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CatFactTheme {
                val navController = rememberNavController()
                sendRequest()
                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(navController = navController)
                    }
                ) {
                    Navigation(navController = navController)
                }
            }
        }
    }

    @Composable
    private fun BottomNavigationBar(navController: NavHostController) {
        val items = listOf("List", "Grid")
        Box(
            modifier = Modifier
                .background(Color.Yellow)

        ) {
        BottomNavigation(   backgroundColor = colorResource(id = androidx.leanback.R.color.lb_browse_header_color),
            contentColor = Color.Black) {
            val currentRoute = navController.currentDestination?.route
            items.forEach { screen ->
                BottomNavigationItem1(
                    selected = currentRoute == screen,
                    onClick = {
                        navController.navigate(screen)
                    },
                    icon = {
                        when (screen) {
                            "List" -> Icon(Icons.Sharp.Home, contentDescription = "List")
                            "Grid" -> Icon(Icons.Default.List, contentDescription = "Grid")
                            else -> Icon(Icons.Default.List, contentDescription = "List")
                        }
                    },
                    label = { Text(screen) },
                    selectedContentColor = Color.Yellow,
                    unselectedContentColor = Color.Gray
                )
            }
        }
        }
    }



    @Composable
    private fun Navigation(navController: NavHostController) {
        NavHost(navController = navController, startDestination = "List") {
            composable(route = "List") {
                ListScreen(itemsList = itemsList, navController = navController)
            }
            composable(route = "Grid") {
                GridScreen(itemsList = itemsList, navController = navController)
            }
            composable(
                "DetailsScreen/{itemName}?price={price}&image={image}",
                arguments = listOf(
                    navArgument("itemName") { type = NavType.StringType },
                    navArgument("price") { type = NavType.StringType },
                    navArgument("image") { type = NavType.StringType }
                )
            ) { navBackStackEntry ->
                DetailsScreen(navController, navBackStackEntry)
            }


        }
    }

    private fun sendRequest() {
        GlobalScope.launch(Dispatchers.IO) {
            val response = try {
                RetrofitInstance.api.getRandomFact()
            } catch (e: retrofit2.HttpException) {
                // Handle HTTP exception
                return@launch
            } catch (e: IOException) {
                // Handle IO exception
                return@launch
            }
            if (response.isSuccessful && response.body() != null) {
                withContext(Dispatchers.Main) {
                    itemsList.value = response.body()!!.data.items
                }
            }
        }
    }
}
