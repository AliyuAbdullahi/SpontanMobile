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
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lek.spontan.android.presentation.eventslist.eventdetails.EventDetailViewModel
import com.lek.spontan.android.presentation.eventslist.eventdetails.model.EventDetailEvent
import com.lek.spontan.android.presentation.eventslist.eventdetails.model.EventDetailState
import com.lek.spontan.android.presentation.shared.composables.LoadingScreen
import com.lek.spontan.android.presentation.shared.composables.VerticalSpace
import com.lek.spontan.android.presentation.shared.theme.SpontanTheme
import com.lek.spontan.android.presentation.shared.theme.SpontanTypography
import com.lek.spontan.events.domain.models.DateData
import com.lek.spontan.events.domain.models.TimeData

@Composable
fun EventDetailScreen(viewModel: EventDetailViewModel, onBackClicked: () -> Unit) {
    val uiState by viewModel.uiState.collectAsState()
    EventDetail(
        uiState,
        onTitleChanged = { viewModel.onEvent(EventDetailEvent.OnTitleChanged(it)) },
        onDescriptionChanged = { viewModel.onEvent(EventDetailEvent.OnDescriptionChanged(it)) },
        onDateSelected = { viewModel.onEvent(EventDetailEvent.OnDateDataChanged(it)) },
        onTimeSelected = { viewModel.onEvent(EventDetailEvent.OnTimeDataChanged(it)) },
        onCreateEventClicked = { viewModel.onEvent(EventDetailEvent.OnAddEventClicked) },
        onOpenDatePickerClicked = { viewModel.onEvent(EventDetailEvent.ShowDatePicker(it)) },
        onOpenTimePickerClicked = { viewModel.onEvent(EventDetailEvent.ShowTimePicker(it)) },
        onBackClicked = onBackClicked
    )
}

@Composable
fun EventDetail(
    eventDomainData: EventDetailState,
    onTitleChanged: (String) -> Unit = {},
    onDescriptionChanged: (String) -> Unit = {},
    onDateSelected: (DateData) -> Unit = {},
    onTimeSelected: (TimeData) -> Unit = {},
    onCreateEventClicked: () -> Unit = {},
    onOpenDatePickerClicked: (Boolean) -> Unit = {},
    onOpenTimePickerClicked: (Boolean) -> Unit = {},
    onBackClicked: () -> Unit = {}
) {

    if (eventDomainData.isEventAdded) {
        onBackClicked()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.systemBarsPadding()) {
            TopAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Row {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "back",
                        modifier = Modifier
                            .clickable { onBackClicked() }
                            .align(Alignment.CenterVertically)
                            .padding(8.dp),
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "Create Event",
                        style = SpontanTypography.h5,
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .align(Alignment.CenterVertically),
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize()
                    .padding(16.dp)
                    .align(Alignment.Start)
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    value = eventDomainData.title,
                    label = { Text(text = "Event Title") },
                    onValueChange = onTitleChanged
                )

                VerticalSpace()

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    textStyle = SpontanTypography.body1,
                    value = eventDomainData.description,
                    label = { Text(text = "Description") },
                    onValueChange = onDescriptionChanged
                )

                VerticalSpace(24.dp)

                Text(
                    text = "Event time - ${eventDomainData.timeData}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onOpenTimePickerClicked(true)
                        }
                )

                VerticalSpace(24.dp)

                Text(
                    text = "Date - ${eventDomainData.dateData.toString()}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onOpenDatePickerClicked(true) }
                )

                VerticalSpace(24.dp)

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    enabled = eventDomainData.isValid,
                    onClick = onCreateEventClicked
                ) {
                    Text(text = "Create Event")
                }
            }
        }

        DatePicker(isVisible = eventDomainData.isDateVisible, onDateSelected = onDateSelected)
        TimePicker(isVisible = eventDomainData.isTimeVisible, onTimeSelected = onTimeSelected)

        if (eventDomainData.isLoading) {
            LoadingScreen()
        }
    }
}

private val testEvent = EventDetailState(
    title = "Meeting with the Business Partners at the border of sang without",
    description = "Meeting with the Business Partners at the border of sang without",
    dateData = DateData(day = 2, month = 2, 2023),
    timeData = TimeData(minute = 30, hour = 13),
    isLoading = false,
    isEventAdded = false,
    isDateVisible = false,
    isTimeVisible = false,
    error = ""
)

@Preview(showSystemUi = true)
@Composable
fun EventDetail_Preview() {
    SpontanTheme {
        EventDetail(eventDomainData = testEvent)
    }
}