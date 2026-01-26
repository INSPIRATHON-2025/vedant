package com.example.yugved4.models

/**
 * Data class for saving a user's generated diet plan
 */
data class UserDietPlan(
    val id: String = "",
    val userId: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val targetCalories: Int = 0,
    val dietType: String = "",
    val breakfast: String = "",
    val lunch: String = "",
    val dinner: String = "",
    val snacks: String = ""
)
