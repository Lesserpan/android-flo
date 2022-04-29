package com.example.flo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.databinding.ItemAlbumBinding

class AlbumRVAdapter(private val albumList: ArrayList<Album>): RecyclerView.Adapter<AlbumRVAdapter.ViewHolder>(){

    interface MyItemClickListener{
        fun onItemClick(album: Album)
        fun onRemoveaAlbum(position: Int)
    }//ClickListener 연결하기

    private lateinit var mItemClickListener:MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }//ClickListener 연결하기

    fun addItem(album: Album){
        albumList.add(album)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int){
        albumList.removeAt(position)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): AlbumRVAdapter.ViewHolder {
        val binding: ItemAlbumBinding = ItemAlbumBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumRVAdapter.ViewHolder, position: Int) {
        holder.bind(albumList[position])
        holder.binding.itemAlbumCoverImgIv.setOnClickListener {  mItemClickListener.onItemClick(albumList[position])}//ClickListener 연결하기
        holder.binding.itemAlbumTitleTv.setOnClickListener{mItemClickListener.onRemoveaAlbum(position)}
    }

    override fun getItemCount(): Int = albumList.size

    inner class ViewHolder(val binding:ItemAlbumBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(album: Album){
            binding.itemAlbumTitleTv.text = album.title
            binding.itemAlbumSingerTv.text = album.singer
            binding.itemAlbumCoverImgIv.setImageResource(album.coverImg!!)
        }

    }
}