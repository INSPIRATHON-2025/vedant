# Complete Gym Exercise Database - SQL Reference

## Database Schema Overview

### Muscle Groups Table
```sql
CREATE TABLE muscle_groups (
    muscle_id INTEGER PRIMARY KEY AUTOINCREMENT,
    muscle_name TEXT NOT NULL,
    muscle_image_resource INTEGER NOT NULL
)
```

### Gym Exercises Table
```sql
CREATE TABLE gym_exercises (
    exercise_id INTEGER PRIMARY KEY AUTOINCREMENT,
    muscle_id INTEGER NOT NULL,
    exercise_name TEXT NOT NULL,
    exercise_description TEXT NOT NULL,
    thumbnail_resource INTEGER NOT NULL,
    FOREIGN KEY(muscle_id) REFERENCES muscle_groups(muscle_id)
)
```

---

## Muscle Group Data (6 Total)

### SQL INSERT Statements

```sql
-- muscle_id = 1
INSERT INTO muscle_groups (muscle_name, muscle_image_resource) 
VALUES ('Chest', R.drawable.ic_activity);

-- muscle_id = 2
INSERT INTO muscle_groups (muscle_name, muscle_image_resource) 
VALUES ('Back', R.drawable.ic_activity);

-- muscle_id = 3
INSERT INTO muscle_groups (muscle_name, muscle_image_resource) 
VALUES ('Shoulders', R.drawable.ic_activity);

-- muscle_id = 4
INSERT INTO muscle_groups (muscle_name, muscle_image_resource) 
VALUES ('Arms', R.drawable.ic_activity);

-- muscle_id = 5
INSERT INTO muscle_groups (muscle_name, muscle_image_resource) 
VALUES ('Legs', R.drawable.ic_activity);

-- muscle_id = 6
INSERT INTO muscle_groups (muscle_name, muscle_image_resource) 
VALUES ('Core', R.drawable.ic_activity);
```

---

## Complete Exercise Library (18 Exercises)

### 1. CHEST EXERCISES (muscle_id = 1) - 3 Exercises

#### Push-ups
```sql
INSERT INTO gym_exercises (muscle_id, exercise_name, exercise_description, thumbnail_resource) 
VALUES (1, 'Push-ups', 'Classic bodyweight chest exercise. Place hands shoulder-width apart, keep your body straight, and lower yourself until your chest nearly touches the ground. Push back up to starting position. Excellent for building upper body strength.', R.drawable.ic_activity);
```

#### Bench Press
```sql
INSERT INTO gym_exercises (muscle_id, exercise_name, exercise_description, thumbnail_resource) 
VALUES (1, 'Bench Press', 'Lie flat on a bench with feet firmly on the floor. Grip the barbell slightly wider than shoulder-width. Lower the bar to your mid-chest in a controlled motion, then press it back up to the starting position. Keep your shoulder blades retracted and core engaged throughout.', R.drawable.ic_activity);
```

#### Dumbbell Flyes
```sql
INSERT INTO gym_exercises (muscle_id, exercise_name, exercise_description, thumbnail_resource) 
VALUES (1, 'Dumbbell Flyes', 'Lie on a bench holding dumbbells above your chest with palms facing each other. Lower the weights out to the sides in a wide arc with a slight bend in your elbows, feeling a stretch across your chest. Bring them back up in a hugging motion, squeezing your pecs at the top.', R.drawable.ic_activity);
```

---

### 2. BACK EXERCISES (muscle_id = 2) - 3 Exercises

#### Lat Pulldowns ✨ (User Requested)
```sql
INSERT INTO gym_exercises (muscle_id, exercise_name, exercise_description, thumbnail_resource) 
VALUES (2, 'Lat Pulldowns', 'Sit at a lat pulldown machine and grip the bar wider than shoulder-width. Pull the bar down to your upper chest while keeping your torso upright. Squeeze your shoulder blades together at the bottom, then slowly return to the starting position. Focus on pulling with your lats, not your arms.', R.drawable.ic_activity);
```

#### Seated Row ✨ (User Requested)
```sql
INSERT INTO gym_exercises (muscle_id, exercise_name, exercise_description, thumbnail_resource) 
VALUES (2, 'Seated Row', 'Sit at a cable row machine with feet on the platform and knees slightly bent. Pull the handle to your abdomen, keeping your back straight and squeezing your shoulder blades together. Slowly extend your arms back to the starting position while maintaining tension.', R.drawable.ic_activity);
```

