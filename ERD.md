```mermaid
erDiagram
    %% User Profile (Central Entity)
    USER_PROFILE {
        int id PK
        string name
        int target_calories
        real current_weight
        int age
        string gender
        string activity_level
        string diet_preference
        real height
        string firebase_uid
    }

    %% Doctors
    DOCTORS {
        int id PK
        string name
        string specialty
        string phone
        string email
    }

    %% Helplines
    HELPLINES {
        int id PK
        string name
        string number
        string description
    }

    %% Fitness - Gym
    MUSCLE_GROUPS {
        int muscle_id PK
        string muscle_name
        int muscle_image_resource
    }

    GYM_EXERCISES {
        int exercise_id PK
        int muscle_id FK
        string exercise_name
        string exercise_description
        int thumbnail_resource
        string video_url
    }

    %% Fitness - Yoga
    YOGA_ASANAS {
        int asana_id PK
        string asana_name
        string sanskrit_name
        string description
        string benefits
        string duration
        string difficulty_level
        string category
        int thumbnail_resource
        string video_url
    }

    %% Mental Health
    MENTAL_HEALTH_CONTENT {
        int id PK
        string title
        string type
        string category
        string content_data
    }

    PEACE_QUOTES {
        int id PK
        string quote_text
    }

    %% Nutrition
    MEAL_PLANS {
        int plan_id PK
        int min_calories
        int max_calories
        string diet_type
        string breakfast
        string lunch
        string dinner
        string snacks
    }

    %% Tracking
    DAILY_STEPS {
        string date PK
        int step_count
    }

    DAILY_MOODS {
        string date PK
        int mood_score
    }

    %% Settings
    APP_SETTINGS {
        string key PK
        int value
    }

    %% --- RELATIONSHIPS WITH CARDINALITY ---

    %% 1. Muscle Groups to Gym Exercises: One-to-Many (1:N)
    %% One Muscle Group (e.g. Chest) has Many Exercises (e.g. Pushup, Bench Press)
    MUSCLE_GROUPS ||--o{ GYM_EXERCISES : "One-to-Many (1 Muscle Group has many Exercises)"

    %% 2. User Profile to Daily Steps: One-to-Many (1:N)
    %% One User has Many Daily Step logs (one per date)
    USER_PROFILE ||--o{ DAILY_STEPS : "One-to-Many (1 User has many Step Logs)"

    %% 3. User Profile to Daily Moods: One-to-Many (1:N)
    %% One User has Many Daily Mood logs
    USER_PROFILE ||--o{ DAILY_MOODS : "One-to-Many (1 User has many Mood Logs)"

    %% 4. User Profile to App Settings: One-to-One (1:1) (Conceptually)
    %% One User (Device) has One Set of Configured Settings (though settings are rows)
    %% In this schema, it's actually User (1) to Settings Rows (N), so 1:N
    USER_PROFILE ||--o{ APP_SETTINGS : "One-to-Many (1 User has many Settings)"
```

## Legend
- **PK**: Primary Key
- **FK**: Foreign Key
- **||--o{**: **One-to-Many** Relationship (The double bar `||` is the "One" side, the crow's foot `o{` is the "Many" side)
- **||--||**: **One-to-One** Relationship
