package com.flowshare

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.flowshare.ui.theme.FlowShareTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FlowShareTheme {
                // 使用Surface作为根容器
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // 这里暂时显示空白，明天我们会添加导航
                    // 现在只显示一个简单的文本
                    androidx.compose.material3.Text(
                        text = "FlowShare - Day 1 初始化完成!",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize(androidx.compose.ui.Alignment.Center)
                    )
                }
            }
        }
    }
}

// 如果wrapContentSize没有自动导入，请添加以下import：
// import androidx.compose.ui.layout.ContentScale
// 或者让Android Studio自动导入（Alt+Enter）