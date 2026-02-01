package com.example.yugved4.models

/**
 * Data class to hold step history data for display in RecyclerView
 * @param date Date in format "yyyy-MM-dd"
 * @param steps Number of steps recorded on that date
 */
data class StepHistory(
    val date: String,
    val steps: Int
)
