package com.example.yugved4.models

/**
 * Data class representing a category in the gym/yoga section
 */
data class Category(
    val id: Int,
    val name: String,
    val iconResId: Int,
    val exerciseCount: Int
)
