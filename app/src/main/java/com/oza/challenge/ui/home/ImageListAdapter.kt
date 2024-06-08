package com.oza.challenge.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.oza.challenge.databinding.ItemImageBinding
import com.oza.challenge.model.ImageData


class ImageListAdapter(private val clickListener: ItemClickListener) :
    ListAdapter<ImageData, ImageListAdapter.ImageViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemImageBinding.inflate(inflater, parent, false)
        return ImageViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = getItem(position)
        holder.bind(image)
    }

    class ImageViewHolder(
        private val binding: ItemImageBinding,
        private val clickListener: ItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(image: ImageData) {
            // Bind the image data to the item view
            binding.image = image
            binding.executePendingBindings()
            itemView.setOnClickListener {
                clickListener.onItemClick(image)
            }
        }

    }

    private class DiffCallback : DiffUtil.ItemCallback<ImageData>() {
        override fun areItemsTheSame(oldItem: ImageData, newItem: ImageData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ImageData, newItem: ImageData): Boolean {
            return oldItem == newItem
        }
    }

}
