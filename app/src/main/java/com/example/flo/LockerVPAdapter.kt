package com.example.flo

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2

class LockerVPAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2



    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> SavedfileFragment()
            else -> MusicfileFragment()
        }

    }

}