// 文件路径: app/src/main/java/com/flowshare/viewmodel/AuthViewModel.kt

package com.flowshare.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flowshare.data.AuthManager
import com.flowshare.data.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * 认证相关的 ViewModel
 * 管理用户登录状态，提供登录、注册、登出等功能
 */
class AuthViewModel : ViewModel() {

    // 使用 AuthManager 作为数据源
    private val authManager = AuthManager()

    // 当前登录用户状态
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    // 登录状态
    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    // 加载状态
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // 错误消息
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        // 初始化时同步 AuthManager 的状态
        viewModelScope.launch {
            authManager.currentUser.collect { user ->
                _currentUser.value = user
                _isLoggedIn.value = user != null
            }
        }
    }

    /**
     * 登录
     * @param username 用户名
     * @param password 密码
     * @return 登录是否成功
     */
    suspend fun login(username: String, password: String): Boolean {
        _isLoading.value = true
        _errorMessage.value = null

        return try {
            val result = authManager.login(username, password)
            result.isSuccess
        } catch (e: Exception) {
            _errorMessage.value = "登录失败: ${e.message}"
            false
        } finally {
            _isLoading.value = false
        }
    }

    /**
     * 注册
     * @param username 用户名
     * @param displayName 显示名称
     * @param password 密码
     * @return 注册是否成功
     */
    suspend fun register(username: String, displayName: String, password: String): Boolean {
        _isLoading.value = true
        _errorMessage.value = null

        return try {
            val result = authManager.register(username, displayName, password)
            result.isSuccess
        } catch (e: Exception) {
            _errorMessage.value = "注册失败: ${e.message}"
            false
        } finally {
            _isLoading.value = false
        }
    }

    /**
     * 登出
     */
    fun logout() {
        authManager.logout()
    }

    /**
     * 清除错误消息
     */
    fun clearError() {
        _errorMessage.value = null
    }

    /**
     * 模拟快速登录（用于调试）
     */
    fun quickLogin(userId: String = "current_user"): Boolean {
        return authManager.loginWithUserId(userId)
    }
}