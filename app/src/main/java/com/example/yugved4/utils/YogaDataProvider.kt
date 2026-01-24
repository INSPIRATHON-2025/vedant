package com.example.yugved4.utils

import com.example.yugved4.R
import com.example.yugved4.models.Category
import com.example.yugved4.models.YogaAsana

/**
 * Data provider for Yoga categories and asanas
 */
object YogaDataProvider {
    
    /**
     * Get yoga categories for the main grid
     */
    fun getYogaCategories(): List<Category> {
        return listOf(
            Category(1, "Beginner", R.drawable.ic_yoga, 8),
            Category(2, "Flexibility", R.drawable.ic_yoga, 10),
            Category(3, "Strength", R.drawable.ic_yoga, 12)
        )
    }
    
    /**
     * Get all yoga asanas
     */
    fun getAllAsanas(): List<YogaAsana> {
        return getBeginnerAsanas() + getFlexibilityAsanas() + getStrengthAsanas()
    }
    
    /**
     * Get asanas filtered by category
     */
    fun getAsanasByCategory(categoryName: String): List<YogaAsana> {
        return when (categoryName) {
            "Beginner" -> getBeginnerAsanas()
            "Flexibility" -> getFlexibilityAsanas()
            "Strength" -> getStrengthAsanas()
            else -> emptyList()
        }
    }
    
    /**
     * Get asana by ID
     */
    fun getAsanaById(asanaId: String): YogaAsana? {
        return getAllAsanas().find { it.asanaId == asanaId }
    }
    
    private fun getBeginnerAsanas(): List<YogaAsana> {
        return listOf(
            YogaAsana(
                asanaId = "tadasana",
                name = "Mountain Pose",
                sanskritName = "Tadasana",
                difficultyLevel = "Beginner",
                videoUrl = "https://placeholder.com/tadasana",
                description = "A foundational standing pose that improves posture and balance.",
                benefits = listOf(
                    "Improves posture and body awareness",
                    "Strengthens thighs, knees, and ankles",
                    "Firms abdomen and buttocks",
                    "Relieves sciatica"
                ),
                duration = "30 seconds",
                category = "Beginner"
            ),
            YogaAsana(
                asanaId = "balasana",
                name = "Child's Pose",
                sanskritName = "Balasana",
                difficultyLevel = "Beginner",
                videoUrl = "https://placeholder.com/balasana",
                description = "A resting pose that gently stretches the back and promotes relaxation.",
                benefits = listOf(
                    "Gently stretches the hips, thighs, and ankles",
                    "Calms the brain and relieves stress",
                    "Relieves back and neck pain when done with head supported"
                ),
                duration = "1-3 minutes",
                category = "Beginner"
            ),
            YogaAsana(
                asanaId = "marjaryasana",
                name = "Cat Pose",
                sanskritName = "Marjaryasana",
                difficultyLevel = "Beginner",
                videoUrl = "https://placeholder.com/marjaryasana",
                description = "A gentle flow between cat and cow poses that warms the body.",
                benefits = listOf(
                    "Stretches the back and neck",
                    "Massages the spine and belly organs",
                    "Improves coordination and mental/emotional balance"
                ),
                duration = "1 minute",
                category = "Beginner"
            ),
            YogaAsana(
                asanaId = "adho_mukha_svanasana",
                name = "Downward-Facing Dog",
                sanskritName = "Adho Mukha Svanasana",
                difficultyLevel = "Beginner",
                videoUrl = "https://placeholder.com/downward-dog",
                description = "An energizing pose that stretches the entire body.",
                benefits = listOf(
                    "Calms the brain and relieves stress",
                    "Energizes the body",
                    "Stretches shoulders, hamstrings, calves, and hands",
                    "Strengthens arms and legs"
                ),
                duration = "1-3 minutes",
                category = "Beginner"
            ),
            YogaAsana(
                asanaId = "vrikshasana",
                name = "Tree Pose",
                sanskritName = "Vrikshasana",
                difficultyLevel = "Beginner",
                videoUrl = "https://placeholder.com/vrikshasana",
                description = "A balancing pose that improves focus and stability.",
                benefits = listOf(
                    "Improves balance and stability",
                    "Strengthens thighs, calves, ankles, and spine",
                    "Stretches the groins, inner thighs, chest, and shoulders",
                    "Improves concentration"
                ),
                duration = "30-60 seconds per side",
                category = "Beginner"
            ),
            YogaAsana(
                asanaId = "sukhasana",
                name = "Easy Pose",
                sanskritName = "Sukhasana",
                difficultyLevel = "Beginner",
                videoUrl = "https://placeholder.com/sukhasana",
                description = "A comfortable seated meditation pose.",
                benefits = listOf(
                    "Calms the mind",
                    "Strengthens the back",
                    "Stretches the knees and ankles",
                    "Opens the hips"
                ),
                duration = "5-10 minutes",
                category = "Beginner"
            ),
            YogaAsana(
                asanaId = "savasana",
                name = "Corpse Pose",
                sanskritName = "Savasana",
                difficultyLevel = "Beginner",
                videoUrl = "https://placeholder.com/savasana",
                description = "The final relaxation pose practiced at the end of yoga sessions.",
                benefits = listOf(
                    "Calms the brain and relieves stress",
                    "Relaxes the body",
                    "Reduces blood pressure and fatigue",
                    "Promotes deep rest"
                ),
                duration = "5-10 minutes",
                category = "Beginner"
            ),
            YogaAsana(
                asanaId = "bhujangasana",
                name = "Cobra Pose",
                sanskritName = "Bhujangasana",
                difficultyLevel = "Beginner",
                videoUrl = "https://placeholder.com/bhujangasana",
                description = "A gentle backbend that opens the chest and strengthens the spine.",
                benefits = listOf(
                    "Strengthens the spine",
                    "Stretches chest, shoulders, and abdomen",
                    "Firms the buttocks",
                    "Relieves stress and fatigue"
                ),
                duration = "15-30 seconds",
                category = "Beginner"
            )
        )
    }
    
