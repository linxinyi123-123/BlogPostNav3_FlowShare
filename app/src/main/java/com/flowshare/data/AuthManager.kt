package com.flowshare.data

import com.flowshare.data.model.User
import com.flowshare.data.repository.MockRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * 认证管理器
 * 管理用户登录状态
 */
class AuthManager {

    // 当前登录用户状态流
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    // 登录状态
    val isLoggedIn: Boolean
        get() = _currentUser.value != null

    init {
        // 初始化时检查是否有已登录用户（这里模拟自动登录）
        // 在实际应用中，这里会检查本地存储的token
        val savedUser = MockRepository.getCurrentUser()
        if (savedUser != null) {
            _currentUser.value = savedUser
        }
    }

    /**
     * 登录
     * @param username 用户名
     * @param password 密码（这里只是模拟，实际应用需要加密处理）
     */
    suspend fun login(username: String, password: String): Result<User> {
        // 模拟网络请求延迟
        kotlinx.coroutines.delay(1000)

        // 模拟登录验证（实际应用中这里会调用API）
        return if (username.isNotBlank() && password.isNotBlank()) {
            // 登录成功，获取用户信息
            val user = MockRepository.getCurrentUser() ?: User.empty()
            _currentUser.value = user
            Result.success(user)
        } else {
            Result.failure(Exception("用户名或密码不能为空"))
        }
    }

    /**
     * 使用用户ID直接登录（用于演示）
     */
    fun loginWithUserId(userId: String): Boolean {
        val user = MockRepository.getUserById(userId)
        if (user != null) {
            _currentUser.value = user
            return true
        }
        return false
    }

    /**
     * 注册
     */
    suspend fun register(
        username: String,
        displayName: String,
        password: String,
        bio: String = ""
    ): Result<User> {
        // 模拟网络请求延迟
        kotlinx.coroutines.delay(1500)

        // 模拟注册逻辑（实际应用中这里会调用API）
        return if (username.isNotBlank() && password.isNotBlank()) {
            val newUser = User(
                id = "user_${System.currentTimeMillis()}",
                username = username,
                displayName = displayName,
                avatarUrl = "https://randomuser.me/api/portraits/lego/${(1..10).random()}.jpg",
                bio = bio
            )

            _currentUser.value = newUser
            Result.success(newUser)
        } else {
            Result.failure(Exception("注册信息不完整"))
        }
    }

    /**
     * 登出
     */
    fun logout() {
        _currentUser.value = null
        // 在实际应用中，这里还需要清除本地存储的token等
    }

    /**
     * 更新用户信息
     */
    suspend fun updateUserProfile(
        displayName: String? = null,
        bio: String? = null,
        avatarUrl: String? = null
    ): Result<User> {
        // 模拟网络请求延迟
        kotlinx.coroutines.delay(800)

        val current = _currentUser.value ?: return Result.failure(Exception("用户未登录"))

        val updatedUser = current.copy(
            displayName = displayName ?: current.displayName,
            bio = bio ?: current.bio,
            avatarUrl = avatarUrl ?: current.avatarUrl
        )

        _currentUser.value = updatedUser
        return Result.success(updatedUser)
    }
}