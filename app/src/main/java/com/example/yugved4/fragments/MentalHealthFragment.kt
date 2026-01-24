package com.example.yugved4.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yugved4.R
import com.example.yugved4.adapters.HelplineAdapter
import com.example.yugved4.models.Helpline
import com.example.yugved4.utils.HelplineDataProvider

/**
 * Mental Health Fragment - Displays helplines with calling functionality
 */
class MentalHealthFragment : Fragment() {

    private lateinit var rvHelplines: RecyclerView
    private lateinit var helplineAdapter: HelplineAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_helplines, container, false)
        
        // Initialize RecyclerView
        rvHelplines = view.findViewById(R.id.rvHelplines)
        setupRecyclerView()
        
        return view
    }

    private fun setupRecyclerView() {
        // Get helplines data
        val helplines = HelplineDataProvider.getHelplines()
        
        // Create adapter with click listener
        helplineAdapter = HelplineAdapter(helplines) { helpline ->
            initiateCall(helpline)
        }
        
        // Setup RecyclerView
        rvHelplines.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = helplineAdapter
            setHasFixedSize(true)
        }
    }

    /**
     * Initiates a phone call using Intent.ACTION_DIAL
     * This opens the phone dialer with the number pre-filled
     * and doesn't require CALL_PHONE permission
     */
    private fun initiateCall(helpline: Helpline) {
        try {
            val phoneUri = Uri.parse("tel:${helpline.phoneNumber}")
            val dialIntent = Intent(Intent.ACTION_DIAL, phoneUri)
            
            // Check if there's an app that can handle this intent
            if (dialIntent.resolveActivity(requireActivity().packageManager) != null) {
                startActivity(dialIntent)
            } else {
                Toast.makeText(
                    requireContext(),
                    "No phone app found",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } catch (e: Exception) {
            Toast.makeText(
                requireContext(),
                "Unable to open dialer: ${e.message}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
