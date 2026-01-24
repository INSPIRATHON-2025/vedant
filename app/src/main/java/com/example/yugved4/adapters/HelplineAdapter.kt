package com.example.yugved4.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.yugved4.R
import com.example.yugved4.models.Helpline

/**
 * Adapter for displaying mental health helplines in a RecyclerView
 */
class HelplineAdapter(
    private val helplines: List<Helpline>,
    private val onCallClick: (Helpline) -> Unit
) : RecyclerView.Adapter<HelplineAdapter.HelplineViewHolder>() {

    inner class HelplineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvHelplineName)
        val tvDescription: TextView = itemView.findViewById(R.id.tvHelplineDescription)
        val btnCall: Button = itemView.findViewById(R.id.btnCall)
        val ivEmergency: ImageView = itemView.findViewById(R.id.ivEmergency)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HelplineViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_helpline, parent, false)
        return HelplineViewHolder(view)
    }

    override fun onBindViewHolder(holder: HelplineViewHolder, position: Int) {
        val helpline = helplines[position]
        
        holder.tvName.text = helpline.name
        holder.tvDescription.text = helpline.description
        
        // Show emergency indicator for 24x7 helplines
        holder.ivEmergency.visibility = if (helpline.isEmergency) {
            View.VISIBLE
        } else {
            View.GONE
        }
        
        // Handle call button click
        holder.btnCall.setOnClickListener {
            onCallClick(helpline)
        }
    }

    override fun getItemCount(): Int = helplines.size
}
