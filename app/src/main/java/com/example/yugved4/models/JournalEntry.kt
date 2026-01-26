package com.example.yugved4.models

import java.util.Date

data class JournalEntry(
    val id: String = "",
    val mood: Int = 0,
    val moodDescription: String = "",
    val content: String = "",
    val date: Date? = null
)
