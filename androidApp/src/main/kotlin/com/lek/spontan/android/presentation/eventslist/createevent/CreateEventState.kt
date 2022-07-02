package com.lek.spontan.android.presentation.eventslist.createevent

import com.lek.spontan.events.domain.EventDomainData
import com.lek.spontan.events.domain.models.DateData
import com.lek.spontan.events.domain.models.TimeData
import java.util.UUID

data class CreateEventState(
    val id: String,
    val title: String,
    val description: String,
    val date: String,
    val time: String,
    val picture: String,
    val isLoading: Boolean,
    val error: String,
    val isEventCreated: Boolean,
    val dateData: DateData,
    val timeDateData: TimeData
) {
    val isInEditMode: Boolean
        get() = id.isNotEmpty()

    companion object {
        val EMPTY = CreateEventState(
            id = "",
            title = "",
            description = "",
            date = "",
            time = "",
            picture = "",
            isLoading = false,
            error = "",
            isEventCreated = false,
            dateData = DateData.EMPTY,
            timeDateData = TimeData.EMPTY
        )
    }
}

fun CreateEventState.toDomainData() = EventDomainData(
    id = UUID.randomUUID().toString(),
    title = this.title,
    description = this.description,
    photo = this.picture,
    dateData = this.dateData,
    timeData = this.timeDateData
)