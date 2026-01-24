package com.example.yugved4.models

/**
 * Data class for Mental Health Resources (Tips and Videos)
 */
data class MentalHealthResource(
    val title: String,
    val description: String,
    val type: ResourceType,
    val category: String
)

/**
 * Types of mental health resources
 */
enum class ResourceType {
    VIDEO,
    TIP
}
