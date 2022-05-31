package com.example.flo.ui.main.locker

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.flo.ui.song.MusicfileFragment
import com.example.flo.ui.song.SavedAlbumFragment
import com.example.flo.ui.song.SavedfileFragment

class LockerVPAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3



    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> SavedfileFragment()
            1 -> MusicfileFragment()
            else -> SavedAlbumFragment()
        }

    }

}