package com.lek.spontan.android.presentation.shared.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier
        .fillMaxSize()
        .background(color = MaterialTheme.colors.background)
) {
    Box(modifier = modifier) {
        CircularProgressIndicator(modifier = Modifier
            .wrapContentSize()
            .align(Alignment.Center))
    }
}