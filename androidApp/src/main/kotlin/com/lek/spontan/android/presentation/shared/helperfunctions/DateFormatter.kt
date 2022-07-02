package com.lek.spontan.android.presentation.shared.helperfunctions

import com.lek.spontan.events.domain.models.DateData
import com.lek.spontan.events.domain.models.TimeData
import java.lang.StringBuilder

fun formatDateTime(
    dateData: DateData?,
    timeData: TimeData?
): String =
    when {
        dateData == null && timeData == null -> ""
        dateData == null -> timeData.toString()
        timeData == null -> dateData.toString()
        else -> StringBuilder().apply {
            append(dateData.toString())
            append(", ")
            append(timeData.toString())
        }.toString()
    }
