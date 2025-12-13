package com.flowshare.ui.screen.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.flowshare.data.model.Post
import com.flowshare.data.repository.MockRepository
import com.flowshare.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(
    innerNavController: NavHostController,
    mainNavController: NavController
) {
    // 获取模拟的动态数据
    val posts = MockRepository.getFeedPosts()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        item {
            Text(
                text = "开发者动态",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(16.dp)
            )
        }

        items(posts) { post ->
            PostCard(
                post = post,
                onPostClick = {
                    // 导航到动态详情页
                    mainNavController.navigate(Screen.PostDetail.createRoute(post.id))
                },
                onUserClick = {
                    // 导航到用户资料页
                    mainNavController.navigate(Screen.UserProfile.createRoute(post.authorId))
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostCard(
    post: Post,
    onPostClick: () -> Unit,
    onUserClick: () -> Unit
) {
    var isLiked by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        onClick = onPostClick
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // 用户信息
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(50))
                        .background(Color.LightGray)
                )

                Column(
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = MockRepository.getUser(post.authorId)?.displayName ?: "开发者",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.clickable(onClick = onUserClick)
                    )
                    Text(
                        text = "2小时前",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }

                IconButton(onClick = { /* 更多操作 */ }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "更多"
                    )
                }
            }

            // 动态内容
            Text(
                text = post.content,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(vertical = 12.dp)
            )

            // 在 PostCard 组件中，修改 AsyncImage 部分：
            if (post.imageUrls.isNotEmpty()) {
                AsyncImage(
                    model = post.imageUrls.first(),
                    contentDescription = "动态图片",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop,

                )
            }

            // 交互按钮
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // 点赞按钮
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { isLiked = !isLiked }
                    ) {
                        Icon(
                            imageVector = if (isLiked)
                                Icons.Filled.Favorite
                            else
                                Icons.Filled.FavoriteBorder,
                            contentDescription = "点赞",
                            tint = if (isLiked) Color.Red else MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Text(
                        text = "${if (isLiked) post.likes + 1 else post.likes}",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }

                // 评论按钮
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { /* 评论 */ }) {
                        Icon(
                            imageVector = Icons.Filled.Share,
                            contentDescription = "评论"
                        )
                    }
                    Text(
                        text = "${post.comments}",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }

                // 分享按钮
                IconButton(onClick = { /* 分享 */ }) {
                    Icon(
                        imageVector = Icons.Filled.Share,
                        contentDescription = "分享"
                    )
                }
            }
        }
    }
}