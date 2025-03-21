package com.example.inspectionapp.ui.screens

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.inspectionapp.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    var isTextExpanded by remember { mutableStateOf(false) }
    val textSize by animateDpAsState(
        targetValue = if (isTextExpanded) 32.dp else 0.dp,
        animationSpec = tween(3000),
        label = "splashTextAnimation"
    )

    LaunchedEffect(Unit) {
        isTextExpanded = true
        delay(3000)
        onTimeout()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        androidx.compose.material3.Text(
            text = "Inspection Form",
            color = Color.Black,
            fontSize = textSize.value.sp
        )
    }
}