    private fun getFlexibilityAsanas(): List<YogaAsana> {
        return listOf(
            YogaAsana(
                asanaId = "uttanasana",
                name = "Standing Forward Bend",
                sanskritName = "Uttanasana",
                difficultyLevel = "Intermediate",
                videoUrl = "https://placeholder.com/uttanasana",
                description = "A forward fold that stretches the entire back body.",
                benefits = listOf(
                    "Stretches the hamstrings, calves, and hips",
                    "Strengthens thighs and knees",
                    "Calms the brain and relieves stress",
                    "Stimulates liver and kidneys"
                ),
                duration = "1-3 minutes",
                category = "Flexibility"
            ),
            YogaAsana(
                asanaId = "paschimottanasana",
                name = "Seated Forward Bend",
                sanskritName = "Paschimottanasana",
                difficultyLevel = "Intermediate",
                videoUrl = "https://placeholder.com/paschimottanasana",
                description = "A deep forward fold that stretches the entire back of the body.",
                benefits = listOf(
                    "Stretches the spine, shoulders, and hamstrings",
                    "Calms the brain and relieves stress",
                    "Stimulates digestive organs",
                    "Reduces anxiety and fatigue"
                ),
                duration = "1-3 minutes",
                category = "Flexibility"
            ),
            YogaAsana(
                asanaId = "kapotasana",
                name = "Pigeon Pose",
                sanskritName = "Kapotasana",
                difficultyLevel = "Intermediate",
                videoUrl = "https://placeholder.com/kapotasana",
                description = "A deep hip opener that releases tension.",
                benefits = listOf(
                    "Opens the hips and chest",
                    "Stretches the hip flexors and rotators",
                    "Relieves lower back tension",
                    "Stimulates internal organs"
                ),
                duration = "1-2 minutes per side",
                category = "Flexibility"
            ),
            YogaAsana(
                asanaId = "matsyendrasana",
                name = "Seated Spinal Twist",
                sanskritName = "Ardha Matsyendrasana",
                difficultyLevel = "Intermediate",
                videoUrl = "https://placeholder.com/matsyendrasana",
                description = "A seated twist that improves spinal mobility.",
                benefits = listOf(
                    "Stretches the shoulders, hips, and neck",
                    "Stimulates digestive fire",
                    "Relieves menstrual discomfort and fatigue",
                    "Improves spinal flexibility"
                ),
                duration = "30-60 seconds per side",
                category = "Flexibility"
            ),
            YogaAsana(
                asanaId = "gomukhasana",
                name = "Cow Face Pose",
                sanskritName = "Gomukhasana",
                difficultyLevel = "Intermediate",
                videoUrl = "https://placeholder.com/gomukhasana",
                description = "A pose that stretches multiple parts of the body simultaneously.",
                benefits = listOf(
                    "Stretches the ankles, hips, thighs, shoulders, and triceps",
                    "Opens the chest",
                    "Improves posture",
                    "Relieves chronic knee pain"
                ),
                duration = "1 minute per side",
                category = "Flexibility"
            ),
            YogaAsana(
                asanaId = "anjaneyasana",
                name = "Low Lunge",
                sanskritName = "Anjaneyasana",
                difficultyLevel = "Intermediate",
                videoUrl = "https://placeholder.com/anjaneyasana",
                description = "A lunging pose that stretches the hip flexors.",
                benefits = listOf(
                    "Stretches the hip flexors and quadriceps",
                    "Opens the chest and shoulders",
                    "Strengthens the legs",
                    "Improves balance and concentration"
                ),
                duration = "30-60 seconds per side",
                category = "Flexibility"
            ),
            YogaAsana(
                asanaId = "ustrasana",
                name = "Camel Pose",
                sanskritName = "Ustrasana",
                difficultyLevel = "Intermediate",
                videoUrl = "https://placeholder.com/ustrasana",
                description = "A deep backbend that opens the front body.",
                benefits = listOf(
                    "Stretches the entire front of the body",
                    "Improves posture",
                    "Strengthens back muscles",
                    "Stimulates organs of the abdomen and neck"
                ),
                duration = "30-60 seconds",
                category = "Flexibility"
            ),
            YogaAsana(
                asanaId = "setu_bandhasana",
                name = "Bridge Pose",
                sanskritName = "Setu Bandhasana",
                difficultyLevel = "Intermediate",
                videoUrl = "https://placeholder.com/setu-bandhasana",
                description = "A gentle backbend that opens the chest.",
                benefits = listOf(
                    "Stretches the chest, neck, and spine",
                    "Strengthens back and hamstrings",
                    "Calms the brain and reduces stress",
                    "Stimulates abdominal organs and thyroid"
                ),
                duration = "30-60 seconds",
                category = "Flexibility"
            ),
            YogaAsana(
                asanaId = "baddha_konasana",
                name = "Butterfly Pose",
                sanskritName = "Baddha Konasana",
                difficultyLevel = "Beginner",
                videoUrl = "https://placeholder.com/baddha-konasana",
                description = "A seated pose that opens the hips and groin.",
                benefits = listOf(
                    "Stretches the inner thighs, groins, and knees",
                    "Stimulates abdominal organs and improves circulation",
                    "Relieves mild depression and anxiety",
                    "Helps relieve menstrual discomfort"
                ),
                duration = "1-5 minutes",
                category = "Flexibility"
            ),
            YogaAsana(
                asanaId = "supta_baddha_konasana",
                name = "Reclining Butterfly Pose",
                sanskritName = "Supta Baddha Konasana",
                difficultyLevel = "Beginner",
                videoUrl = "https://placeholder.com/supta-baddha-konasana",
                description = "A restorative pose that opens the hips while lying down.",
                benefits = listOf(
                    "Stretches inner thighs and groins",
                    "Relieves symptoms of stress and mild depression",
                    "Improves circulation",
                    "Stimulates heart and reproductive organs"
                ),
                duration = "3-10 minutes",
                category = "Flexibility"
            )
        )
    }
    
