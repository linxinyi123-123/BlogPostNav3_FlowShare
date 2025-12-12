package com.flowshare.data.model

import java.util.Date

/**
 * 动态/帖子数据模型
 * @param id 动态唯一标识
 * @param authorId 作者用户ID
 * @param content 动态内容
 * @param imageUrls 图片URL列表（支持多图）
 * @param likes 点赞数
 * @param comments 评论数
 * @param timestamp 发布时间戳
 * @param isLiked 当前用户是否已点赞
 * @param tags 标签列表
 */
data class Post(
    val id: String,
    val authorId: String,
    val content: String,
    val imageUrls: List<String> = emptyList(),
    val likes: Int = 0,
    val comments: Int = 0,
    val timestamp: Long = System.currentTimeMillis(),
    val isLiked: Boolean = false,
    val tags: List<String> = emptyList()
) {
    // 获取格式化的时间显示
    fun getTimeAgo(): String {
        val now = Date().time
        val diff = now - timestamp
        val seconds = diff / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24

        return when {
            seconds < 60 -> "${seconds}秒前"
            minutes < 60 -> "${minutes}分钟前"
            hours < 24 -> "${hours}小时前"
            days < 7 -> "${days}天前"
            else -> {
                val date = Date(timestamp)
                val formattedDate = String.format("%tF", date)
                formattedDate
            }
        }
    }
}