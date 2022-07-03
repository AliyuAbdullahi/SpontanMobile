package com.lek.spontan.android.presentation.eventslist

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.lek.spontan.android.presentation.eventslist.composablecomponents.EventScreen
import com.lek.spontan.android.presentation.eventslist.eventdetails.EventDetailViewModel
import com.lek.spontan.android.presentation.shared.theme.SpontanTheme
import kotlinx.coroutines.launch

class EventsListActivity : ComponentActivity() {

    private val eventListViewModel = EventsListViewModel()
    private val eventDetailsViewModel = EventDetailViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            eventListViewModel.uiState.collect {
                if (it.error.isNotEmpty()) {
                    Log.e("ERROR", it.error)
                    Toast.makeText(this@EventsListActivity, it.error, Toast.LENGTH_SHORT).show()
                }
            }
        }

        lifecycleScope.launch {
            eventDetailsViewModel.uiState.collect {
                if (it.error.isNotEmpty()) {
                    Log.e("ERROR", it.error)
                    Toast.makeText(this@EventsListActivity, it.error, Toast.LENGTH_SHORT).show()
                }
            }
        }

        setContent {
            SpontanTheme {
                EventScreen(eventListViewModel, eventDetailsViewModel)
            }
        }
    }
}