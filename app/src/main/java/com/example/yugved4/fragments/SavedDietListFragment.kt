package com.example.yugved4.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.yugved4.R
import com.example.yugved4.adapters.SavedDietListAdapter
import com.example.yugved4.utils.AuthHelper
import com.example.yugved4.utils.FirestoreHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SavedDietListFragment : Fragment() {

    private lateinit var rvSavedDiets: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var tvEmpty: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_saved_diet_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvSavedDiets = view.findViewById(R.id.rvSavedDiets)
        progressBar = view.findViewById(R.id.progressBar)
        tvEmpty = view.findViewById(R.id.tvEmpty)

        loadSavedDiets()
    }

    private fun loadSavedDiets() {
        val currentUser = AuthHelper.getCurrentUser()
        if (currentUser == null) {
            tvEmpty.text = "Please login to view saved plans"
            tvEmpty.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val dietPlans = FirestoreHelper.getUserDietPlans(currentUser.uid)
                
                withContext(Dispatchers.Main) {
                    progressBar.visibility = View.GONE
                    
                    if (dietPlans.isNotEmpty()) {
                        rvSavedDiets.visibility = View.VISIBLE
                        tvEmpty.visibility = View.GONE
                        
                        rvSavedDiets.adapter = SavedDietListAdapter(dietPlans) { selectedPlan ->
                            // TODO: Pass actual object or ID. For now finding logic to pass details
                            // Since we don't have a shared ViewModel yet, we'll implement Detail fragment to load by ID
                            // But for now, let's just use a simple bundle if the object is Parcelable (it's not)
                            // Or, we can just save it to a temporary singleton/cache
                            
                            // For simplicity in this quick iteration, we'll assume we can pass the ID
                            // and fetch it again, OR (better) pass the fields in arguments.
                            val bundle = Bundle().apply {
                                putString("dietPlanId", selectedPlan.id) // Assuming we added ID on fetch
                                // Or pass all fields
                                putInt("calories", selectedPlan.targetCalories)
                                putString("dietType", selectedPlan.dietType)
                                putString("breakfast", selectedPlan.breakfast)
                                putString("lunch", selectedPlan.lunch)
                                putString("dinner", selectedPlan.dinner)
                                putString("snacks", selectedPlan.snacks)
                            }
                            findNavController().navigate(R.id.action_savedDietListFragment_to_savedDietDetailFragment, bundle)
                        }
                    } else {
                        rvSavedDiets.visibility = View.GONE
                        tvEmpty.visibility = View.VISIBLE
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    progressBar.visibility = View.GONE
                    tvEmpty.text = "Error loading plans: ${e.message}"
                    tvEmpty.visibility = View.VISIBLE
                }
            }
        }
    }
}
