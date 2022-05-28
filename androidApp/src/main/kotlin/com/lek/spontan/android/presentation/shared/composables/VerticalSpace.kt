package com.lek.spontan.android.presentation.shared.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.lek.spontan.android.presentation.shared.theme.Spacing

@Composable
fun VerticalSpace(margin: Dp = Spacing.standardVerticalMargin) {
    Spacer(modifier = Modifier.height(margin))
}