package com.example.yugved4.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yugved4.databinding.ItemExerciseBinding
import com.example.yugved4.models.Exercise

/**
 * Adapter for displaying exercises in a list
 */
class ExerciseAdapter(
    private val exercises: List<Exercise>,
    private val onExerciseClick: (Exercise) -> Unit
) : RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val binding = ItemExerciseBinding.inflate(
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

    inner class ExerciseViewHolder(
        private val binding: ItemExerciseBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(exercise: Exercise) {
            binding.apply {
                tvExerciseName.text = exercise.name
                tvDifficulty.text = exercise.difficultyLevel
                tvTargetMuscles.text = "Target: ${exercise.targetMuscles.joinToString(", ")}"
                tvDuration.text = exercise.duration
                
                root.setOnClickListener {
                    onExerciseClick(exercise)
                }
            }
        }
    }
}
