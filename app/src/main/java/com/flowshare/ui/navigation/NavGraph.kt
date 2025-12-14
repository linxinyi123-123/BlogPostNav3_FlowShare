package com.flowshare.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.flowshare.viewmodel.AuthViewModel
import com.flowshare.ui.screen.auth.LoginScreen
import com.flowshare.ui.screen.auth.RegisterScreen
import com.flowshare.ui.screen.auth.WelcomeScreen
import com.flowshare.ui.screen.main.MainContainer
import com.flowshare.ui.screen.post.PostDetailScreen
import com.flowshare.ui.screen.profile.ProfileScreen
import com.flowshare.ui.screen.createpost.CreatePostScreen
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import com.flowshare.ui.screen.settings.SettingsScreen

/**
 * 应用的主导航图
 * 包含所有可导航的目的地
 */
@Composable
fun FlowShareNavHost(
    navController: NavHostController,
    authViewModel: AuthViewModel, // 新增参数
    modifier: Modifier = Modifier,
    startDestination: String = Screen.Welcome.route
) {
    // 监听登录状态变化
    val isLoggedIn = authViewModel.isLoggedIn.collectAsState().value

    // 根据登录状态动态决定起始页面
    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn && navController.currentDestination?.route != Screen.Main.route) {
            // 如果已登录且不在主页面，导航到主页面
            navController.navigate(Screen.Main.route) {
                popUpTo(Screen.Welcome.route) { inclusive = true }
            }
        } else if (!isLoggedIn && navController.currentDestination?.route != Screen.Welcome.route) {
            // 如果未登录且不在欢迎页，导航到欢迎页
            navController.navigate(Screen.Welcome.route) {
                popUpTo(0) { inclusive = true }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        // ================== 认证流程 ==================
        composable(
            route = Screen.Welcome.route,
            enterTransition = {
                fadeIn(animationSpec = tween(300))
            },
            exitTransition = {
                fadeOut(animationSpec = tween(300))
            }
        ) {
            WelcomeScreen(
                onLoginClick = {
                    navController.navigate(Screen.Login.route)
                },
                onRegisterClick = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }

        composable(
            route = Screen.Login.route,
            enterTransition = {
                when (initialState.destination.route) {
                    Screen.Welcome.route -> slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Start,
                        animationSpec = tween(300)
                    )
                    else -> null
                }
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(300))
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(300))
            }
        ) {
            LoginScreen(
                authViewModel = authViewModel, // 传递 ViewModel
                onLoginSuccess = {
                    // 登录成功后导航到主页面
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.Welcome.route) { inclusive = true }
                    }
                },
                onRegisterClick = {
                    navController.navigate(Screen.Register.route)
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Screen.Register.route,
            enterTransition = {
                when (initialState.destination.route) {
                    Screen.Login.route -> slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.End,
                        animationSpec = tween(300)
                    )
                    else -> null
                }
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(300))
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(300))
            }
        ) {
            RegisterScreen(
                authViewModel = authViewModel, // 传递 ViewModel
                onRegisterSuccess = {
                    // 注册成功后导航到主页面
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.Welcome.route) { inclusive = true }
                    }
                },
                onLoginClick = {
                    navController.navigate(Screen.Login.route)
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        // ================== 主应用页面 ==================
        composable(
            route = Screen.Main.route,
            enterTransition = {
                fadeIn(animationSpec = tween(500))
            },
            exitTransition = {
                fadeOut(animationSpec = tween(300))
            }
        ) {
            MainContainer(
                navController = navController,
                authViewModel = authViewModel // 传递 ViewModel
            )
        }

        // ================== 发布页面 ==================
// 修改 CreatePost 路由部分
        composable(
            route = Screen.CreatePost.route,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Up,
                    animationSpec = tween(300)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Down,
                    animationSpec = tween(300)
                )
            }
        ) { backStackEntry ->
            // 收集登录状态
            val isLoggedIn by authViewModel.isLoggedIn.collectAsState()

            // 检查登录状态，未登录则跳转到登录页
            LaunchedEffect(isLoggedIn) {
                if (!isLoggedIn) {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Main.route) { saveState = true }
                    }
                }
            }

            if (isLoggedIn) {
                CreatePostScreen(
                    navController = navController,
                    onPostCreated = {
                        // 发布成功后返回到 Feed 页
                        navController.navigate(Screen.Main.route) {
                            popUpTo(Screen.Main.route) { inclusive = false }
                        }
                    }
                )
            } else {
                // 显示加载状态
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }

        // ================== 二级页面 ==================
        composable(
            route = Screen.PostDetail.route,
            arguments = listOf(
                navArgument("postId") {
                    type = androidx.navigation.NavType.StringType
                }
            ),
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(300)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(300)
                )
            }
        ) { backStackEntry ->
            val postId = backStackEntry.arguments?.getString("postId") ?: ""
            // ✅ Day 5：使用真正的动态详情页
            PostDetailScreen(
                postId = postId,
                navController = navController
            )
        }

        composable(
            route = Screen.UserProfile.route,
            arguments = listOf(
                navArgument("userId") {
                    type = androidx.navigation.NavType.StringType
                }
            ),
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(300)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(300)
                )
            }
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            ProfileScreen(
                navController = navController,
                userId = userId
            )
        }

        composable(
            route = Screen.Settings.route,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(300)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(300)
                )
            }
        ) {
            SettingsScreen(
                navController = navController,
                authViewModel = authViewModel  // 传递 AuthViewModel
            )
        }

        composable(
            route = Screen.Notifications.route,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(300)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(300)
                )
            }
        ) {
            // 我们将在后续实现通知页面
            ProfileScreen(
                navController = navController,
                userId = "current_user"
            )
        }
    }
}