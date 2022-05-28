package com.example.flo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo.databinding.FragmentLockerSavedalbumBinding
import com.example.flo.databinding.FragmentSavedfileBinding
import com.google.gson.Gson


class SavedAlbumFragment : Fragment() {

    lateinit var binding: FragmentLockerSavedalbumBinding
    lateinit var albumDB: SongDatabase


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLockerSavedalbumBinding.inflate(inflater, container,false)

        albumDB = SongDatabase.getInstance(requireContext())!!

        return binding.root
    }

    override fun onStart(){
        super.onStart()
        initRecyclerview()
    }

    private fun initRecyclerview(){
        binding.lockerAlbumListRv.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL, false)

        val albumRVAdapter = AlbumLockerRVAdapter()

        albumRVAdapter.setMyItemClickListener(object : AlbumLockerRVAdapter.MyItemClickListener{
            override fun onRemoveSong(songId: Int) {
                albumDB.albumDao().getLikedAlbums(getJwt())
            }

        })

        binding.lockerAlbumListRv.adapter = albumRVAdapter

        albumRVAdapter.addAlbums(albumDB.albumDao().getLikedAlbums(getJwt())as ArrayList)

    }
    private fun getJwt() : Int {
        val spf = activity?.getSharedPreferences("auth" , AppCompatActivity.MODE_PRIVATE)
        val jwt = spf!!.getInt("jwt", 0)
        Log.d("MAIN_ACT/GET_JWT", "jwt_token: $jwt")

        return jwt
    }
}



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