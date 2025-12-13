package com.flowshare.ui.screen.profile

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun ProfileScreen(
    navController: NavController,
    userId: String
) {
    Text(
        text = "用户资料页面 - 用户ID: $userId\nDay 6 实现",
        modifier = Modifier.fillMaxSize(),
        color = Color.Gray,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    )
}