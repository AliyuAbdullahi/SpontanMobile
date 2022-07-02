package com.lek.spontan.events.domain.models

const val NEGATIVE_TIME = -1

data class TimeData(
    val minute: Int,
    val hour: Int
) {
    override fun toString(): String {
        val minuteText = if (minute == NEGATIVE_TIME || minute == 0) "-" else minute.toString()
        val hourText = if (hour == NEGATIVE_TIME || minute == 0) "-" else hour.toString()
        return "$hourText : $minuteText"
    }

    val isNull: Boolean = hour == NEGATIVE_TIME

    companion object {
        val EMPTY = TimeData(
            minute = NEGATIVE_TIME,
            hour = NEGATIVE_TIME
        )
    }
}