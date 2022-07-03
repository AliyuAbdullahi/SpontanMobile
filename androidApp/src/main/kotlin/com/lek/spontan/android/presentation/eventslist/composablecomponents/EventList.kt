package com.lek.spontan.android.presentation.eventslist.composablecomponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lek.spontan.android.presentation.shared.composables.LoadingScreen
import com.lek.spontan.android.presentation.shared.composables.VerticalSpace
import com.lek.spontan.android.presentation.shared.helperfunctions.formatDateTime
import com.lek.spontan.android.presentation.shared.theme.SpontanTheme
import com.lek.spontan.android.presentation.shared.theme.SpontanTypography
import com.lek.spontan.events.domain.EventDomainData
import com.lek.spontan.events.domain.models.DateData
import com.lek.spontan.events.domain.models.TimeData

@Composable
fun EventList(
    events: List<EventDomainData>,
    onEventSelected: (EventDomainData) -> Unit
) {
    LazyColumn {
        items(events) { event ->
            EventRow(event = event, onEventSelected)
        }
    }
}

@Composable
fun EventRow(event: EventDomainData, onEventSelected: (EventDomainData) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .clickable { onEventSelected(event) }
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
    ) {

        Column(
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.CenterVertically)
        ) {
            Text(text = event.title, style = SpontanTypography.h6, maxLines = 1)
            VerticalSpace()
            Text(
                text = formatDateTime(event.dateData, event.timeData),
                style = SpontanTypography.body1,
                maxLines = 1
            )
        }
    }
}

private val testEvent = EventDomainData(
    id = "",
    title = "Meeting with the Business Partners at the border of sang without",
    description = "",
    photo = null,
    dateData = DateData(12, 11, 2022),
    timeData = TimeData(30, 3)
)

@Composable
fun EventListScreen(
    events: List<EventDomainData>,
    isEmptyState: Boolean,
    isLoading: Boolean,
    onEventSelected: (EventDomainData) -> Unit,
    onAction: () -> Unit
) {
    SpontanTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.systemBarsPadding()) {
                TopAppBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                ) {
                    Text(
                        text = "Events",
                        style = SpontanTypography.h5,
                        color = Color.White,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                EventList(events = events, onEventSelected = onEventSelected)
            }

            FloatingActionButton(
                onClick = onAction,
                modifier = Modifier
                    .padding(16.dp)
                    .size(60.dp)
                    .align(Alignment.BottomEnd)
            ) {
                Icon(Icons.Filled.Add, "add")
            }

            if (isEmptyState) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    Text(text = "Add an Event to see events here", style = SpontanTypography.h6)
                }
            }

            if (isLoading) {
                LoadingScreen()
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun EventRow_Preview() {
    EventListScreen(events = listOf(), isEmptyState = true, isLoading = false, {}, {})
}