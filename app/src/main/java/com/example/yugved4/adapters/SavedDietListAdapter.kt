package com.example.yugved4.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.yugved4.R
import com.example.yugved4.models.UserDietPlan
import com.google.android.material.chip.Chip
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SavedDietListAdapter(
    private val dietPlans: List<UserDietPlan>,
    private val onItemClick: (UserDietPlan) -> Unit
) : RecyclerView.Adapter<SavedDietListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvCalories: TextView = view.findViewById(R.id.tvCalories)
        val tvDate: TextView = view.findViewById(R.id.tvDate)
        val chipType: Chip = view.findViewById(R.id.chipType)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_saved_diet, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val plan = dietPlans[position]
        
        holder.tvCalories.text = "${plan.targetCalories} kcal Plan"
        holder.chipType.text = plan.dietType
        
        val date = Date(plan.timestamp)
        val format = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
        holder.tvDate.text = "Saved on: ${format.format(date)}"
        
        holder.itemView.setOnClickListener {
            onItemClick(plan)
        }
    }

    override fun getItemCount(): Int = dietPlans.size
}
