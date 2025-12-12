package com.flowshare.data.repository

import com.flowshare.data.model.Conversation
import com.flowshare.data.model.Message
import com.flowshare.data.model.Post
import com.flowshare.data.model.User
import kotlinx.coroutines.delay
import kotlin.random.Random

/**
 * 模拟数据仓库
 * 提供静态的模拟数据，用于开发和测试
 */
object MockRepository {

    // ================== 用户数据 ==================
    private val mockUsers = listOf(
        User(
            id = "user_001",
            username = "alice_wonder",
            displayName = "Alice Wonderland",
            avatarUrl = "https://randomuser.me/api/portraits/women/1.jpg",
            bio = "Digital artist | Dreamer | Coffee lover ☕️",
            followers = 2456,
            following = 342,
            isVerified = true
        ),
        User(
            id = "user_002",
            username = "bob_builder",
            displayName = "Bob Builder",
            avatarUrl = "https://randomuser.me/api/portraits/men/2.jpg",
            bio = "Building the future, one line of code at a time 👨‍💻",
            followers = 1234,
            following = 567,
            isVerified = false
        ),
        User(
            id = "user_003",
            username = "charlie_dev",
            displayName = "Charlie Developer",
            avatarUrl = "https://randomuser.me/api/portraits/men/3.jpg",
            bio = "Android Developer @TechCorp | Open source enthusiast",
            followers = 7890,
            following = 234,
            isVerified = true
        ),
        User(
            id = "user_004",
            username = "diana_design",
            displayName = "Diana Designer",
            avatarUrl = "https://randomuser.me/api/portraits/women/4.jpg",
            bio = "UI/UX Designer | Minimalist | Mountain lover 🏔️",
            followers = 4567,
            following = 123,
            isVerified = false
        ),
        User(
            id = "user_005",
            username = "flowshare_official",
            displayName = "FlowShare",
            avatarUrl = "https://randomuser.me/api/portraits/lego/5.jpg",
            bio = "Official account of FlowShare. Share your flow with the world!",
            followers = 50000,
            following = 150,
            isVerified = true
        ),
        // 当前用户（假设已登录）
        User(
            id = "current_user",
            username = "your_username",
            displayName = "You",
            avatarUrl = "https://randomuser.me/api/portraits/men/6.jpg",
            bio = "Learning Jetpack Compose and Navigation!",
            followers = 150,
            following = 200,
            isVerified = false
        )
    )

    // ================== 动态数据 ==================
    private val mockPosts = listOf(
        Post(
            id = "post_001",
            authorId = "user_001",
            content = "Just finished my morning coffee and feeling inspired! ☕️✨ Working on a new digital art piece. What's your morning routine?",
            imageUrls = listOf("https://images.unsplash.com/photo-1511919884226-fd3cad34687c"),
            likes = 245,
            comments = 32,
            timestamp = System.currentTimeMillis() - 2 * 60 * 60 * 1000, // 2小时前
            tags = listOf("#art", "#morning", "#inspiration")
        ),
        Post(
            id = "post_002",
            authorId = "user_002",
            content = "Just deployed a major update to our app! 🚀 3 months of hard work finally paying off. So grateful for my amazing team!",
            imageUrls = listOf(
                "https://images.unsplash.com/photo-1551650975-87deedd944c3",
                "https://images.unsplash.com/photo-1545235617-9465d2a55698"
            ),
            likes = 567,
            comments = 45,
            timestamp = System.currentTimeMillis() - 5 * 60 * 60 * 1000, // 5小时前
            isLiked = true,
            tags = listOf("#tech", "#development", "#teamwork")
        ),
        Post(
            id = "post_003",
            authorId = "user_003",
            content = "Sharing some Jetpack Compose tips I've learned recently! Navigation with Compose is so much smoother than the old way. What's your favorite Compose feature?",
            imageUrls = emptyList(),
            likes = 123,
            comments = 18,
            timestamp = System.currentTimeMillis() - 1 * 24 * 60 * 60 * 1000, // 1天前
            tags = listOf("#android", "#compose", "#kotlin", "#programming")
        ),
        Post(
            id = "post_004",
            authorId = "user_004",
            content = "Sunset views from my hike today 🏔️ Nature always puts things in perspective. Remember to take breaks and enjoy the simple things!",
            imageUrls = listOf("https://images.unsplash.com/photo-1506905925346-21bda4d32df4"),
            likes = 890,
            comments = 67,
            timestamp = System.currentTimeMillis() - 2 * 24 * 60 * 60 * 1000, // 2天前
            tags = listOf("#nature", "#hiking", "#photography")
        ),
        Post(
            id = "post_005",
            authorId = "user_005",
            content = "Welcome to FlowShare! We're excited to build a community where developers can share their workflow, tips, and experiences. What would you like to see in this app?",
            imageUrls = listOf("https://images.unsplash.com/photo-1551288049-bebda4e38f71"),
            likes = 1200,
            comments = 89,
            timestamp = System.currentTimeMillis() - 3 * 24 * 60 * 60 * 1000, // 3天前
            isLiked = true,
            tags = listOf("#community", "#welcome", "#feedback")
        )
    )

