package com.flowshare.ui.screen.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun MainContainer(navController: NavController) {
    Text(
        text = "主页面容器 - Day 4 实现",
        modifier = Modifier.fillMaxSize(),
        color = Color.Gray,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold
    )
}