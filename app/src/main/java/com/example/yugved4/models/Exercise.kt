package com.example.yugved4.models

/**
 * Data class representing a gym exercise
 */
data class Exercise(
    val exerciseId: String,
    val name: String,
    val targetMuscles: List<String>,
    val difficultyLevel: String, // "Beginner", "Intermediate", "Advanced"
    val videoUrl: String,
    val description: String,
    val category: String, // "Chest", "Back", "Legs", "Shoulders", "Arms", "Core"
    val duration: String, // e.g., "3 sets x 12 reps"
    val caloriesBurned: Int
)
