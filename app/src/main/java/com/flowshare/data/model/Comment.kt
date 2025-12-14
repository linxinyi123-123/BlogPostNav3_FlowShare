// 文件路径: app/src/main/java/com/flowshare/data/model/Comment.kt

package com.flowshare.data.model

/**
 * 评论数据模型
 * @param id 评论唯一标识
 * @param postId 所属动态ID
 * @param authorId 评论作者ID
 * @param content 评论内容
 * @param likes 点赞数
 * @param timestamp 评论时间戳
 * @param isLiked 当前用户是否已点赞
 * @param replies 回复列表（支持嵌套评论）
 */
data class Comment(
    val id: String,
    val postId: String,
    val authorId: String,
    val content: String,
    val likes: Int = 0,
    val timestamp: Long = System.currentTimeMillis(),
    val isLiked: Boolean = false,
    val replies: List<Comment> = emptyList()
) {
    // 获取格式化的时间显示
    fun getTimeAgo(): String {
        val now = System.currentTimeMillis()
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
                val formattedDate = java.text.SimpleDateFormat("MM/dd", java.util.Locale.getDefault())
                    .format(java.util.Date(timestamp))
                formattedDate
            }
        }
    }
}