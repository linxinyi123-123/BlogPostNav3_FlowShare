package com.flowshare

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.flowshare.ui.navigation.FlowShareNavHost
import com.flowshare.ui.navigation.Screen
import com.flowshare.ui.screen.auth.LoginScreen
import com.flowshare.ui.screen.auth.RegisterScreen
import com.flowshare.ui.screen.auth.WelcomeScreen
import com.flowshare.ui.screen.main.MainContainer
import com.flowshare.ui.theme.FlowShareTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.navigation.compose.currentBackStackEntryAsState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlowShareTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FlowShareApp()
                }
            }
        }
    }
}

@Composable
fun FlowShareApp() {
    val navController = rememberNavController()

    FlowShareNavHost(
        navController = navController,
        startDestination = Screen.Welcome.route
    )
}