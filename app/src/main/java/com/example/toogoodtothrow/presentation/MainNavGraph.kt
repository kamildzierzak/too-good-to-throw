package com.example.toogoodtothrow.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.toogoodtothrow.presentation.screens.ProductDetailScreen
import com.example.toogoodtothrow.presentation.screens.ProductListScreen

@Composable
fun MainNavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "product_list") {
        composable("product_list") {
            ProductListScreen(
                onProductClick = { productId ->
                    navController.navigate("product_detail/$productId")
                },
                onAddClick = {
                    // TODO: Navigate to Add Product screen (to be implemented)
                }
            )
        }
        composable(
            route = "product_detail/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId") ?: 0
            ProductDetailScreen(productId = productId, onBack = { navController.popBackStack() })
        }
    }
}