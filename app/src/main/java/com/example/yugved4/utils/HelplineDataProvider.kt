package com.example.yugved4.utils

import com.example.yugved4.models.Helpline

/**
 * Data provider for mental health helplines
 */
object HelplineDataProvider {
    
    fun getHelplines(): List<Helpline> {
        return listOf(
            Helpline(
                name = "AASRA",
                description = "24x7 helpline for emotional support and suicide prevention",
                phoneNumber = "919820466726",
                isEmergency = true
            ),
            Helpline(
                name = "Vandrevala Foundation",
                description = "Mental health support and counseling services",
                phoneNumber = "18602662345",
                isEmergency = false
            ),
            Helpline(
                name = "NIMHANS",
                description = "National Institute of Mental Health and Neurosciences helpline",
                phoneNumber = "08046110007",
                isEmergency = false
            ),
            Helpline(
                name = "iCall",
                description = "Psychosocial helpline by TISS",
                phoneNumber = "9152987821",
                isEmergency = false
            ),
            Helpline(
                name = "Sumaitri",
                description = "Crisis intervention center for emotional support",
                phoneNumber = "01123389090",
                isEmergency = false
            ),
            Helpline(
                name = "Snehi",
                description = "Crisis helpline for emotional support",
                phoneNumber = "01165989090",
                isEmergency = false
            )
        )
    }
}
