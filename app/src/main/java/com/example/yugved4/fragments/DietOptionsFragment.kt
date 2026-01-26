package com.example.yugved4.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.yugved4.R
import com.google.android.material.card.MaterialCardView

class DietOptionsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_diet_options, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<MaterialCardView>(R.id.cardCreatePlan).setOnClickListener {
            // Navigate to the existing survey fragment
            // Note: We will rename the ID in nav_graph to dietSurveyFragment
            findNavController().navigate(R.id.action_dietFragment_to_dietSurveyFragment)
        }

        view.findViewById<MaterialCardView>(R.id.cardViewSaved).setOnClickListener {
            // Navigate to the list of saved plans
            findNavController().navigate(R.id.action_dietFragment_to_savedDietListFragment)
        }
    }
}
