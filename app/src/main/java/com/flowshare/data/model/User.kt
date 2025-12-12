package com.flowshare.data.model

/**
 * 用户数据模型
 * @param id 用户唯一标识
 * @param username 用户名（用于登录和@提及）
 * @param displayName 显示名称
 * @param avatarUrl 头像图片URL
 * @param bio 个人简介
 * @param followers 粉丝数
 * @param following 关注数
 */
data class User(
    val id: String,
    val username: String,
    val displayName: String,
    val avatarUrl: String,
    val bio: String,
    val followers: Int = 0,
    val following: Int = 0,
    val isVerified: Boolean = false,
    val joinDate: String = "2024-01-01"
) {
    companion object {
        // 创建一个空用户实例
        fun empty(): User {
            return User(
                id = "",
                username = "",
                displayName = "",
                avatarUrl = "",
                bio = ""
            )
        }
    }
}