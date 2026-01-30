package com.example.yugved4.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.yugved4.R
import com.example.yugved4.models.YogaAsana

/**
 * Adapter for displaying yoga asanas in a RecyclerView
 */
class AsanaAdapter(
    private val asanas: List<YogaAsana>,
    private val onAsanaClick: (YogaAsana) -> Unit
) : RecyclerView.Adapter<AsanaAdapter.AsanaViewHolder>() {

    inner class AsanaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivIcon: ImageView = itemView.findViewById(R.id.ivAsanaIcon)
        val tvSanskritName: TextView = itemView.findViewById(R.id.tvSanskritName)
        val tvEnglishName: TextView = itemView.findViewById(R.id.tvEnglishName)
        val tvDuration: TextView = itemView.findViewById(R.id.tvDuration)
        val tvDifficulty: TextView = itemView.findViewById(R.id.tvDifficulty)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsanaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_asana, parent, false)
        return AsanaViewHolder(view)
    }

    override fun onBindViewHolder(holder: AsanaViewHolder, position: Int) {
        val asana = asanas[position]
        
        holder.tvSanskritName.text = asana.sanskritName
        holder.tvEnglishName.text = asana.name
        holder.tvDuration.text = asana.duration
        holder.tvDifficulty.text = asana.difficultyLevel
        holder.ivIcon.setImageResource(asana.thumbnail)
        
        // Handle click
        holder.itemView.setOnClickListener {
            onAsanaClick(asana)
        }
    }

    override fun getItemCount(): Int = asanas.size
}
