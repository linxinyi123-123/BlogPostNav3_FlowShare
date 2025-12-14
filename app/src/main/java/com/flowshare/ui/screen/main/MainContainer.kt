package com.flowshare.ui.screen.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.flowshare.data.AuthManager
import com.flowshare.ui.navigation.Screen
import com.flowshare.ui.screen.feed.FeedScreen
import com.flowshare.ui.screen.messages.MessagesScreen
import com.flowshare.ui.screen.search.SearchScreen
import com.flowshare.ui.theme.FlowShareTheme

/**
 * 主容器组件 - 包含底部导航和五个标签页
 * 使用多返回栈保持每个标签页的状态
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContainer(
    navController: NavHostController
) {
    val authManager = remember { AuthManager() }

    // 底部导航标签列表
    val tabs = Screen.getBottomNavigationItems()

    // 用于内部标签页导航的独立NavController
    val innerNavController = rememberNavController()

    // 处理发布按钮点击
    val onCreatePostClick: () -> Unit = {
        if (authManager.isLoggedIn) {
            // 已登录，导航到发布页面
            navController.navigate(Screen.CreatePost.route)
        } else {
            // 未登录，跳转到登录页
            navController.navigate(Screen.Login.route) {
                popUpTo(Screen.Main.route) { saveState = true }
            }
        }
    }

    Scaffold(
        bottomBar = {
            // 底部导航栏
            BottomNavigationBar(
                innerNavController = innerNavController,
                mainNavController = navController,
                tabs = tabs,
                authManager = authManager,
                onCreatePostClick = onCreatePostClick
            )
        }
    ) { paddingValues ->
        // 内部导航图 - 管理五个标签页
        NavHost(
            navController = innerNavController,
            startDestination = Screen.Feed.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            // ============ 动态流页面 ============
            composable(route = Screen.Feed.route) {
                FeedScreen(
                    innerNavController = innerNavController,
                    mainNavController = navController
                )
            }

            // ============ 搜索页面 ============
            composable(route = Screen.Search.route) {
                SearchScreen(
                    innerNavController = innerNavController,
                    mainNavController = navController
                )
            }

            // ============ 发布页面 ============
            composable(route = Screen.CreatePost.route) {
                // 显示加载状态，然后跳转到主导航的发布页面
                LaunchedEffect(Unit) {
                    // 检查登录状态
                    if (authManager.isLoggedIn) {
                        navController.navigate(Screen.CreatePost.route)
                    } else {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Main.route) { saveState = true }
                        }
                    }
                }

                // 显示加载状态
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            // ============ 消息页面 ============
            composable(route = Screen.Messages.route) {
                MessagesScreen(
                    innerNavController = innerNavController,
                    mainNavController = navController
                )
            }

            // ============ 个人资料页面 ============
            composable(route = Screen.Profile.route) {
                com.flowshare.ui.screen.profile.ProfileScreen(
                    navController = navController,
                    userId = "current_user"
                )
            }
        }
    }
}

/**
 * 底部导航栏组件
 * 实现多返回栈的关键：保存和恢复每个标签页的状态
 */
@Composable
fun BottomNavigationBar(
    innerNavController: NavHostController,
    mainNavController: NavHostController,
    tabs: List<Screen>,
    authManager: AuthManager,
    onCreatePostClick: () -> Unit
) {
    // 获取当前导航堆栈状态
    val navBackStackEntry by innerNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        tabs.forEach { screen ->
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any {
                    it.route == screen.route
                } == true,

                onClick = {
                    if (screen == Screen.CreatePost) {
                        // 发布按钮：检查登录状态
                        if (authManager.isLoggedIn) {
                            // 已登录，导航到发布页面（在主导航中）
                            mainNavController.navigate(Screen.CreatePost.route)
                        } else {
                            // 未登录，跳转到登录页
                            mainNavController.navigate(Screen.Login.route) {
                                popUpTo(Screen.Main.route) { saveState = true }
                            }
                        }
                    } else {
                        // 其他标签页：使用内部导航
                        innerNavController.navigate(screen.route) {
                            // 🎯 多返回栈的关键配置 🎯
                            // popUpTo：返回到导航图的起始点
                            popUpTo(innerNavController.graph.findStartDestination().id) {
                                // 保存当前标签页的状态
                                saveState = true
                            }

                            // 如果已经在这个标签页的栈顶，不再创建新实例
                            launchSingleTop = true

                            // 恢复之前保存的状态
                            restoreState = true
                        }
                    }
                },

                icon = {
                    val icon: ImageVector? = if (currentDestination?.hierarchy?.any {
                            it.route == screen.route
                        } == true) {
                        // 选中状态使用填充图标
                        screen.iconFilled ?: screen.icon
                    } else {
                        // 未选中状态使用轮廓图标
                        screen.icon
                    }

                    icon?.let {
                        Icon(
                            imageVector = it,
                            contentDescription = screen.title
                        )
                    }
                },

                label = {
                    Text(screen.title)
                }
            )
        }
    }
}

// 预览组件
@Preview(showBackground = true)
@Composable
fun MainContainerPreview() {
    FlowShareTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            MainContainer(navController = rememberNavController())
        }
    }
}