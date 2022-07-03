package com.lek.spontan.android.presentation.eventslist.composablecomponents

import android.app.TimePickerDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.lek.spontan.android.R
import com.lek.spontan.events.domain.models.TimeData
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun TimePicker(
    isVisible: Boolean,
    onTimeSelected: (TimeData) -> Unit
) {
    val context = LocalContext.current

    val minute: Int
    val hour: Int

    val now = Clock.System.now()
    val datetimeInUtc: LocalDateTime = now.toLocalDateTime(TimeZone.UTC)
    minute = datetimeInUtc.minute
    hour = datetimeInUtc.hour

    val timePickerDialog = TimePickerDialog(
        context,
        R.style.AlertDialogCustom,
        { _, theHour, theMinute ->
            onTimeSelected(TimeData(minute = theMinute, hour = theHour))
        },
        hour, minute, true
    )

    timePickerDialog.setCancelable(false)

    timePickerDialog.setOnCancelListener { onTimeSelected(TimeData.EMPTY) }

    if (isVisible) {
        timePickerDialog.show()
    } else {
        timePickerDialog.hide()
    }
}