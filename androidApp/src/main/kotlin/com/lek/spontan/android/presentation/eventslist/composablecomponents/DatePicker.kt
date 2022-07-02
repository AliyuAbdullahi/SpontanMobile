package com.lek.spontan.android.presentation.eventslist.composablecomponents

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.lek.spontan.android.R
import com.lek.spontan.events.domain.models.DateData
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun DatePicker(
    isVisible: Boolean,
    onDateSelected: (DateData) -> Unit
) {

    val mContext = LocalContext.current

    val mYear: Int
    val mMonth: Int
    val mDay: Int

    val now = Clock.System.now()
    val datetimeInUtc: LocalDateTime = now.toLocalDateTime(TimeZone.UTC)
    mYear = datetimeInUtc.year
    mMonth = datetimeInUtc.monthNumber
    mDay = datetimeInUtc.dayOfMonth

    val mDatePickerDialog = DatePickerDialog(
        mContext,
        R.style.AlertDialogCustom,
        { _: DatePicker, year: Int, month: Int, dayOfTheMonth: Int ->
            onDateSelected(DateData(dayOfTheMonth, month, year))
        }, mYear, mMonth, mDay
    )

    mDatePickerDialog.setCancelable(false)

    mDatePickerDialog.setOnCancelListener { onDateSelected(DateData.EMPTY) }

    if (isVisible) {
        mDatePickerDialog.show()
    } else {
        mDatePickerDialog.hide()
    }
}