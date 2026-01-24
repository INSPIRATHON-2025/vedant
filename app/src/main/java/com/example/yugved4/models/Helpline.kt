package com.example.yugved4.models

/**
 * Data class for Mental Health Helpline information
 */
data class Helpline(
    val name: String,
    val description: String,
    val phoneNumber: String,
    val isEmergency: Boolean = false
)
