package com.lek.spontan.events.domain.models

data class DateData(
    val day: Int,
    val month: Int,
    val year: Int
) {
    override fun toString(): String {
        return if (isNull()) {
            "- / - / -"
        } else {
            "$day/$month/$year"
        }
    }

    fun isNull(): Boolean = day == 0 || month == 0 || year == 0

    companion object {
        val EMPTY = DateData(
            day = 0,
            month = 0,
            year = 0
        )
    }
}