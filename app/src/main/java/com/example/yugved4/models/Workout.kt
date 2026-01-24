package com.example.yugved4.models

/**
 * Data class representing a workout item
 */
data class Workout(
    val id: Int,
    val title: String,
    val duration: String,
    val imageResId: Int,
    val category: String
)