    // ================== 消息数据 ==================
    private val mockMessages = listOf(
        Message(
            id = "msg_001",
            senderId = "user_001",
            receiverId = "current_user",
            content = "Hey! Loved your recent post about Compose navigation!",
            timestamp = System.currentTimeMillis() - 30 * 60 * 1000, // 30分钟前
            isRead = true
        ),
        Message(
            id = "msg_002",
            senderId = "current_user",
            receiverId = "user_001",
            content = "Thanks Alice! I'm still learning but it's been fun so far.",
            timestamp = System.currentTimeMillis() - 25 * 60 * 1000, // 25分钟前
            isRead = true
        ),
        Message(
            id = "msg_003",
            senderId = "user_001",
            receiverId = "current_user",
            content = "We should collaborate sometime! I'm working on a design project that needs some Android expertise.",
            timestamp = System.currentTimeMillis() - 20 * 60 * 1000, // 20分钟前
            isRead = false
        ),
        Message(
            id = "msg_004",
            senderId = "user_002",
            receiverId = "current_user",
            content = "Great work on the Day 1 implementation! Looking forward to seeing the navigation features.",
            timestamp = System.currentTimeMillis() - 2 * 60 * 60 * 1000, // 2小时前
            isRead = true
        ),
        Message(
            id = "msg_005",
            senderId = "user_004",
            receiverId = "current_user",
            content = "The mountain photo from your hike was amazing! Where was that?",
            timestamp = System.currentTimeMillis() - 5 * 60 * 60 * 1000, // 5小时前
            isRead = false
        )
    )

    // ================== 会话数据 ==================
    private val mockConversations = listOf(
        Conversation(
            id = "conv_001",
            userIds = listOf("current_user", "user_001"),
            lastMessage = "We should collaborate sometime! I'm working on a design project...",
            lastMessageTime = System.currentTimeMillis() - 20 * 60 * 1000,
            unreadCount = 1,
            user = getUserById("user_001")
        ),
        Conversation(
            id = "conv_002",
            userIds = listOf("current_user", "user_002"),
            lastMessage = "Great work on the Day 1 implementation! Looking forward...",
            lastMessageTime = System.currentTimeMillis() - 2 * 60 * 60 * 1000,
            unreadCount = 0,
            user = getUserById("user_002")
        ),
        Conversation(
            id = "conv_003",
            userIds = listOf("current_user", "user_004"),
            lastMessage = "The mountain photo from your hike was amazing! Where was that?",
            lastMessageTime = System.currentTimeMillis() - 5 * 60 * 60 * 1000,
            unreadCount = 1,
            user = getUserById("user_004")
        ),
        Conversation(
            id = "conv_004",
            userIds = listOf("current_user", "user_003"),
            lastMessage = "Check out this new Compose library I found!",
            lastMessageTime = System.currentTimeMillis() - 1 * 24 * 60 * 60 * 1000,
            unreadCount = 0,
            user = getUserById("user_003")
        ),
        Conversation(
            id = "conv_005",
            userIds = listOf("current_user", "user_005"),
            lastMessage = "Welcome to FlowShare! We're excited to have you here.",
            lastMessageTime = System.currentTimeMillis() - 3 * 24 * 60 * 60 * 1000,
            unreadCount = 0,
            user = getUserById("user_005")
        )
    )

    // ================== 公开方法 ==================

    /**
     * 获取Feed动态列表（模拟网络延迟）
     */
    suspend fun getFeedPosts(): List<Post> {
        // 模拟网络延迟
        delay(500)
        return mockPosts
    }

    /**
     * 根据ID获取用户
     */
    fun getUserById(userId: String): User? {
        return mockUsers.find { it.id == userId }
    }

    /**
     * 获取当前登录用户
     */
    fun getCurrentUser(): User? {
        return mockUsers.find { it.id == "current_user" }
    }

    /**
     * 根据用户ID获取该用户发布的动态
     */
    suspend fun getPostsByUserId(userId: String): List<Post> {
        delay(300)
        return mockPosts.filter { it.authorId == userId }
    }

    /**
     * 搜索用户
     */
    suspend fun searchUsers(query: String): List<User> {
        delay(200)
        if (query.isEmpty()) return emptyList()

        return mockUsers.filter {
            it.username.contains(query, ignoreCase = true) ||
                    it.displayName.contains(query, ignoreCase = true) ||
                    it.bio.contains(query, ignoreCase = true)
        }
    }

    /**
     * 获取会话列表
     */
    suspend fun getConversations(): List<Conversation> {
        delay(400)
        return mockConversations.sortedByDescending { it.lastMessageTime }
    }

    /**
     * 获取两个用户之间的消息记录
     */
    suspend fun getMessagesBetweenUsers(userId1: String, userId2: String): List<Message> {
        delay(300)
        return mockMessages.filter {
            (it.senderId == userId1 && it.receiverId == userId2) ||
                    (it.senderId == userId2 && it.receiverId == userId1)
        }.sortedBy { it.timestamp }
    }

    /**
     * 点赞动态
     */
    suspend fun likePost(postId: String): Boolean {
        delay(200)
        // 这里只是模拟，实际应用中会调用API
        return true
    }

    /**
     * 取消点赞
     */
    suspend fun unlikePost(postId: String): Boolean {
        delay(200)
        return true
    }

    /**
     * 发布新动态
     */
    suspend fun createPost(content: String, imageUrls: List<String> = emptyList()): Post {
        delay(800) // 模拟上传时间

        val newPost = Post(
            id = "post_${System.currentTimeMillis()}",
            authorId = "current_user",
            content = content,
            imageUrls = imageUrls,
            likes = 0,
            comments = 0,
            timestamp = System.currentTimeMillis()
        )

        // 注意：在真实应用中，这里会调用API，然后更新本地数据
        return newPost
    }

    /**
     * 发送消息
     */
    suspend fun sendMessage(receiverId: String, content: String): Message {
        delay(300)

        return Message(
            id = "msg_${System.currentTimeMillis()}",
            senderId = "current_user",
            receiverId = receiverId,
            content = content,
            timestamp = System.currentTimeMillis(),
            isRead = false
        )
    }
}