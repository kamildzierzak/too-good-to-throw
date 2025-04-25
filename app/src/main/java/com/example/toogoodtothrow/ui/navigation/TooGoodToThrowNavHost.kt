package com.example.toogoodtothrow.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.toogoodtothrow.ui.screens.product_form.ProductFormScreen
import com.example.toogoodtothrow.ui.screens.product_list.ProductListScreen

@Composable
fun TooGoodToThrowNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = ProductListDestination.route,
        modifier = modifier
    ) {
        // Product list
        composable(route = ProductListDestination.route) {
            ProductListScreen(
                navigateToProductDetail = {
                    navController.navigate(ProductEditDestination.createRoute(null))
                },
                navigateToProductEdit = { productId ->
                    navController.navigate(ProductEditDestination.createRoute(productId))
                }
            )
        }

        // Product add, edit and detail
        composable(
            route = ProductEditDestination.routeWithArgs,
            arguments = listOf(
                navArgument(ProductEditDestination.PRODUCT_ID_ARG) {
                    type = NavType.IntType
                    defaultValue = -1  // it means new product
                    nullable = false
                }
            )
        ) { backStack ->
            val rawId = backStack.arguments?.getInt(ProductEditDestination.PRODUCT_ID_ARG) ?: -1
            val productId: Int? = rawId.takeIf { it >= 0 }

            ProductFormScreen(
                productId = productId,
                onNavigateBack = { navController.popBackStack() },
                onSaveFinished = { navController.popBackStack() }
            )
        }
    }
}
