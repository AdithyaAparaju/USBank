package com.demoalbum.photos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.demoalbum.databinding.ItemPhotoBinding

class PhotosAdapter(
    private val list: List<PhotosData>,
    private val itemClickListener: PhotoItemClickListener
) : RecyclerView.Adapter<OrdersViewHolder>() {

    private lateinit var binding: ItemPhotoBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersViewHolder {
        binding = ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrdersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrdersViewHolder, position: Int) {
        holder.bind(list[position], itemClickListener)
    }

    override fun getItemCount(): Int = list.size
}

class OrdersViewHolder(
    private val binding: ItemPhotoBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        photo: PhotosData,
        itemClickListener: PhotoItemClickListener
    ) {
        binding.photo = photo
        binding.root.setOnClickListener {
            photo.id?.let {
                itemClickListener.onPhotoClick(photo)
            }
        }
    }
}