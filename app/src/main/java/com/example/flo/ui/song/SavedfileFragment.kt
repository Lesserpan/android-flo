package com.example.flo.ui.song

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo.data.entities.Song
import com.example.flo.data.local.SongDatabase
import com.example.flo.databinding.FragmentSavedfileBinding


class SavedfileFragment : Fragment() {

    lateinit var binding: FragmentSavedfileBinding
    lateinit var songDB: SongDatabase


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSavedfileBinding.inflate(inflater, container,false)

        songDB = SongDatabase.getInstance(requireContext())!!

        /*val savedRVAdapter = SavedRVAdapter(SavedDatas)
        binding.lockerMusicListRv.adapter = savedRVAdapter
        binding.lockerMusicListRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        savedRVAdapter.setMyItemClickListener(object: SavedRVAdapter.MyItemClickListener{
            override fun onItemClick(save: Saved) {
                (context as MainActivity).supportFragmentManager.beginTransaction()*//*ClickListener 연결하기*//*
                    .replace(R.id.main_frm,AlbumFragment().apply {
                        arguments = Bundle().apply {
                            val gson = Gson()
                            val albumJson = gson.toJson(save)
                            putString("album",albumJson)
                        }
                    })
                    .commitAllowingStateLoss()//ClickListener 연결하기

            }

            override fun onRemoveSave(position: Int) {
                savedRVAdapter.removeItem(position)
            }
        })
*/


        return binding.root
    }

    override fun onStart(){
        super.onStart()
        initRecyclerview()
    }

    private fun initRecyclerview(){
        binding.lockerMusicListRv.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL, false)

        val songRVAdapter = SavedRVAdapter()

        songRVAdapter.setMyItemClickListener(object : SavedRVAdapter.MyItemClickListener {
            override fun onItemClick(save: Saved) {
                TODO("Not yet implemented")
            }

            override fun onRemoveSave(songId: Int) {
               songDB.songDao().updateIsLikeById(false,songId)
            }

        })

        binding.lockerMusicListRv.adapter = songRVAdapter

        songRVAdapter.addSongs(songDB.songDao().getLikedSongs(true)as ArrayList<Song>)

    }




}