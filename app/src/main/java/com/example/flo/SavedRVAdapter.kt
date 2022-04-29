package com.example.flo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.databinding.ItemAlbumlistBinding

class SavedRVAdapter(private val savedList: ArrayList<Saved>): RecyclerView.Adapter<SavedRVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): SavedRVAdapter.ViewHolder {
        val binding: ItemAlbumlistBinding = ItemAlbumlistBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SavedRVAdapter.ViewHolder, position: Int) {
        holder.bind(savedList[position])
    }

    override fun getItemCount(): Int = savedList.size

    inner class ViewHolder(val binding: ItemAlbumlistBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(album: Saved){
            binding.itemAlbumlistTitleTv.text = album.title
            binding.itemAlbumlistSingerTv.text = album.singer
            binding.itemAlbumlistAlbum1Iv.setImageResource(album.coverImg!!)
        }
    }
}