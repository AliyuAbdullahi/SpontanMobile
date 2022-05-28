package com.lek.spontan

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}