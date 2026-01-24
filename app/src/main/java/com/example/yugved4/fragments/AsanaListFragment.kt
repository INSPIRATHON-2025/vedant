package com.example.yugved4.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yugved4.R
import com.example.yugved4.adapters.AsanaAdapter
import com.example.yugved4.models.YogaAsana
import com.example.yugved4.utils.YogaDataProvider

/**
 * Fragment displaying a list of yoga asanas filtered by category
 */
class AsanaListFragment : Fragment() {

    private lateinit var rvAsanas: RecyclerView
    private lateinit var tvCategoryHeader: TextView
    private lateinit var tvEmptyState: TextView
    private lateinit var asanaAdapter: AsanaAdapter
    
    private var categoryName: String = ""
    private val asanas = mutableListOf<YogaAsana>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_asana_list, container, false)
        
        // Get category name from arguments
        categoryName = arguments?.getString("categoryName") ?: ""
        
        // Initialize views
        rvAsanas = view.findViewById(R.id.rvAsanas)
        tvCategoryHeader = view.findViewById(R.id.tvCategoryHeader)
        tvEmptyState = view.findViewById(R.id.tvEmptyState)
        
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        loadAsanas()
        setupRecyclerView()
        updateUI()
    }

    /**
     * Load asanas filtered by category
     */
    private fun loadAsanas() {
        asanas.clear()
        asanas.addAll(YogaDataProvider.getAsanasByCategory(categoryName))
    }

    /**
     * Setup RecyclerView with adapter
     */
    private fun setupRecyclerView() {
        asanaAdapter = AsanaAdapter(asanas) { asana ->
            // Navigate to AsanaDetailFragment with asana ID
            val bundle = Bundle().apply {
                putString("asanaId", asana.asanaId)
            }
            findNavController().navigate(R.id.action_asanaListFragment_to_asanaDetailFragment, bundle)
        }

        rvAsanas.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = asanaAdapter
        }
    }

    /**
     * Update UI based on data
     */
    private fun updateUI() {
        tvCategoryHeader.text = "$categoryName Asanas"
        
        if (asanas.isEmpty()) {
            rvAsanas.visibility = View.GONE
            tvEmptyState.visibility = View.VISIBLE
        } else {
            rvAsanas.visibility = View.VISIBLE
            tvEmptyState.visibility = View.GONE
        }
    }
}
