package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentAlbumBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson

class AlbumFragment : Fragment(){

    lateinit var binding: FragmentAlbumBinding
    private var gson: Gson = Gson()

    private val information = arrayListOf("수록곡", "상세정보", "영상")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentAlbumBinding.inflate(inflater,container,false)

        val albumJson = arguments?.getString("album")
        val album = gson.fromJson(albumJson, Album::class.java)
        setInit(album)


        val albumlistJson = arguments?.getString("album")
        val saved = gson.fromJson(albumlistJson, Saved::class.java)
        setInit(saved)

        binding.albumBackbtnIv.setOnClickListener{
            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_frm,HomeFragment()).commitAllowingStateLoss()//fragment이동하게 해주는 코드 문장

        }

        val albumAdapter = AlbumVPAdapter(this)
        binding.albumContentVp.adapter = albumAdapter
        TabLayoutMediator(binding.albumContentTb, binding.albumContentVp){
                tab, position ->
            tab.text = information[position]
        }.attach()//tablayout 제목 넣어주기

        return binding.root
    }

    private fun setInit(album: Album){
        binding.albumAlbumIv.setImageResource(album.coverImg!!)
        binding.albumTitleTv.text=album.title.toString()
        binding.albumSingerTv.text=album.title.toString()
    }

    private fun setInit(saved: Saved){
        binding.albumAlbumIv.setImageResource(saved.coverImg!!)
        binding.albumTitleTv.text=saved.title.toString()
        binding.albumSingerTv.text=saved.title.toString()
    }
}