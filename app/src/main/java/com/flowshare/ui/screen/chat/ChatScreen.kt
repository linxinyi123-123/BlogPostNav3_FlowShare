package com.flowshare.ui.screen.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.flowshare.data.model.Message
import com.flowshare.data.model.User
import com.flowshare.data.repository.MockRepository
import kotlinx.coroutines.launch
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    userId: String,
    navController: NavController
) {
    // 获取对方用户信息
    val otherUser = MockRepository.getUser(userId) ?: User.empty()

    // 聊天消息状态
    val messages = remember { mutableStateListOf<Message>() }
    var inputText by remember { mutableStateOf(TextFieldValue()) }
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    // 初始化模拟消息
    LaunchedEffect(Unit) {
        // 添加一些模拟消息
        messages.addAll(
            listOf(
                Message(
                    id = "msg_001",
                    senderId = userId,
                    receiverId = "current_user",
                    content = "你好！看到你发布的动态很有趣",
                    timestamp = System.currentTimeMillis() - 3600000
                ),
                Message(
                    id = "msg_002",
                    senderId = "current_user",
                    receiverId = userId,
                    content = "谢谢！我也刚看了你的项目",
                    timestamp = System.currentTimeMillis() - 1800000
                ),
                Message(
                    id = "msg_003",
                    senderId = userId,
                    receiverId = "current_user",
                    content = "我们可以交流一下技术实现吗？",
                    timestamp = System.currentTimeMillis() - 600000
                ),
                Message(
                    id = "msg_004",
                    senderId = "current_user",
                    receiverId = userId,
                    content = "当然可以！你对哪部分比较感兴趣？",
                    timestamp = System.currentTimeMillis() - 300000
                )
            )
        )

        // 滚动到底部
        scope.launch {
            listState.scrollToItem(messages.size - 1)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // 用户头像
                        androidx.compose.foundation.Image(
                            painter = rememberAsyncImagePainter(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(otherUser.avatarUrl)
                                    .crossfade(true)
                                    .build()
                            ),
                            contentDescription = "用户头像",
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Column {
                            Text(
                                text = otherUser.displayName,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "在线",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "返回"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            // 消息列表
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                state = listState,
                reverseLayout = false
            ) {
                items(messages) { message ->
                    MessageBubble(
                        message = message,
                        isCurrentUser = message.senderId == "current_user",
                        otherUser = otherUser
                    )
                }
            }

            // 消息输入区域
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(24.dp))
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 输入框
                TextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 4.dp),
                    placeholder = { Text("输入消息...") },
                    singleLine = false,
                    maxLines = 3,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        disabledContainerColor = MaterialTheme.colorScheme.surface,
                        focusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
                        unfocusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent
                    )
                )

                Spacer(modifier = Modifier.width(4.dp))

                // 发送按钮
                IconButton(
                    onClick = {
                        if (inputText.text.isNotBlank()) {
                            // 添加新消息
                            val newMessage = Message(
                                id = "msg_${System.currentTimeMillis()}",
                                senderId = "current_user",
                                receiverId = userId,
                                content = inputText.text,
                                timestamp = System.currentTimeMillis(),
                                isRead = false
                            )
                            messages.add(newMessage)

                            // 清空输入框
                            inputText = TextFieldValue()

                            // 滚动到底部
                            scope.launch {
                                listState.animateScrollToItem(messages.size - 1)
                            }
                        }
                    },
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            MaterialTheme.colorScheme.primary,
                            CircleShape
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "发送",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

            // 底部安全区域
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun MessageBubble(
    message: Message,
    isCurrentUser: Boolean,
    otherUser: User
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalAlignment = if (isCurrentUser) Alignment.End else Alignment.Start
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(
                    if (isCurrentUser)
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                    else
                        MaterialTheme.colorScheme.surfaceVariant
                )
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (!isCurrentUser) {
                // 对方头像（小）
                androidx.compose.foundation.Image(
                    painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(otherUser.avatarUrl)
                            .crossfade(true)
                            .build()
                    ),
                    contentDescription = "用户头像",
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                )
                Spacer(modifier = Modifier.width(8.dp))
            }

            Column {
                Text(
                    text = message.content,
                    fontSize = 14.sp,
                    color = if (isCurrentUser)
                        MaterialTheme.colorScheme.onPrimary
                    else
                        MaterialTheme.colorScheme.onSurface,
                    lineHeight = 18.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = formatTime(message.timestamp),
                    fontSize = 10.sp,
                    color = if (isCurrentUser)
                        MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                    else
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
            }

            if (isCurrentUser) {
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}

// 格式化时间显示（用于聊天）
fun formatTime(timestamp: Long): String {
    val date = Date(timestamp)
    val now = Date()
    val diff = now.time - timestamp

    val formatter = java.text.SimpleDateFormat("HH:mm", Locale.getDefault())

    return when {
        diff < 60 * 1000 -> "刚刚"
        diff < 60 * 60 * 1000 -> {
            val minutes = (diff / (60 * 1000)).toInt()
            "${minutes}分钟前"
        }
        diff < 24 * 60 * 60 * 1000 -> formatter.format(date)
        else -> {
            val dateFormatter = java.text.SimpleDateFormat("MM/dd HH:mm", Locale.getDefault())
            dateFormatter.format(date)
        }
    }
}