#### Deadlifts
```sql
INSERT INTO gym_exercises (muscle_id, exercise_name, exercise_description, thumbnail_resource) 
VALUES (2, 'Deadlifts', 'Stand with feet hip-width apart, barbell over mid-foot. Bend at hips and knees to grip the bar with hands just outside your legs. Keep your back straight, chest up, and core tight. Lift by extending hips and knees simultaneously, keeping the bar close to your body. Lower with control.', R.drawable.ic_activity);
```

---

### 3. SHOULDERS EXERCISES (muscle_id = 3) - 3 Exercises

#### Overhead Press ✨ (User Requested)
```sql
INSERT INTO gym_exercises (muscle_id, exercise_name, exercise_description, thumbnail_resource) 
VALUES (3, 'Overhead Press', 'Stand with feet shoulder-width apart, holding dumbbells or a barbell at shoulder height. Press the weight directly overhead until arms are fully extended, keeping your core engaged. Lower back to shoulder level with control. Avoid arching your lower back.', R.drawable.ic_activity);
```

#### Lateral Raise ✨ (User Requested)
```sql
INSERT INTO gym_exercises (muscle_id, exercise_name, exercise_description, thumbnail_resource) 
VALUES (3, 'Lateral Raise', 'Stand holding dumbbells at your sides with palms facing inward. Raise the weights out to the sides with a slight bend in your elbows until arms are parallel to the floor. Focus on leading with your elbows. Lower slowly back to starting position, maintaining control.', R.drawable.ic_activity);
```

#### Front Raises
```sql
INSERT INTO gym_exercises (muscle_id, exercise_name, exercise_description, thumbnail_resource) 
VALUES (3, 'Front Raises', 'Stand holding dumbbells in front of your thighs. Raise one or both weights forward and upward to shoulder height, keeping arms straight but not locked. Slowly lower back down and repeat. This targets the front deltoids.', R.drawable.ic_activity);
```

---

### 4. ARMS EXERCISES (muscle_id = 4) - 3 Exercises

#### Bicep Curls ✨ (User Requested)
```sql
INSERT INTO gym_exercises (muscle_id, exercise_name, exercise_description, thumbnail_resource) 
VALUES (4, 'Bicep Curls', 'Stand with dumbbells at your sides, palms facing forward. Curl the weights up towards your shoulders by bending at the elbows, keeping your upper arms stationary. Squeeze your biceps at the top, then slowly lower the weights back down. Avoid swinging or using momentum.', R.drawable.ic_activity);
```

#### Tricep Pushdowns ✨ (User Requested)
```sql
INSERT INTO gym_exercises (muscle_id, exercise_name, exercise_description, thumbnail_resource) 
VALUES (4, 'Tricep Pushdowns', 'Stand facing a cable machine with a rope or bar attachment set at upper chest height. Grip the attachment and push down until your arms are fully extended, keeping your elbows close to your sides. Squeeze your triceps at the bottom, then slowly return to the starting position.', R.drawable.ic_activity);
```

#### Hammer Curls
```sql
INSERT INTO gym_exercises (muscle_id, exercise_name, exercise_description, thumbnail_resource) 
VALUES (4, 'Hammer Curls', 'Stand with dumbbells at your sides, palms facing each other (neutral grip). Curl the weights towards your shoulders while maintaining the neutral grip throughout. This variation targets both biceps and forearms. Lower with control.', R.drawable.ic_activity);
```

---

### 5. LEGS EXERCISES (muscle_id = 5) - 3 Exercises

#### Squats ✨ (User Requested)
```sql
INSERT INTO gym_exercises (muscle_id, exercise_name, exercise_description, thumbnail_resource) 
VALUES (5, 'Squats', 'Stand with feet shoulder-width apart, toes slightly pointed out. Lower your hips as if sitting back into a chair, keeping your chest up and weight on your heels. Descend until thighs are parallel to the ground. Drive through your heels to return to standing.', R.drawable.ic_activity);
```

#### Leg Press ✨ (User Requested)
```sql
INSERT INTO gym_exercises (muscle_id, exercise_name, exercise_description, thumbnail_resource) 
VALUES (5, 'Leg Press', 'Sit in a leg press machine with feet shoulder-width apart on the platform. Release the safety handles and lower the platform by bending your knees until they reach 90 degrees. Push through your heels to extend your legs back to the starting position. Keep your back flat against the seat.', R.drawable.ic_activity);
```

