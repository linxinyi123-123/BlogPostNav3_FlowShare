// 修改 ProfileScreen.kt
package com.flowshare.ui.screen.profile

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.flowshare.viewmodel.AuthViewModel  // 添加导入

@Composable
fun ProfileScreen(
    navController: NavController, // 主导航控制器
    userId: String,
    authViewModel: AuthViewModel? = null  // 新增：可选参数
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
                navController = nestedNavController,
                userId = userId,
                mainNavController = navController,
                authViewModel = authViewModel  // 传递 AuthViewModel
            )
        }

        // 粉丝列表页面
        composable("followers") {
            UserFollowersScreen(
                navController = nestedNavController,
                userId = userId,
                mainNavController = navController
            )
        }

        // 关注列表页面
        composable("following") {
            UserFollowingScreen(
                navController = nestedNavController,
                userId = userId,
                mainNavController = navController
            )
        }
    }
}