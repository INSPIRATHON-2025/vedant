package com.example.yugved4.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yugved4.R
import com.example.yugved4.adapters.JournalAdapter
import com.example.yugved4.models.JournalEntry
import com.example.yugved4.utils.AuthHelper
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class JournalHistoryBottomSheet : BottomSheetDialogFragment() {

    private lateinit var rvJournalHistory: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var tvEmptyState: TextView
    private val journalEntries = mutableListOf<JournalEntry>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_journal_history, container, false)
        
        rvJournalHistory = view.findViewById(R.id.rvJournalHistory)
        progressBar = view.findViewById(R.id.progressBar)
        tvEmptyState = view.findViewById(R.id.tvEmptyState)
        
        setupRecyclerView()
        fetchJournalHistory()
        
        return view
    }

    private fun setupRecyclerView() {
        rvJournalHistory.layoutManager = LinearLayoutManager(requireContext())
        rvJournalHistory.adapter = JournalAdapter(journalEntries)
    }

    private fun fetchJournalHistory() {
        val userId = AuthHelper.getCurrentUserId()
        if (userId == null) {
            Toast.makeText(requireContext(), "User not signed in", Toast.LENGTH_SHORT).show()
            dismiss()
            return
        }

        progressBar.visibility = View.VISIBLE
        tvEmptyState.visibility = View.GONE
        
        FirebaseFirestore.getInstance()
            .collection("users")
            .document(userId)
            .collection("journal")
            .orderBy("date", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { documents ->
                journalEntries.clear()
                for (document in documents) {
                    val entry = document.toObject(JournalEntry::class.java).copy(id = document.id)
                    journalEntries.add(entry)
                }
                
                progressBar.visibility = View.GONE
                if (journalEntries.isEmpty()) {
                    tvEmptyState.visibility = View.VISIBLE
                } else {
                    rvJournalHistory.adapter?.notifyDataSetChanged()
                }
            }
            .addOnFailureListener { e ->
                progressBar.visibility = View.GONE
                Toast.makeText(requireContext(), "Failed to load history: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    companion object {
        const val TAG = "JournalHistoryBottomSheet"
    }
}
