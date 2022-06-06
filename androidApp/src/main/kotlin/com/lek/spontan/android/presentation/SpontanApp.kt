package com.lek.spontan.android.presentation

import android.app.Application
import com.lek.spontan.DatabaseDriverFactory

class SpontanApp : Application() {
    override fun onCreate() {
        super.onCreate()
        DatabaseDriverFactory.context = this
    }
}