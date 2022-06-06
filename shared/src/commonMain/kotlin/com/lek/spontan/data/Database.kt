package com.lek.spontan.data

import com.lek.spontan.DatabaseDriverFactory
import com.lek.spontan.shared.cache.SpontanDatabase

fun createDatabase(): SpontanDatabase = SpontanDatabase(DatabaseDriverFactory.createDriver())