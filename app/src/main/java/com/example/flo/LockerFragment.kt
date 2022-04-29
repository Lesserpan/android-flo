package com.example.flo

import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.flo.databinding.ActivityMainBinding
import com.example.flo.databinding.FragmentLockerBinding
import com.example.flo.databinding.FragmentSavedfileBinding
import com.google.android.material.tabs.TabLayoutMediator

class LockerFragment : Fragment() {


    lateinit var binding: FragmentLockerBinding

    private val information = arrayListOf("저장한 곡", "음악파일")


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLockerBinding.inflate(inflater, container, false)
        val lockerAdapter = LockerVPAdapter(this)
        binding.lockerContentVp.adapter = lockerAdapter
        TabLayoutMediator(binding.lockerContentTb, binding.lockerContentVp) { tab, position ->
            tab.text = information[position]
        }.attach()

        return binding.root

    }
}
    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = (DataBindingUtil.setContentView(
            this, R.layout.activity_main) as ActivityMainBinding)
            .apply {
                lifecycleOwner = this@LockerFragment
            }

        val lockerVPAdapter = LockerVPAdapter(this)
            .apply{
                addFragment(SavedfileFragment())
                addFragment(MusicfileFragment())
            }

        val viewPager:ViewPager2=binding.pager.apply {
            adpater = pagerAdapter
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    Log.d("ViewPagerFragment", "Page ${position + 1}")
                }
            })
        }

        TabLayoutMediator(binding.tabLayout, viewPager) { tab, position ->
            tab.text = "Tab ${position+1}"
        }.attach()
    }*/




