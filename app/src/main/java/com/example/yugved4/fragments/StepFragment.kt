package com.example.yugved4.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.yugved4.database.DatabaseHelper
import com.example.yugved4.databinding.FragmentStepBinding
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Step Counter Fragment
 * Enhanced with Samsung Health-style features:
 * - WHO 150 min/week goal
 * - Calories with fun comparison
 * - Distance with colosseum comparison
 */
class StepFragment : Fragment(), SensorEventListener {

    private var _binding: FragmentStepBinding? = null
    private val binding get() = _binding!!

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var auth: FirebaseAuth
    private var sensorManager: SensorManager? = null
    private var stepCounterSensor: Sensor? = null
    
    private var initialStepCount: Int = 0
    private var isFirstReading = true
    private var isTracking = false
    
    // Constants for calculations
    private val STEPS_PER_MINUTE_WALKING = 100 // Average walking cadence
    private val CALORIES_PER_STEP = 0.04 // Average kcal per step
    private val STEP_LENGTH_METERS = 0.762 // Average stride length (~2.5ft)
    private val COCONUT_WATER_CALORIES = 45 // Approx calories in 1 cup
    private val COLOSSEUM_LOOP_KM = 0.527 // Perimeter of Colosseum
    private val WHO_WEEKLY_GOAL_MIN = 150
    
