package com.flowshare

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.flowshare.ui.navigation.FlowShareNavHost
import com.flowshare.ui.theme.FlowShareTheme
import com.flowshare.viewmodel.AuthViewModel
import android.net.Uri

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlowShareTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FlowShareApp(intent?.data)  // 传递Intent的data（可能是深链接URI）
                }
            }
        }
    }
}

@Composable
fun FlowShareApp(initialUri: Uri? = null) {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()

    // 处理初始深链接
    LaunchedEffect(initialUri) {
        initialUri?.let { uri ->
            // 延迟一下，确保导航已经准备好
            kotlinx.coroutines.delay(100)
            navController.navigate(uri)
        }
    }

    FlowShareNavHost(
        navController = navController,
        authViewModel = authViewModel
    )
}