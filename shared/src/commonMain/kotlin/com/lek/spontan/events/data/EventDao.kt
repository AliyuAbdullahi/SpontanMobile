package com.lek.spontan.events.data

import com.lek.spontan.shared.cache.SpontanDatabase

class EventDao(private val db: SpontanDatabase) : IEventsDao {

    override fun getEvents(): List<Event> {
        return db.eventsTableQueries
            .getEvents()
            .executeAsList()
            .map { cacheEvent ->
                Event(
                    id = cacheEvent.id,
                    title = cacheEvent.title,
                    description = cacheEvent.description,
                    photo = cacheEvent.coverIamge,
                    startTime = cacheEvent.startTime
                )
            }
    }

    override suspend fun saveEvent(event: Event) {
        db.eventsTableQueries.createEvent(
            id = event.id,
            title = event.title,
            description = event.description,
            startTime = event.startTime,
            coverIamge = event.photo,
        )
    }

    override suspend fun deleteEvent(id: String) {
        db.eventsTableQueries.deleteEvent(id)
    }

    override suspend fun saveEvents(events: List<Event>) {
        events.forEach { saveEvent(it) }
    }

    override suspend fun deleteAllEvents() {
        db.eventsTableQueries.deleteEvents()
    }
}