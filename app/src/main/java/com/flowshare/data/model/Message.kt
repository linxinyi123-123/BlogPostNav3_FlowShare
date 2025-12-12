package com.flowshare.data.model

/**
 * 消息数据模型
 * @param id 消息唯一标识
 * @param senderId 发送者用户ID
 * @param receiverId 接收者用户ID
 * @param content 消息内容
 * @param timestamp 发送时间戳
 * @param isRead 是否已读
 * @param type 消息类型（文本、图片等）
 */
data class Message(
    val id: String,
    val senderId: String,
    val receiverId: String,
    val content: String,
    val timestamp: Long = System.currentTimeMillis(),
    val isRead: Boolean = false,
    val type: MessageType = MessageType.TEXT
) {
    // 获取格式化的时间显示（聊天界面用）
    fun getChatTime(): String {
        val date = java.util.Date(timestamp)
        val formatter = java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault())
        return formatter.format(date)
    }
}

/**
 * 消息类型枚举
 */
enum class MessageType {
    TEXT,
    IMAGE,
    VIDEO,
    VOICE
}