package com.flowshare.ui.navigation

import android.net.Uri
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * 应用中的所有屏幕（页面）路由定义
 * 使用sealed class实现类型安全导航
 */
sealed class Screen(
    val route: String,
    val title: String = "",
    val icon: ImageVector? = null,
    val iconFilled: ImageVector? = null
) {
    // ================== 认证流程 ==================
    object Welcome : Screen(
        route = "welcome",
        title = "欢迎"
    )

    object Login : Screen(
        route = "login",
        title = "登录"
    )

    object Register : Screen(
        route = "register",
        title = "注册"
    )

    // ================== 主应用页面 ==================
    object Main : Screen(
        route = "main",
        title = "首页"
    )

    object Feed : Screen(
        route = "feed",
        title = "动态",
        icon = Icons.Outlined.Home,
        iconFilled = Icons.Filled.Home
    )

    object Search : Screen(
        route = "search",
        title = "搜索",
        icon = Icons.Outlined.Search,
        iconFilled = Icons.Filled.Search
    )

    object CreatePost : Screen(
        route = "create_post",
        title = "发布",
        icon = Icons.Outlined.Add,
        iconFilled = Icons.Outlined.Add
    )

    object Messages : Screen(
        route = "messages",
        title = "消息",
        icon = Icons.Outlined.ChatBubbleOutline,
        iconFilled = Icons.Outlined.ChatBubbleOutline
    )

    object Profile : Screen(
        route = "profile",
        title = "个人资料",
        icon = Icons.Outlined.Person,
        iconFilled = Icons.Filled.Person
    )

    // ================== 二级页面 ==================
    object PostDetail : Screen(
        route = "post/{postId}",
        title = "动态详情"
    ) {
        // 创建带参数的路由
        fun createRoute(postId: String) = "post/$postId"
    }

    object UserProfile : Screen(
        route = "user/{userId}",
        title = "用户资料"
    ) {
        fun createRoute(userId: String) = "user/$userId"
    }

    object Chat : Screen(
        route = "chat/{userId}",
        title = "聊天"
    ) {
        fun createRoute(userId: String) = "chat/$userId"
    }

    object Settings : Screen(
        route = "settings",
        title = "设置"
    )

    object Notifications : Screen(
        route = "notifications",
        title = "通知"
    )

    object FullScreenImage : Screen(
        route = "fullscreen_image/{imageUrl}",
        title = "全屏图片"
    ) {
        fun createRoute(imageUrl: String) = "fullscreen_image/${Uri.encode(imageUrl)}"
    }

    // ================== 实用函数 ==================
    companion object {
        /**
         * 获取所有底部导航标签页
         */
        fun getBottomNavigationItems(): List<Screen> {
            return listOf(Feed, Search, CreatePost, Messages, Profile)
        }

        /**
         * 根据route获取Screen对象
         */
        fun fromRoute(route: String?): Screen? {
            return when {
                route?.startsWith("post/") == true -> PostDetail
                route?.startsWith("user/") == true -> UserProfile
                route?.startsWith("chat/") == true -> Chat
                route?.startsWith("image/") == true -> FullScreenImage
                else -> when (route) {
                    Welcome.route -> Welcome
                    Login.route -> Login
                    Register.route -> Register
                    Main.route -> Main
                    Feed.route -> Feed
                    Search.route -> Search
                    CreatePost.route -> CreatePost
                    Messages.route -> Messages
                    Profile.route -> Profile
                    Settings.route -> Settings
                    Notifications.route -> Notifications
                    else -> null
                }
            }
        }
    }
}