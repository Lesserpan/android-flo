package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo.databinding.ActivityMainBinding.inflate
import com.example.flo.databinding.ActivitySongBinding.inflate
import com.example.flo.databinding.FragmentSavedfileBinding


class SavedfileFragment : Fragment() {

    lateinit var binding: FragmentSavedfileBinding
    private var SavedDatas = ArrayList<Saved>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSavedfileBinding.inflate(inflater, container,false)

        SavedDatas.apply {
            add(Saved("Lilac","아이유 (IU)", R.drawable.img_album_exp2))
            add(Saved("Butter","방탄소년단 (BTS)", R.drawable.img_album_exp))
            add(Saved("Sinhodeung", "이무진", R.drawable.img_album_exp3))
            add(Saved("Next level", "에스파(aespa)", R.drawable.img_album_exp4))
            add(Saved("Weekend", "태연(Taeyeon)", R.drawable.img_album_exp5))
            add(Saved("사랑인가봐", "멜로망스", R.drawable.img_album_exp6))
        }

        val savedRVAdapter = SavedRVAdapter(SavedDatas)
        binding.lockerMusicListRv.adapter = savedRVAdapter
        binding.lockerMusicListRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        return binding.root
    }


}