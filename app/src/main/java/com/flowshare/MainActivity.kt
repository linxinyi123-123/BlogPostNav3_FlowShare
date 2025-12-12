package com.flowshare

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flowshare.data.AuthManager
import com.flowshare.data.repository.MockRepository
import com.flowshare.ui.theme.FlowShareTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlowShareTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DataModelTestScreen()
                }
            }
        }
    }
}

@Composable
fun DataModelTestScreen() {
    val postCount = remember { mutableStateOf(0) }
    val userCount = remember { mutableStateOf(0) }
    val currentUser = remember { mutableStateOf("未登录") }

    // 加载数据
    LaunchedEffect(Unit) {
        val posts = MockRepository.getFeedPosts()
        postCount.value = posts.size

        // 获取用户数量
        val users = listOf("user_001", "user_002", "user_003", "user_004", "user_005")
        userCount.value = users.size

        // 模拟登录
        val authManager = AuthManager()
        currentUser.value = authManager.currentUser.value?.displayName ?: "未登录"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "🎉 Day 2 完成!",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "✅ 数据模型创建成功",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(20.dp))

        // 显示数据统计
        DataCard(title = "用户数量", value = "${userCount.value} 个")
        Spacer(modifier = Modifier.height(16.dp))
        DataCard(title = "动态数量", value = "${postCount.value} 条")
        Spacer(modifier = Modifier.height(16.dp))
        DataCard(title = "当前用户", value = currentUser.value)

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "已创建的数据模型：",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text("- User.kt (用户模型)")
        Text("- Post.kt (动态模型)")
        Text("- Message.kt (消息模型)")
        Text("- Conversation.kt (会话模型)")
        Text("- MockRepository.kt (模拟数据仓库)")
        Text("- AuthManager.kt (认证管理器)")
    }
}

@Composable
fun DataCard(title: String, value: String) {
    Surface(
        modifier = Modifier.padding(4.dp),
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceVariant,
        tonalElevation = 2.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}