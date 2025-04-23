package com.example.toogoodtothrow.ui.navigation

import com.example.toogoodtothrow.R

object ProductEditDestination : NavigationDestination {
    const val PRODUCT_ID_ARG = "productId"
    override val route: String = "product_edit"
    override val titleRes: Int = R.string.edit_product

    val routeWithArgs get() = "$route?$PRODUCT_ID_ARG={$PRODUCT_ID_ARG}"

    fun createRoute(productId: Int?): String {
        val id = productId?.toString() ?: "-1"
        return "$route?$PRODUCT_ID_ARG=$id"
    }
}
