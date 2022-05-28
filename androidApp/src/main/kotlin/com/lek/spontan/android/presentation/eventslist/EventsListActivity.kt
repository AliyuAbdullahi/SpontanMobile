package com.lek.spontan.android.presentation.eventslist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.lek.spontan.android.presentation.shared.theme.SpontanTheme

class EventsListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpontanTheme {

            }
        }
    }
}