#### Lunges
```sql
INSERT INTO gym_exercises (muscle_id, exercise_name, exercise_description, thumbnail_resource) 
VALUES (5, 'Lunges', 'Stand with feet hip-width apart. Step forward with one leg and lower your hips until both knees are bent at approximately 90 degrees. The back knee should nearly touch the ground. Push back to the starting position through your front heel and alternate legs.', R.drawable.ic_activity);
```

---

### 6. CORE EXERCISES (muscle_id = 6) - 3 Exercises

#### Planks ✨ (User Requested)
```sql
INSERT INTO gym_exercises (muscle_id, exercise_name, exercise_description, thumbnail_resource) 
VALUES (6, 'Planks', 'Start in a push-up position with forearms on the ground, elbows directly under shoulders. Keep your body in a straight line from head to heels, engaging your core, glutes, and quads. Hold this position without letting your hips sag or pike up. Breathe steadily.', R.drawable.ic_activity);
```

#### Crunches ✨ (User Requested)
```sql
INSERT INTO gym_exercises (muscle_id, exercise_name, exercise_description, thumbnail_resource) 
VALUES (6, 'Crunches', 'Lie on your back with knees bent and feet flat on the floor. Place hands behind your head or across your chest. Lift your shoulder blades off the ground by contracting your abs, bringing your ribs toward your hips. Lower back down with control without fully relaxing.', R.drawable.ic_activity);
```

#### Russian Twists
```sql
INSERT INTO gym_exercises (muscle_id, exercise_name, exercise_description, thumbnail_resource) 
VALUES (6, 'Russian Twists', 'Sit on the floor with knees bent and feet lifted. Lean back slightly to engage your core. Hold your hands together or hold a weight. Rotate your torso from side to side, touching the ground beside your hip with each twist. Keep your core tight throughout.', R.drawable.ic_activity);
```

---

## Database Query Examples

### Get All Exercises for a Specific Muscle Group
```kotlin
// Example: Get all Back exercises (muscle_id = 2)
val backExercises = dbHelper.getExercisesByMuscleId(2)

// Raw SQL executed:
// SELECT * FROM gym_exercises WHERE muscle_id = 2
```

### Get Specific Exercise Details
```kotlin
// Example: Get details for exercise_id = 5
val exercise = dbHelper.getExerciseDetail(5)

// Raw SQL executed:
// SELECT * FROM gym_exercises WHERE exercise_id = 5
```

---

## Foreign Key Constraints ✅

All exercises properly reference their parent muscle groups:
- **Chest** (ID: 1) → 3 exercises
- **Back** (ID: 2) → 3 exercises  
- **Shoulders** (ID: 3) → 3 exercises
- **Arms** (ID: 4) → 3 exercises
- **Legs** (ID: 5) → 3 exercises
- **Core** (ID: 6) → 3 exercises

The foreign key constraint ensures:
```sql
FOREIGN KEY(muscle_id) REFERENCES muscle_groups(muscle_id)
```

This guarantees that filtering by `muscle_id` will work correctly across the app.

---

## Placeholder Resources

### Images & Thumbnails
Currently using: `R.drawable.ic_activity` as placeholder for all icons and thumbnails

**To replace with real images:**
1. Add your image files to `res/drawable/` folder
2. Update the resource IDs in the database seed data
3. Increment `DATABASE_VERSION` to trigger database upgrade
4. Rebuild the app

### Video URLs
The database schema supports `thumbnail_resource` (integer resource IDs). 

**Note:** The current implementation doesn't include video URLs in the database. To add video support later:
1. Add a `video_url TEXT` column to the `gym_exercises` table
2. Populate with placeholder strings like `"https://placeholder.com/video.mp4"`
3. Update `ExerciseDetailFragment` to load videos using VideoView or ExoPlayer

---

## Summary

✅ **6 Muscle Groups** - Complete with consistent IDs  
✅ **18 Total Exercises** - 3 per muscle group  
✅ **All User-Requested Exercises** - Lat Pulldowns, Seated Row, Leg Press, Overhead Press, Lateral Raise, Bicep Curls, Tricep Pushdowns, Squats, Planks, Crunches  
✅ **Proper Foreign Keys** - Ensures filtering works correctly  
✅ **Detailed Descriptions** - Comprehensive "How to Perform" instructions  
✅ **Placeholder Resources** - Ready for real images and videos later  

The gym library is now fully populated and ready to use! 🎯
