package com.example.flo

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.flo.databinding.FragmentAllofcountryBinding
import com.example.flo.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.concurrent.thread

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding



    private val information = arrayListOf("종합", "국내", "해외")
    private var currentPage:Int=0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)




        val bannerAdapter = BannerVPAdapter(this)
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))
        binding.homeBannerVp.adapter=bannerAdapter
        binding.homeBannerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.homeBannerVp.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                binding.homeCi.selectDot(position)
            }
        })
        binding.homeCi.createDotPanel(2,R.drawable.indicator_dot_off,R.drawable.indicator_dot_on,0)


        val mainAdapter = MainVPAdapter(this)
        mainAdapter.addFragment(PannelBlackFragment())
        mainAdapter.addFragment(PannelMintFragment())
        mainAdapter.addFragment(PannelBlueFragment())
        mainAdapter.addFragment(PannelBlackFragment())
        mainAdapter.addFragment(PannelMintFragment())
        mainAdapter.addFragment(PannelBlueFragment())
        binding.homePannelVp.adapter= mainAdapter
        binding.homePannelVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        val child = binding.homePannelVp.getChildAt(0)
        (child as? RecyclerView)?.overScrollMode=View.OVER_SCROLL_NEVER
        binding.homePannelVp.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                binding.pannelCi.selectDot(position)
            }
        })
        binding.pannelCi.createDotPanel(3,R.drawable.indicator_dot_off,R.drawable.indicator_dot_on,0)


        fun setPage(){

            if(currentPage==0){
                binding.homePannelVp.setCurrentItem(currentPage,true)
                currentPage+=1
            }
            else if(currentPage==1) {
                binding.homePannelVp.setCurrentItem(currentPage, true)
                currentPage += 1
            }
            else{
                binding.homePannelVp.setCurrentItem(currentPage, true)
                currentPage -= 2
            }
        }

        val handler=Handler(Looper.getMainLooper()){
            setPage()
            true
        }

        class PagerRunnable:Runnable{
            override fun run(){
                while(true){
                    try {
                        Thread.sleep(2000)
                        handler.sendEmptyMessage(0)
                    }catch(e: InterruptedException){
                        Log.d("interrupt","interrupt 발생")
                    }
                }
            }
        }

        val thread= Thread(PagerRunnable())
        thread.start()


        val whereAdapter = WhereVPAdapter(this)
        binding.homeContentVp.adapter = whereAdapter
        TabLayoutMediator(binding.homeContentTb, binding.homeContentVp){
                tab, position ->
            tab.text = information[position]
        }.attach()

        return binding.root


    }




}


