package com.example.yugved4.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yugved4.databinding.ItemMuscleGroupBinding
import com.example.yugved4.models.MuscleGroup

/**
 * Adapter for displaying muscle groups in a grid layout
 */
class MuscleGroupAdapter(
    private val muscleGroups: List<MuscleGroup>,
    private val onItemClick: (MuscleGroup) -> Unit
) : RecyclerView.Adapter<MuscleGroupAdapter.MuscleGroupViewHolder>() {

    inner class MuscleGroupViewHolder(private val binding: ItemMuscleGroupBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(muscleGroup: MuscleGroup) {
            binding.tvMuscleName.text = muscleGroup.name
            binding.ivMuscleIcon.setImageResource(muscleGroup.imageResource)

            binding.root.setOnClickListener {
                onItemClick(muscleGroup)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MuscleGroupViewHolder {
        val binding = ItemMuscleGroupBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MuscleGroupViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MuscleGroupViewHolder, position: Int) {
        holder.bind(muscleGroups[position])
    }

    override fun getItemCount(): Int = muscleGroups.size
}
