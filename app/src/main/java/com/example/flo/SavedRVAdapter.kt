package com.example.flo

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Delete
import com.example.flo.databinding.ItemAlbumlistBinding

class SavedRVAdapter(): RecyclerView.Adapter<SavedRVAdapter.ViewHolder>() {
    private val songs = ArrayList<Song>()


    interface MyItemClickListener{
        fun onItemClick(save : Saved)
        fun onRemoveSave(songId: Int)
    }


    private lateinit var mItemClickListener:MyItemClickListener

    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): SavedRVAdapter.ViewHolder {
        val binding: ItemAlbumlistBinding = ItemAlbumlistBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    /*fun removeItem(position: Int){
        songs.removeAt(position)
        notifyDataSetChanged()
    }*/



    override fun onBindViewHolder(holder: SavedRVAdapter.ViewHolder, position: Int) {
        holder.bind(songs[position])
        /*holder.itemView.setOnClickListener {  mItemClickListener.onItemClick(songs[position].id)}*/
        holder.binding.itemAlbumlistMoreIv.setOnClickListener{mItemClickListener.onRemoveSave(position)
            mItemClickListener.onRemoveSave(songs[position].id)
            removeSong(position)
        }
    }

    override fun getItemCount(): Int = songs.size

    @SuppressLint("NotifyDataSetChanged")
    fun addSongs(songs: ArrayList<Song>){
        this.songs.clear()
        this.songs.addAll(songs)

        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun removeSong(position: Int){
        songs.removeAt(position)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemAlbumlistBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(song: Song){
            binding.itemAlbumlistTitleTv.text = song.title
            binding.itemAlbumlistSingerTv.text = song.singer
            binding.itemAlbumlistAlbum1Iv.setImageResource(song.coverImg!!)
        }
    }
}