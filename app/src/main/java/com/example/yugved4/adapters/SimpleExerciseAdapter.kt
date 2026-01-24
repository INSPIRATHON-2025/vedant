package com.example.yugved4.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yugved4.database.DatabaseHelper
import com.example.yugved4.databinding.ItemExerciseSimpleBinding

/**
 * Adapter for displaying simple exercise list
 */
class SimpleExerciseAdapter(
    private val exercises: List<DatabaseHelper.GymExercise>,
    private val onItemClick: (DatabaseHelper.GymExercise) -> Unit
) : RecyclerView.Adapter<SimpleExerciseAdapter.ExerciseViewHolder>() {

    inner class ExerciseViewHolder(private val binding: ItemExerciseSimpleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(exercise: DatabaseHelper.GymExercise) {
            binding.tvExerciseName.text = exercise.name
            binding.ivExerciseThumbnail.setImageResource(exercise.thumbnailResource)

            binding.root.setOnClickListener {
                onItemClick(exercise)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val binding = ItemExerciseSimpleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ExerciseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        holder.bind(exercises[position])
    }

    override fun getItemCount(): Int = exercises.size
}
