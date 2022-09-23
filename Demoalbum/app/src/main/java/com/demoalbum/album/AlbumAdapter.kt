package com.demoalbum.album

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.demoalbum.databinding.ItemAlbumBinding

class GalleryAdapter(
    private val list: List<AlbumData>,
    private val itemClickListener: AlbumItemClickListener
) : RecyclerView.Adapter<OrdersViewHolder>() {

    private lateinit var binding: ItemAlbumBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersViewHolder {
        binding = ItemAlbumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrdersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrdersViewHolder, position: Int) {
        holder.bind(list[position], itemClickListener)
    }

    override fun getItemCount(): Int = list.size
}

class OrdersViewHolder(
    private val binding: ItemAlbumBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        album: AlbumData,
        itemClickListener: AlbumItemClickListener
    ) {
        binding.album = album
        binding.root.setOnClickListener {
            album.id?.let {
                itemClickListener.onAlbumClick(album)
            }
        }
    }
}