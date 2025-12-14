// 文件路径: app/src/main/java/com/flowshare/ui/screen/profile/ProfileScreen.kt

package com.flowshare.ui.screen.profile

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun ProfileScreen(
    navController: NavController, // 主导航控制器
    userId: String
) {
    // 为嵌套导航创建一个独立的 NavController
    val nestedNavController = rememberNavController()

    // 嵌套导航图
    NavHost(
        navController = nestedNavController,
        startDestination = "main",
        route = "user_flow/$userId"
    ) {
        // 主页面
        composable("main") {
            UserProfileMainScreen(
                navController = nestedNavController, // 传递嵌套导航控制器
                userId = userId,
                mainNavController = navController // 传递主导航控制器
            )
        }

        // 粉丝列表页面
        composable("followers") {
            UserFollowersScreen(
                navController = nestedNavController,
                userId = userId,
                mainNavController = navController // 传递主导航控制器
            )
        }

        // 关注列表页面
        composable("following") {
            UserFollowingScreen(
                navController = nestedNavController,
                userId = userId,
                mainNavController = navController // 传递主导航控制器
            )
        }
    }
}