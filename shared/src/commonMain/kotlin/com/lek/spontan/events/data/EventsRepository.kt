package com.lek.spontan.events.data

import com.lek.spontan.events.domain.EventDomainData
import com.lek.spontan.events.domain.IEventsRepository

class EventsRepository(private val eventDao: IEventsDao) : IEventsRepository {

    override fun getEvents(): List<EventDomainData> = eventDao.getEvents().map { it.toDomainModel() }

    override suspend fun addEvent(event: EventDomainData) = eventDao.saveEvent(event.toDataModel())

    override suspend fun deleteEvent(id: String) =  eventDao.deleteEvent(id)

    override suspend fun deleteAllEvents() = eventDao.deleteAllEvents()
}

fun EventDomainData.toDataModel(): Event =
    Event(
        id = id,
        title = title,
        description = description,
        color = color,
        photo = photo,
        startTime = startTime
    )

fun Event.toDomainModel(): EventDomainData =
    EventDomainData(
        id = id,
        title = title,
        description = description,
        color = color,
        photo = photo,
        startTime = startTime
    )