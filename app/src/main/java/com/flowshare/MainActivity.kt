// 文件路径: app/src/main/java/com/flowshare/MainActivity.kt
// 替换现有内容

package com.flowshare

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.flowshare.ui.navigation.FlowShareNavHost
import com.flowshare.ui.theme.FlowShareTheme
import com.flowshare.viewmodel.AuthViewModel

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

// 修改 MainActivity.kt 中的 FlowShareApp 函数
@Composable
fun FlowShareApp() {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()

    FlowShareNavHost(
        navController = navController,
        authViewModel = authViewModel
    )
}