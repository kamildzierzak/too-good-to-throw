package com.example.toogoodtothrow

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.toogoodtothrow.ui.navigation.TooGoodToThrowNavHost

@Composable
fun TooGoodToThrowApp(navController: NavHostController = rememberNavController()) {
    TooGoodToThrowNavHost(navController = navController)
}