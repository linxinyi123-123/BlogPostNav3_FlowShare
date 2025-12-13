package com.flowshare.data.model

/**
 * 用户数据模型
 */
data class User(
    val id: String,
    val username: String,
    val displayName: String,
    val avatarUrl: String,
    val bio: String,
    val followers: Int = 0,
    val following: Int = 0
) {
    // 创建一个空的User对象
    companion object {
        fun empty(): User = User(
            id = "",
            username = "",
            displayName = "未登录用户",
            avatarUrl = "",
            bio = "",
            followers = 0,
            following = 0
        )
    }
}