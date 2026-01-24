package com.example.yugved4.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.yugved4.databinding.ItemWorkoutBinding
import com.example.yugved4.models.Workout

/**
 * Adapter for displaying workout items in a horizontal RecyclerView
 */
class WorkoutAdapter(
    private val workouts: List<Workout>
) : RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val binding = ItemWorkoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return WorkoutViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        holder.bind(workouts[position])
    }

    override fun getItemCount(): Int = workouts.size

    inner class WorkoutViewHolder(
        private val binding: ItemWorkoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(workout: Workout) {
            with(binding) {
                // Set workout data
                tvWorkoutTitle.text = workout.title
                tvDuration.text = workout.duration
                ivWorkoutImage.setImageResource(workout.imageResId)

                // Set play button click listener
                fabPlay.setOnClickListener {
                    Toast.makeText(
                        it.context,
                        "Starting ${workout.title}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                // Set card click listener
                cardWorkout.setOnClickListener {
                    Toast.makeText(
                        it.context,
                        "Opening ${workout.title} details",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}
