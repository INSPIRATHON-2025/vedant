package com.example.yugved4.adapters

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.yugved4.R
import com.example.yugved4.models.JournalEntry
import java.util.Locale

class JournalAdapter(private val entries: List<JournalEntry>) :
    RecyclerView.Adapter<JournalAdapter.JournalViewHolder>() {

    class JournalViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvEntryMoodEmoji: TextView = view.findViewById(R.id.tvEntryMoodEmoji)
        val tvEntryDate: TextView = view.findViewById(R.id.tvEntryDate)
        val tvEntryMoodDesc: TextView = view.findViewById(R.id.tvEntryMoodDesc)
        val tvEntryContent: TextView = view.findViewById(R.id.tvEntryContent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JournalViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_journal_entry, parent, false)
        return JournalViewHolder(view)
    }

    override fun onBindViewHolder(holder: JournalViewHolder, position: Int) {
        val entry = entries[position]

        // Set Mood Emoji based on score
        holder.tvEntryMoodEmoji.text = getEmojiForScore(entry.mood)

        // Set Mood Description and Content
        holder.tvEntryMoodDesc.text = entry.moodDescription
        holder.tvEntryContent.text = entry.content

        // Format Date
        entry.date?.let {
            val dateFormat = DateFormat.getBestDateTimePattern(Locale.getDefault(), "MMMd hh:mm a")
            holder.tvEntryDate.text = DateFormat.format(dateFormat, it)
        }
    }

    override fun getItemCount() = entries.size

    private fun getEmojiForScore(score: Int): String {
        return when (score) {
            1 -> "😫"
            2 -> "😟"
            3 -> "😐"
            4 -> "🙂"
            5 -> "😄"
            else -> "😐"
        }
    }
}
