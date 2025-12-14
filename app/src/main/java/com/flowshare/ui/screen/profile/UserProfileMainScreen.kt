package com.flowshare.ui.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.flowshare.data.model.User
import com.flowshare.data.repository.MockRepository
import com.flowshare.ui.component.PostCard
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import com.flowshare.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileMainScreen(
    navController: NavController, // 嵌套导航控制器
    userId: String,
    mainNavController: NavController? = null // 主导航控制器（可选）
) {
    // 获取用户信息
    val user = MockRepository.getUser(userId) ?: User.empty()
    val isCurrentUser = userId == "current_user"

    // 获取用户的所有帖子
    val userPosts = MockRepository.getPostsByUserId(userId)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = user.displayName,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    if (!isCurrentUser) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "返回"
                            )
                        }
                    }
                },
                actions = {
                    if (isCurrentUser) {
                        IconButton(onClick = {
                            // 导航到设置页面（主导航栈）
                            // 注意：这里需要访问主导航控制器，我们需要传递主navController
                            // 暂时使用占位，Day 9会实现
                        }) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "设置"
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            // 头部信息部分
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    // 用户头像和信息行
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // 头像
                        androidx.compose.foundation.Image(
                            painter = rememberAsyncImagePainter(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(user.avatarUrl)
                                    .crossfade(true)
                                    .build()
                            ),
                            contentDescription = "用户头像",
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
                            contentScale = ContentScale.Crop
                        )

                        Spacer(modifier = Modifier.width(20.dp))

                        // 统计信息
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = userPosts.size.toString(),
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = "帖子",
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                    )
                                }

                                // 粉丝 - 点击可导航到粉丝列表
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center,
                                    modifier = Modifier.clickable {
                                        navController.navigate("followers")
                                    }
                                ) {
                                    Text(
                                        text = user.followers.toString(),
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = "粉丝",
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                    )
                                }

                                // 关注 - 点击可导航到关注列表
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center,
                                    modifier = Modifier.clickable {
                                        navController.navigate("following")
                                    }
                                ) {
                                    Text(
                                        text = user.following.toString(),
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = "关注",
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // 用户名和简介
                    Column {
                        Text(
                            text = user.displayName,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "@${user.username}",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = user.bio,
                            fontSize = 14.sp,
                            lineHeight = 20.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            // 帖子列表标题
            item {
                Text(
                    text = "帖子",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            // 帖子列表
            if (userPosts.isEmpty()) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        androidx.compose.material3.Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = null,
                            modifier = Modifier.size(48.dp),
                            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "还没有发布任何帖子",
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        )
                    }
                }
            } else {
                // 修改帖子列表中的导航
                items(userPosts) { post ->
                    PostCard(
                        post = post,
                        onPostClick = {
                            // 使用主导航控制器导航到动态详情页
                            mainNavController?.navigate(Screen.PostDetail.createRoute(post.id))
                        },
                        onUserClick = {
                            // 已经是用户资料页，不需要跳转
                        },
                        showUserInfo = false
                    )
                }
            }

            // 底部留白
            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}