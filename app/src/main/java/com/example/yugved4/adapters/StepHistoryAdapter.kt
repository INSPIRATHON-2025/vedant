package com.example.yugved4.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.yugved4.R
import com.example.yugved4.models.StepHistory
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * RecyclerView Adapter for displaying step history entries
 * Shows date and step count for each day
 */
class StepHistoryAdapter(private var stepHistoryList: List<StepHistory>) :
    RecyclerView.Adapter<StepHistoryAdapter.StepHistoryViewHolder>() {

    class StepHistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvHistoryDate: TextView = view.findViewById(R.id.tvHistoryDate)
        val tvHistorySteps: TextView = view.findViewById(R.id.tvHistorySteps)
        val tvStepsLabel: TextView = view.findViewById(R.id.tvStepsLabel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepHistoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_step_history, parent, false)
        return StepHistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: StepHistoryViewHolder, position: Int) {
        val stepHistory = stepHistoryList[position]
        
        // Format the date to a more readable format (e.g., "February 1, 2026")
        val formattedDate = formatDate(stepHistory.date)
        holder.tvHistoryDate.text = formattedDate
        
        // Format steps with thousand separators (e.g., "8,500")
        val numberFormat = NumberFormat.getNumberInstance(Locale.getDefault())
        holder.tvHistorySteps.text = numberFormat.format(stepHistory.steps)
    }

    override fun getItemCount(): Int = stepHistoryList.size

    /**
     * Update the adapter data and refresh the RecyclerView
     */
    fun updateData(newList: List<StepHistory>) {
        stepHistoryList = newList
        notifyDataSetChanged()
    }

    /**
     * Format date from "yyyy-MM-dd" to a more readable format like "February 1, 2026"
     */
    private fun formatDate(dateString: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outputFormat = SimpleDateFormat("MMMM d, yyyy", Locale.getDefault())
            val date = inputFormat.parse(dateString)
            date?.let { outputFormat.format(it) } ?: dateString
        } catch (e: Exception) {
            dateString // Return original if parsing fails
        }
    }
}