    private fun getStrengthAsanas(): List<YogaAsana> {
        return listOf(
            YogaAsana(
                asanaId = "phalakasana",
                name = "Plank Pose",
                sanskritName = "Phalakasana",
                difficultyLevel = "Intermediate",
                videoUrl = "https://placeholder.com/phalakasana",
                description = "A foundational pose that builds core strength.",
                benefits = listOf(
                    "Strengthens arms, wrists, and spine",
                    "Tones the abdomen",
                    "Builds stamina and endurance",
                    "Improves posture"
                ),
                duration = "30-60 seconds",
                category = "Strength"
            ),
            YogaAsana(
                asanaId = "virabhadrasana_1",
                name = "Warrior I",
                sanskritName = "Virabhadrasana I",
                difficultyLevel = "Beginner",
                videoUrl = "https://placeholder.com/warrior-1",
                description = "A standing pose that builds strength and stamina.",
                benefits = listOf(
                    "Strengthens shoulders, arms, legs, and back",
                    "Opens the chest, lungs, and hips",
                    "Improves focus, balance, and stability",
                    "Stretches the hip flexors"
                ),
                duration = "30-60 seconds per side",
                category = "Strength"
            ),
            YogaAsana(
                asanaId = "virabhadrasana_2",
                name = "Warrior II",
                sanskritName = "Virabhadrasana II",
                difficultyLevel = "Beginner",
                videoUrl = "https://placeholder.com/warrior-2",
                description = "A powerful standing pose that builds endurance.",
                benefits = listOf(
                    "Strengthens legs and ankles",
                    "Stretches groins, chest, and shoulders",
                    "Stimulates abdominal organs",
                    "Increases stamina"
                ),
                duration = "30-60 seconds per side",
                category = "Strength"
            ),
            YogaAsana(
                asanaId = "virabhadrasana_3",
                name = "Warrior III",
                sanskritName = "Virabhadrasana III",
                difficultyLevel = "Advanced",
                videoUrl = "https://placeholder.com/warrior-3",
                description = "A balancing pose that requires and builds full-body strength.",
                benefits = listOf(
                    "Strengthens ankles, legs, shoulders, and back",
                    "Tones the abdomen",
                    "Improves balance and posture",
                    "Builds concentration and coordination"
                ),
                duration = "30-60 seconds per side",
                category = "Strength"
            ),
            YogaAsana(
                asanaId = "utkatasana",
                name = "Chair Pose",
                sanskritName = "Utkatasana",
                difficultyLevel = "Intermediate",
                videoUrl = "https://placeholder.com/utkatasana",
                description = "A powerful standing pose that strengthens the legs.",
                benefits = listOf(
                    "Strengthens ankles, thighs, calves, and spine",
                    "Stretches shoulders and chest",
                    "Stimulates abdominal organs and diaphragm",
                    "Reduces flat feet"
                ),
                duration = "30-60 seconds",
                category = "Strength"
            ),
            YogaAsana(
                asanaId = "navasana",
                name = "Boat Pose",
                sanskritName = "Navasana",
                difficultyLevel = "Intermediate",
                videoUrl = "https://placeholder.com/navasana",
                description = "A core-strengthening pose that builds abdominal muscles.",
                benefits = listOf(
                    "Strengthens the abdomen, hip flexors, and spine",
                    "Stimulates kidneys and intestines",
                    "Relieves stress",
                    "Improves digestion"
                ),
                duration = "10-20 seconds",
                category = "Strength"
            ),
            YogaAsana(
                asanaId = "chaturanga_dandasana",
                name = "Four-Limbed Staff Pose",
                sanskritName = "Chaturanga Dandasana",
                difficultyLevel = "Advanced",
                videoUrl = "https://placeholder.com/chaturanga",
                description = "A challenging arm balance that builds upper body strength.",
                benefits = listOf(
                    "Strengthens arms, wrists, and core",
                    "Tones the abdomen",
                    "Prepares the body for more challenging arm balances",
                    "Improves posture"
                ),
                duration = "10-30 seconds",
                category = "Strength"
            ),
            YogaAsana(
                asanaId = "vasisthasana",
                name = "Side Plank",
                sanskritName = "Vasisthasana",
                difficultyLevel = "Intermediate",
                videoUrl = "https://placeholder.com/vasisthasana",
                description = "A side-balancing pose that strengthens the arms and core.",
                benefits = listOf(
                    "Strengthens arms, wrists, and legs",
                    "Tones the abdomen",
                    "Improves balance and concentration",
                    "Stretches the backs of the legs"
                ),
                duration = "15-30 seconds per side",
                category = "Strength"
            ),
            YogaAsana(
                asanaId = "bakasana",
                name = "Crow Pose",
                sanskritName = "Bakasana",
                difficultyLevel = "Advanced",
                videoUrl = "https://placeholder.com/bakasana",
                description = "An arm balance that requires strength and focus.",
                benefits = listOf(
                    "Strengthens arms, wrists, and core",
                    "Stretches the upper back",
                    "Improves balance and concentration",
                    "Builds confidence"
                ),
                duration = "20-60 seconds",
                category = "Strength"
            ),
            YogaAsana(
                asanaId = "purvottanasana",
                name = "Upward Plank",
                sanskritName = "Purvottanasana",
                difficultyLevel = "Intermediate",
                videoUrl = "https://placeholder.com/purvottanasana",
                description = "A reverse plank that strengthens the back body.",
                benefits = listOf(
                    "Strengthens arms, wrists, and legs",
                    "Stretches shoulders, chest, and front ankles",
                    "Improves posture",
                    "Counteracts forward bends"
                ),
                duration = "30 seconds",
                category = "Strength"
            ),
            YogaAsana(
                asanaId = "ardha_chandrasana",
                name = "Half Moon Pose",
                sanskritName = "Ardha Chandrasana",
                difficultyLevel = "Intermediate",
                videoUrl = "https://placeholder.com/ardha-chandrasana",
                description = "A standing balance that builds strength and coordination.",
                benefits = listOf(
                    "Strengthens abdomen, ankles, thighs, and spine",
                    "Stretches groins, hamstrings, and calves",
                    "Improves coordination and balance",
                    "Relieves stress"
                ),
                duration = "30-60 seconds per side",
                category = "Strength"
            ),
            YogaAsana(
                asanaId = "dhanurasana",
                name = "Bow Pose",
                sanskritName = "Dhanurasana",
                difficultyLevel = "Intermediate",
                videoUrl = "https://placeholder.com/dhanurasana",
                description = "A backbend that strengthens the entire back body.",
                benefits = listOf(
                    "Strengthens back muscles",
                    "Stretches entire front of the body",
                    "Improves posture",
                    "Stimulates organs of the abdomen and neck"
                ),
                duration = "20-30 seconds",
                category = "Strength"
            )
        )
    }
}
