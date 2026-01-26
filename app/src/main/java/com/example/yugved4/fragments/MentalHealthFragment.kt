package com.example.yugved4.fragments


import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yugved4.R
import com.example.yugved4.adapters.HelplineCardAdapter
import com.example.yugved4.utils.AuthHelper
import com.example.yugved4.database.DatabaseHelper
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.firestore.FirebaseFirestore
import com.google.android.material.textfield.TextInputEditText
import java.util.Date

/**
 * Mental Health Fragment - Complete mental wellness toolkit
 * Features: Mood tracking, breathing exercises, relaxation audio, daily quotes, helplines
 */
class MentalHealthFragment : Fragment() {

    private lateinit var rvHelplines: RecyclerView
    private lateinit var dbHelper: DatabaseHelper
    
    // UI Components

    private lateinit var btnToggleAudio: MaterialButton
    private lateinit var btnMood1: Button
    private lateinit var btnMood2: Button
    private lateinit var btnMood3: Button
    private lateinit var btnMood4: Button
    private lateinit var btnMood5: Button
    
    private lateinit var etJournalEntry: TextInputEditText
    private lateinit var btnSaveJournal: MaterialButton
    private lateinit var btnViewHistory: MaterialButton
    
    private var selectedMood: Int = 0
    private var selectedMoodDescription: String = ""
    

    
    // Audio player
    private var mediaPlayer: MediaPlayer? = null
    private var isPlaying = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_mental_health, container, false)

        // Initialize database helper
        dbHelper = DatabaseHelper(requireContext())

        // Initialize views

        btnToggleAudio = view.findViewById(R.id.btnToggleAudio)
        btnMood1 = view.findViewById(R.id.btnMood1)
        btnMood2 = view.findViewById(R.id.btnMood2)
        btnMood3 = view.findViewById(R.id.btnMood3)
        btnMood4 = view.findViewById(R.id.btnMood4)
        btnMood5 = view.findViewById(R.id.btnMood5)
        
        etJournalEntry = view.findViewById(R.id.etJournalEntry)
        btnSaveJournal = view.findViewById(R.id.btnSaveJournal)
        btnViewHistory = view.findViewById(R.id.btnViewHistory)
        
        btnSaveJournal.setOnClickListener {
            saveJournalEntry()
        }
        
        btnViewHistory.setOnClickListener {
            showJournalHistory()
        }
        
        // Initialize RecyclerView
        rvHelplines = view.findViewById(R.id.rvHelplines)
        setupRecyclerView()
        

        
        // Setup mood buttons
        setupMoodTracking()
        

        
        // Setup audio player (only if audio file exists)
        setupAudioPlayer()

        return view
    }
    

    
    /**
     * Setup mood tracking buttons
     */
    private fun setupMoodTracking() {
        btnMood1.setOnClickListener { saveMood(1, "😫 Terrible") }
        btnMood2.setOnClickListener { saveMood(2, "😟 Not great") }
        btnMood3.setOnClickListener { saveMood(3, "😐 Okay") }
        btnMood4.setOnClickListener { saveMood(4, "🙂 Good") }
        btnMood5.setOnClickListener { saveMood(5, "😄 Excellent") }
    }
    
    /**
     * Save mood to database and show feedback
     */
    /**
     * Save mood locally and update UI selection
     */
    private fun saveMood(score: Int, description: String) {
        selectedMood = score
        selectedMoodDescription = description
        
        // Visual feedback for selection (reset others first)
        resetMoodButtons()
        highlightSelectedMood(score)
        
        Toast.makeText(requireContext(), "Mood selected: $description", Toast.LENGTH_SHORT).show()
    }
    
    private fun resetMoodButtons() {
        val defaultAlpha = 0.5f
        btnMood1.alpha = defaultAlpha
        btnMood2.alpha = defaultAlpha
        btnMood3.alpha = defaultAlpha
        btnMood4.alpha = defaultAlpha
        btnMood5.alpha = defaultAlpha
    }
    
    private fun highlightSelectedMood(score: Int) {
        val selectedAlpha = 1.0f
        when(score) {
            1 -> btnMood1.alpha = selectedAlpha
            2 -> btnMood2.alpha = selectedAlpha
            3 -> btnMood3.alpha = selectedAlpha
            4 -> btnMood4.alpha = selectedAlpha
            5 -> btnMood5.alpha = selectedAlpha
        }
    }

    private fun showJournalHistory() {
        val bottomSheet = JournalHistoryBottomSheet()
        bottomSheet.show(parentFragmentManager, JournalHistoryBottomSheet.TAG)
    }

    private fun saveJournalEntry() {
        val content = etJournalEntry.text.toString().trim()
        
        if (selectedMood == 0) {
            Toast.makeText(requireContext(), "Please select a mood first", Toast.LENGTH_SHORT).show()
            return
        }
        
        if (content.isEmpty()) {
            Toast.makeText(requireContext(), "Please write your thoughts", Toast.LENGTH_SHORT).show()
            return
        }
        
        val userId = AuthHelper.getCurrentUserId()
        if (userId == null) {
            Toast.makeText(requireContext(), "Please sign in to save journal", Toast.LENGTH_SHORT).show()
            return
        }
        
        // Prepare data
        val journalEntry = hashMapOf(
            "mood" to selectedMood,
            "moodDescription" to selectedMoodDescription,
            "content" to content,
            "date" to Date(),
            "userId" to userId
        )
        
        // Show loading state
        btnSaveJournal.isEnabled = false
        btnSaveJournal.text = "Saving..."
        
        // Save to Firestore
        FirebaseFirestore.getInstance()
            .collection("users")
            .document(userId)
            .collection("journal")
            .add(journalEntry)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Journal saved successfully", Toast.LENGTH_SHORT).show()
                etJournalEntry.text?.clear()
                btnSaveJournal.isEnabled = true
                btnSaveJournal.text = "Save Entry"
            }
            .addOnFailureListener { e ->
                Toast.makeText(requireContext(), "Failed to save: ${e.message}", Toast.LENGTH_SHORT).show()
                btnSaveJournal.isEnabled = true
                btnSaveJournal.text = "Save Entry"
            }
    }
    

    
    /**
     * Setup audio player for relaxation sounds
     * Note: Requires rain_sounds.mp3 in res/raw directory
     */
    private fun setupAudioPlayer() {
        btnToggleAudio.setOnClickListener {
            toggleAudio()
        }
    }
    
    /**
     * Toggle audio playback
     */
    private fun toggleAudio() {
        try {
            if (isPlaying) {
                // Pause audio
                mediaPlayer?.pause()
                isPlaying = false
                btnToggleAudio.text = "▶️ Play Sounds"
                btnToggleAudio.setIconResource(android.R.drawable.ic_media_play)
            } else {
                // Initialize if needed
                if (mediaPlayer == null) {
                    // Check if audio file exists by trying to create MediaPlayer
                    try {
                        // TODO: Add rain_sounds.mp3 to res/raw/ directory to enable this feature
                        // mediaPlayer = MediaPlayer.create(requireContext(), R.raw.rain_sounds)
                        // mediaPlayer?.isLooping = true
                        
                        // Show message that audio file is not available
                        Toast.makeText(
                            requireContext(),
                            "Audio file not found. Please add rain_sounds.mp3 to res/raw/",
                            Toast.LENGTH_LONG
                        ).show()
                        return
                    } catch (e: Exception) {
                        Toast.makeText(
                            requireContext(),
                            "Audio file not found. Please add rain_sounds.mp3 to res/raw/",
                            Toast.LENGTH_LONG
                        ).show()
                        return
                    }
                }
                
                // Play audio
                mediaPlayer?.start()
                isPlaying = true
                btnToggleAudio.text = "⏸️ Pause Sounds"
                btnToggleAudio.setIconResource(android.R.drawable.ic_media_pause)
            }
        } catch (e: Exception) {
            Toast.makeText(
                requireContext(),
                "Error playing audio: ${e.message}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun setupRecyclerView() {
        // Get all helplines from database
        val helplines = dbHelper.getAllHelplines()

        // Create adapter
        val adapter = HelplineCardAdapter(helplines)

        // Setup RecyclerView
        rvHelplines.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
            setHasFixedSize(true)
        }
    }
    
    override fun onPause() {
        super.onPause()
        // Pause audio when fragment is paused
        if (isPlaying) {
            mediaPlayer?.pause()
        }
    }
    
    override fun onResume() {
        super.onResume()
        // Resume audio if it was playing before
        if (isPlaying) {
            mediaPlayer?.start()
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        

        
        // Clean up media player
        mediaPlayer?.apply {
            if (isPlaying) {
                stop()
            }
            release()
        }
        mediaPlayer = null
        isPlaying = false
    }
}