    // Permission launcher
    private val activityPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            startTracking()
            Toast.makeText(requireContext(), "Step tracking enabled", Toast.LENGTH_SHORT).show()
        } else {
            binding.btnToggle.isChecked = false
            Toast.makeText(
                requireContext(),
                "Permission required for step tracking",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStepBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dbHelper = DatabaseHelper(requireContext())
        auth = FirebaseAuth.getInstance()
        sensorManager = requireContext().getSystemService(android.content.Context.SENSOR_SERVICE) as? SensorManager
        stepCounterSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        
        checkSensorAvailability()
        loadSavedState()
        setupToggleButton()
        setupProfileButton()
        
        // Initial UI update
        updateWeeklyStats()
    }
    
    private fun checkSensorAvailability() {
        if (stepCounterSensor == null) {
            binding.btnToggle.isEnabled = false
            binding.tvStatus.text = "Step counter not available on this device"
            Toast.makeText(
                requireContext(),
                "⚠️ NO STEP SENSOR! Are you on an emulator? Use a real phone!",
                Toast.LENGTH_LONG
            ).show()
        }
    }
    
    private fun loadSavedState() {
        // Load toggle state from database
        val isEnabled = dbHelper.getStepCounterEnabled()
        binding.btnToggle.isChecked = isEnabled
        isTracking = isEnabled
        
        // Load today's step count
        val todaySteps = dbHelper.getTodayStepCount()
        updateStepDisplay(todaySteps)
        
        // Update status text
        updateStatusText()
        
        // Start tracking if enabled
        if (isEnabled) {
            registerStepSensor()
        }
    }
    
    private fun setupToggleButton() {
        binding.btnToggle.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Turning ON
                checkPermissionAndStart()
            } else {
                // Turning OFF
                stopTracking()
            }
        }
    }
    
    private fun setupProfileButton() {
        binding.btnProfile.setOnClickListener {
            val profileBottomSheet = ProfileBottomSheet()
            profileBottomSheet.show(parentFragmentManager, "ProfileBottomSheet")
        }
    }
    
    private fun checkPermissionAndStart() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            when {
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACTIVITY_RECOGNITION
                ) == PackageManager.PERMISSION_GRANTED -> {
                    startTracking()
                }
                shouldShowRequestPermissionRationale(Manifest.permission.ACTIVITY_RECOGNITION) -> {
                    Toast.makeText(
                        requireContext(),
                        "Permission needed to track your steps",
                        Toast.LENGTH_LONG
                    ).show()
                    activityPermissionLauncher.launch(Manifest.permission.ACTIVITY_RECOGNITION)
                }
                else -> {
                    activityPermissionLauncher.launch(Manifest.permission.ACTIVITY_RECOGNITION)
                }
            }
        } else {
            // Android 9 and below don't need runtime permission
            startTracking()
        }
    }
    
    private fun startTracking() {
        registerStepSensor()
        isTracking = true
        dbHelper.setStepCounterEnabled(true)
        updateStatusText()
        Toast.makeText(requireContext(), "Step tracking started", Toast.LENGTH_SHORT).show()
    }
    
    private fun stopTracking() {
        unregisterStepSensor()
        isTracking = false
        dbHelper.setStepCounterEnabled(false)
        updateStatusText()
        Toast.makeText(requireContext(), "Step tracking stopped", Toast.LENGTH_SHORT).show()
    }
    
    private fun registerStepSensor() {
        stepCounterSensor?.let { sensor ->
            sensorManager?.registerListener(
                this,
                sensor,
                SensorManager.SENSOR_DELAY_UI
            )
        }
    }
    
    private fun unregisterStepSensor() {
        sensorManager?.unregisterListener(this)
        isFirstReading = true
    }
    
    private fun updateStepDisplay(stepCount: Int) {
        // Calculate metrics
        val caloriesBurned = (stepCount * CALORIES_PER_STEP).toInt()
        val distanceKm = (stepCount * STEP_LENGTH_METERS) / 1000
        
        // Update Calories Card
        binding.tvCalories.text = caloriesBurned.toString()
        val cups = String.format("%.2f", caloriesBurned.toFloat() / COCONUT_WATER_CALORIES)
        binding.tvCalComparison.text = "≈$cups cups of nariyal pani"
        
        // Update Distance Card
        binding.tvDistance.text = String.format("%.2f", distanceKm)
        val laps = String.format("%.2f", distanceKm / COLOSSEUM_LOOP_KM)
        binding.tvDistComparison.text = "≈Run $laps lap(s) around the Colosseum"
        
        // Update Step Counter Card (Simplified)
        val stepGoal = 10000 // Default goal
        val progress = ((stepCount.toFloat() / stepGoal) * 100).toInt().coerceIn(0, 100)
        
        binding.progressSteps.progress = progress
        binding.tvStepCountDisplay.text = stepCount.toString()
        binding.tvStepGoalLabel.text = "/ $stepGoal"
        
        // Save today's activity minutes (Background tracking)
        // We assume all steps are "Brisk walking" for simplicity
        val walkingMinutes = stepCount / STEPS_PER_MINUTE_WALKING
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        dbHelper.saveActivityMinutes(today, "brisk_walking", walkingMinutes)
        
        // Update hidden fields for backward compatibility if any
        binding.tvStepCount.text = stepCount.toString()
    }
    
    private fun updateWeeklyStats() {
        // Method kept empty or removed as UI for this is removed
        // Background tracking still happens in updateStepDisplay
    }
    
    private fun updateStatusText() {
        binding.tvStatus.text = if (isTracking) {
            "Status: Tracking your steps..."
        } else {
            "Status: Step tracking is OFF"
        }
    }
    
    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            if (it.sensor.type == Sensor.TYPE_STEP_COUNTER) {
                val totalStepsSinceBoot = it.values[0].toInt()
                
                if (isFirstReading) {
                    val savedSteps = dbHelper.getTodayStepCount()
                    initialStepCount = totalStepsSinceBoot - savedSteps
                    isFirstReading = false
                }
                
                val todaySteps = totalStepsSinceBoot - initialStepCount
                
                // Save to database
                val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                dbHelper.saveStepCount(today, todaySteps)
                
                // Update UI
                updateStepDisplay(todaySteps)
            }
        }
    }
    
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Not needed
    }
    
    override fun onPause() {
        super.onPause()
        if (isTracking) {
            unregisterStepSensor()
        }
    }
    
    override fun onResume() {
        super.onResume()
        if (isTracking) {
            registerStepSensor()
            val todaySteps = dbHelper.getTodayStepCount()
            updateStepDisplay(todaySteps)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unregisterStepSensor()
        _binding = null
    }
}
