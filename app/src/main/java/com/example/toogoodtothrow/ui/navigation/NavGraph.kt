package com.example.toogoodtothrow.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.toogoodtothrow.ui.screens.product_list.ProductListDestination
import com.example.toogoodtothrow.ui.screens.product_list.ProductListScreen

interface NavigationDestination {
    val route: String
    val titleRes: Int
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = ProductListDestination.route,
        modifier = modifier
    ) {
        composable(route = ProductListDestination.route) {
            ProductListScreen(
                navigateToProductDetail = {
                    TODO()
                },
                navigateToProductUpdate = {
                    TODO()
                }
            )
        }
    }
}