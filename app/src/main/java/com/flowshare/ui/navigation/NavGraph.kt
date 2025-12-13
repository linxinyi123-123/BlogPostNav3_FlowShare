package com.flowshare.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.flowshare.ui.screen.auth.LoginScreen
import com.flowshare.ui.screen.auth.RegisterScreen
import com.flowshare.ui.screen.auth.WelcomeScreen
import com.flowshare.ui.screen.main.MainContainer
import com.flowshare.ui.screen.post.PostDetailScreen
import com.flowshare.ui.screen.profile.ProfileScreen

/**
 * 应用的主导航图
 * 包含所有可导航的目的地
 */
@Composable
fun FlowShareNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = Screen.Welcome.route
) {
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
                    // 修复1：从欢迎页到登录页，不要popUpTo欢迎页
                    navController.navigate(Screen.Login.route)
                },
                onRegisterClick = {
                    // 修复2：从欢迎页到注册页，不要popUpTo欢迎页
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
                onLoginSuccess = {
                    // 修复3：登录成功后跳转到主页面，并清除整个认证栈
                    navController.navigate(Screen.Main.route) {
                        // 清除从欢迎页到当前页的所有页面
                        popUpTo(Screen.Welcome.route) { inclusive = true }
                    }
                },
                onRegisterClick = {
                    // 修复4：从登录页到注册页，正常跳转
                    navController.navigate(Screen.Register.route)
                },
                onBackClick = {
                    // 修复5：返回时pop到欢迎页
                    navController.popBackStack(Screen.Welcome.route, false)
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
                onRegisterSuccess = {
                    // 修复6：注册成功后跳转到主页面，并清除整个认证栈
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.Welcome.route) { inclusive = true }
                    }
                },
                onLoginClick = {
                    // 修复7：从注册页点击"立即登录"，应该返回到登录页
                    navController.navigate(Screen.Login.route)
                },
                onBackClick = {
                    // 修复8：返回时pop到欢迎页
                    navController.popBackStack(Screen.Welcome.route, false)

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
            MainContainer(navController = navController)
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
            // 我们将在后续实现设置页面
            ProfileScreen(
                navController = navController,
                userId = "current_user"
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