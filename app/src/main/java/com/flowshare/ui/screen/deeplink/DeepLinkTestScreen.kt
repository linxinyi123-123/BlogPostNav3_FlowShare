package com.flowshare.ui.screen.deeplink

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.flowshare.ui.navigation.Screen
import android.net.Uri  // 添加这行导入

@Composable
fun DeepLinkTestScreen(navController: NavController) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "深链接测试",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        Text(
            text = "点击下面的按钮测试不同的深链接。这些链接会在应用内打开对应的页面。",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // 测试动态详情深链接
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "动态详情深链接",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "使用HTTPS方案: https://flowshare.app/post/post_001",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {
                            // 修复：使用 Uri.parse() 包裹深链接
                            val deepLink = "https://flowshare.app/post/post_001"
                            val uri = Uri.parse(deepLink)
                            navController.navigate(uri)
                        }
                    ) {
                        Text("HTTPS方案")
                    }

                    Button(
                        onClick = {
                            // 修复：使用 Uri.parse() 包裹深链接
                            val deepLink = "flowshare://post/post_001"
                            val uri = Uri.parse(deepLink)
                            navController.navigate(uri)
                        }
                    ) {
                        Text("自定义方案")
                    }
                }
            }
        }

        // 测试不存在的动态
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "测试不存在的动态",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "模拟一个不存在的动态ID，测试错误处理",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                Button(
                    onClick = {
                        // 修复：使用 Uri.parse() 包裹深链接
                        val deepLink = "https://flowshare.app/post/non_existing_post"
                        val uri = Uri.parse(deepLink)
                        navController.navigate(uri)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("测试不存在动态")
                }
            }
        }

        // 返回按钮
        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("返回")
        }
    }
}