package com.example.yugved4.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.yugved4.R

class SavedDietDetailFragment : Fragment() {

    private lateinit var tvCaloriesValue: TextView
    private lateinit var tvDietTypeValue: TextView
    private lateinit var tvBreakfast: TextView
    private lateinit var tvLunch: TextView
    private lateinit var tvDinner: TextView
    private lateinit var tvSnacks: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_saved_diet_detail, container, false)
        
        tvCaloriesValue = view.findViewById(R.id.tvCaloriesValue)
        tvDietTypeValue = view.findViewById(R.id.tvDietTypeValue)
        tvBreakfast = view.findViewById(R.id.tvBreakfast)
        tvLunch = view.findViewById(R.id.tvLunch)
        tvDinner = view.findViewById(R.id.tvDinner)
        tvSnacks = view.findViewById(R.id.tvSnacks)
        
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Retrieve data from arguments
        val calories = arguments?.getInt("calories") ?: 0
        val dietType = arguments?.getString("dietType") ?: "-"
        val breakfast = arguments?.getString("breakfast") ?: "-"
        val lunch = arguments?.getString("lunch") ?: "-"
        val dinner = arguments?.getString("dinner") ?: "-"
        val snacks = arguments?.getString("snacks") ?: "-"
        
        tvCaloriesValue.text = "$calories kcal"
        tvDietTypeValue.text = "Diet Type: $dietType"
        tvBreakfast.text = breakfast
        tvLunch.text = lunch
        tvDinner.text = dinner
        tvSnacks.text = snacks
    }
}
