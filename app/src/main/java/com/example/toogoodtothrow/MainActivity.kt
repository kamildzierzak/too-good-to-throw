package com.example.toogoodtothrow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.toogoodtothrow.presentation.MainNavGraph
import com.example.toogoodtothrow.presentation.theme.TooGoodToThrowTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TooGoodToThrowTheme {
                MainNavGraph()
            }
        }
    }
}