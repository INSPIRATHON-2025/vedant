package com.example.yugved4.models

/**
 * Data class representing a yoga asana (pose)
 */
data class YogaAsana(
    val asanaId: String,
    val name: String,
    val sanskritName: String,
    val difficultyLevel: String, // "Beginner", "Intermediate", "Advanced"
    val videoUrl: String,
    val description: String,
    val benefits: List<String>,
    val duration: String, // e.g., "30 seconds", "1 minute"
    val category: String // "Standing", "Seated", "Balancing", "Backbend", "Inversion"
)
