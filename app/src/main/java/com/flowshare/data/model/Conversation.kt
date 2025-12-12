package com.flowshare.data.model

/**
 * 会话/对话数据模型
 * @param id 会话唯一标识
 * @param userIds 参与用户ID列表
 * @param lastMessage 最后一条消息内容
 * @param lastMessageTime 最后消息时间
 * @param unreadCount 未读消息数
 * @param user 对方用户信息（用于显示）
 */
data class Conversation(
    val id: String,
    val userIds: List<String>,
    val lastMessage: String,
    val lastMessageTime: Long = System.currentTimeMillis(),
    val unreadCount: Int = 0,
    val user: User? = null
)