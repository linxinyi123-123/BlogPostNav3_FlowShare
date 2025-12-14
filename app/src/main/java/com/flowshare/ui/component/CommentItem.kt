// 文件路径: app/src/main/java/com/flowshare/ui/component/CommentItem.kt

package com.flowshare.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.flowshare.data.model.Comment
import com.flowshare.data.repository.MockRepository
import com.flowshare.ui.navigation.Screen

@Composable
fun CommentItem(
    comment: Comment,
    navController: NavController,
    onLikeClick: () -> Unit,
    showReplyButton: Boolean = true
) {
    // 获取作者信息
    val author = MockRepository.getUser(comment.authorId) ?: com.flowshare.data.model.User.empty()

    // 评论状态
    var isLiked by remember { mutableStateOf(comment.isLiked) }
    var likes by remember { mutableStateOf(comment.likes) }
// 添加回复状态
    var showReplyInput by remember { mutableStateOf(false) }
    var replyContent by remember { mutableStateOf("") }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            // 评论作者信息
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 用户头像
                androidx.compose.foundation.Image(
                    painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(author.avatarUrl)
                            .crossfade(true)
                            .build()
                    ),
                    contentDescription = "用户头像",
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .clickable {
                            navController.navigate(Screen.UserProfile.createRoute(author.id))
                        }
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(8.dp))

                // 用户信息和时间
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = author.displayName,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.clickable {
                            navController.navigate(Screen.UserProfile.createRoute(author.id))
                        }
                    )
                    Text(
                        text = comment.getTimeAgo(),
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }

                // 更多选项按钮
                IconButton(
                    onClick = { /* 更多操作 */ },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "更多",
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 评论内容
            Text(
                text = comment.content,
                fontSize = 14.sp,
                lineHeight = 18.sp,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 评论操作栏
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 点赞按钮
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable {
                        isLiked = !isLiked
                        likes = if (isLiked) likes + 1 else likes - 1
                        onLikeClick()
                    }
                ) {
                    Icon(
                        imageVector = if (isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "点赞",
                        tint = if (isLiked) MaterialTheme.colorScheme.error
                        else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = likes.toString(),
                        fontSize = 12.sp,
                        color = if (isLiked) MaterialTheme.colorScheme.error
                        else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }

                // 回复按钮
                if (showReplyButton) {
                    TextButton(
                        onClick = { /* 回复评论 */ },
                        modifier = Modifier.height(24.dp)
                    ) {
                        Text(
                            text = "回复",
                            fontSize = 12.sp
                        )
                    }
                }
            }

            // 显示回复（如果有）
            if (comment.replies.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Column(
                    modifier = Modifier.padding(start = 16.dp)
                ) {
                    comment.replies.forEach { reply ->
                        CommentItem(
                            comment = reply,
                            navController = navController,
                            onLikeClick = { /* 点赞回复 */ },
                            showReplyButton = false
                        )
                    }
                }
            }
        }
    }
}