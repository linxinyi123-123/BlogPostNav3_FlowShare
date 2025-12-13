package com.flowshare.data.repository

import com.flowshare.data.model.Conversation
import com.flowshare.data.model.Message
import com.flowshare.data.model.Post
import com.flowshare.data.model.User

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
            avatarUrl = "https://i.pravatar.cc/300?img=1",  // 更可靠的图片服务
            bio = "Digital artist | Dreamer | Coffee lover ☕️",
            followers = 2456,
            following = 342
        ),
        User(
            id = "user_002",
            username = "bob_builder",
            displayName = "Bob Builder",
            avatarUrl = "https://i.pravatar.cc/300?img=2",
            bio = "Building the future, one line of code at a time 👨‍💻",
            followers = 1234,
            following = 567
        ),
        User(
            id = "user_003",
            username = "charlie_dev",
            displayName = "Charlie Developer",
            avatarUrl = "https://i.pravatar.cc/300?img=3",
            bio = "Android Developer @TechCorp | Open source enthusiast",
            followers = 7890,
            following = 234
        ),
        User(
            id = "current_user",
            username = "your_username",
            displayName = "You",
            avatarUrl = "https://i.pravatar.cc/300?img=4",
            bio = "Learning Jetpack Compose and Navigation!",
            followers = 150,
            following = 200
        )
    )

    // 修改动态数据部分的imageUrls
    private val mockPosts = listOf(
        Post(
            id = "post_001",
            authorId = "user_001",
            content = "Just finished my morning coffee and feeling inspired! ☕️✨ Working on a new digital art piece.",
            imageUrls = listOf("https://picsum.photos/400/300?random=1"),  // 随机图片
            likes = 245,
            comments = 32,
            timestamp = System.currentTimeMillis() - 2 * 60 * 60 * 1000
        ),
        Post(
            id = "post_002",
            authorId = "user_002",
            content = "Just deployed a major update to our app! 🚀 3 months of hard work finally paying off.",
            imageUrls = listOf(
                "https://picsum.photos/400/300?random=2",
                "https://picsum.photos/400/300?random=3"
            ),
            likes = 567,
            comments = 45,
            timestamp = System.currentTimeMillis() - 5 * 60 * 60 * 1000
        ),
        Post(
            id = "post_003",
            authorId = "user_003",
            content = "Sharing some Jetpack Compose tips I've learned recently! Navigation with Compose is so much smoother.",
            imageUrls = emptyList(),
            likes = 123,
            comments = 18,
            timestamp = System.currentTimeMillis() - 1 * 24 * 60 * 60 * 1000
        ),
        Post(
            id = "post_004",
            authorId = "current_user",
            content = "Working on my FlowShare app! Learning Navigation in Compose.",
            imageUrls = emptyList(),
            likes = 15,
            comments = 3,
            timestamp = System.currentTimeMillis() - 1 * 60 * 60 * 1000
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
            user = getUser("user_001")
        ),
        Conversation(
            id = "conv_002",
            userIds = listOf("current_user", "user_002"),
            lastMessage = "Great work on the Day 1 implementation! Looking forward...",
            lastMessageTime = System.currentTimeMillis() - 2 * 60 * 60 * 1000,
            unreadCount = 0,
            user = getUser("user_002")
        ),
        Conversation(
            id = "conv_003",
            userIds = listOf("current_user", "user_003"),
            lastMessage = "Check out this new Compose library I found!",
            lastMessageTime = System.currentTimeMillis() - 1 * 24 * 60 * 60 * 1000,
            unreadCount = 0,
            user = getUser("user_003")
        )
    )

    // ================== 同步函数（用于Composable） ==================

    /**
     * 获取Feed动态列表（同步版本）
     */
    fun getFeedPosts(): List<Post> = mockPosts

    /**
     * 根据ID获取用户
     */
    fun getUser(userId: String): User? {
        return mockUsers.find { it.id == userId }
    }

    /**
     * 获取所有用户（同步版本）
     */
    fun getAllUsers(): List<User> = mockUsers

    /**
     * 搜索用户（同步版本）
     */
    fun searchUsers(query: String): List<User> {
        if (query.isEmpty()) return mockUsers

        return mockUsers.filter { user ->
            user.username.contains(query, ignoreCase = true) ||
                    user.displayName.contains(query, ignoreCase = true) ||
                    user.bio.contains(query, ignoreCase = true)
        }
    }

    /**
     * 获取对话列表（同步版本）
     */
    fun getConversations(): List<Conversation> {
        return mockConversations.sortedByDescending { it.lastMessageTime }
    }

    /**
     * 获取用户的所有动态（同步版本）
     */
    fun getPostsByUserId(userId: String): List<Post> {
        return mockPosts.filter { it.authorId == userId }
    }

    // ================== 异步函数（用于ViewModel） ==================

    /**
     * 获取Feed动态列表（异步版本）
     */
    suspend fun getFeedPostsAsync(): List<Post> {
        kotlinx.coroutines.delay(300) // 模拟网络延迟
        return mockPosts
    }

    /**
     * 搜索用户（异步版本）
     */
    suspend fun searchUsersAsync(query: String): List<User> {
        kotlinx.coroutines.delay(200)
        if (query.isEmpty()) return mockUsers

        return mockUsers.filter { user ->
            user.username.contains(query, ignoreCase = true) ||
                    user.displayName.contains(query, ignoreCase = true) ||
                    user.bio.contains(query, ignoreCase = true)
        }
    }

    /**
     * 获取对话列表（异步版本）
     */
    suspend fun getConversationsAsync(): List<Conversation> {
        kotlinx.coroutines.delay(400)
        return mockConversations.sortedByDescending { it.lastMessageTime }
    }

    // ================== 新增函数 ==================

    /**
     * 获取当前登录用户
     */
    fun getCurrentUser(): User? {
        return getUser("current_user")
    }

    /**
     * 注册新用户
     */
    fun register(
        username: String,
        displayName: String,
        password: String
    ): Result<User> {
        // 检查用户名是否已存在
        val existingUser = mockUsers.find { it.username == username }
        if (existingUser != null) {
            return Result.failure(Exception("用户名已存在"))
        }

        // 创建新用户
        val newUser = User(
            id = "user_${System.currentTimeMillis()}",
            username = username,
            displayName = displayName,
            avatarUrl = "https://randomuser.me/api/portraits/lego/1.jpg",
            bio = "新用户",
            followers = 0,
            following = 0
        )

        // 在实际应用中，这里会保存到数据库
        return Result.success(newUser)
    }

    /**
     * 模拟空用户（用于错误处理）
     */
    fun getEmptyUser(): User {
        return User(
            id = "",
            username = "",
            displayName = "用户不存在",
            avatarUrl = "",
            bio = "",
            followers = 0,
            following = 0
        )
    }
}