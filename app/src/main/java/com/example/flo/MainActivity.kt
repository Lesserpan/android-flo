package com.example.flo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.example.flo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_FLO)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBottomNavigation()

        val song = Song(binding.mainMiniplayerTitleTv.text.toString(), binding.mainMiniplayerSingerTv.text.toString(),0,60,false)




        binding.mainPlayerCl.setOnClickListener{
            val intent = Intent(this,SongActivity::class.java)
            intent.putExtra("title", song.title)
            intent.putExtra("singer", song.singer)
            intent.putExtra("second", song.second)
            intent.putExtra("playTime", song.playTime)
            intent.putExtra("isPlaying", song.isPlaying)
            startActivity(intent)
        }
    }








    private fun initBottomNavigation(){

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, HomeFragment())
            .commitAllowingStateLoss()

        binding.mainBnv.setOnItemSelectedListener{ item ->
            when (item.itemId) {

                R.id.homeFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, HomeFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.lookFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LookFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.searchFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, SearchFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.lockerFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LockerFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }




    }

    /*private lateinit var iv1:ImageView
    private lateinit var iv2:ImageView
    private lateinit var iv3:ImageView

    private lateinit var viewPager2: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.fragment_home)
        iv1=findViewById(R.id.iv1)
        iv2=findViewById(R.id.iv2)
        iv3=findViewById(R.id.iv3)

        val images= listOf(R.drawable.shape_circle_white,R.drawable.shape_circle_white,R.drawable.shape_circle_white)
        val adapter=WhereVPAdapter(images)
        viewPager2.adapter=adapter*/



    /*viewPager2.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            changeColor()
            super.onPageScrolled(position, positionOffset, positionOffsetPixels)
        }

        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
        }

        override fun onPageScrollStateChanged(state: Int) {
            super.onPageScrollStateChanged(state)
            changeColor()
        }

    })


}*/

    /*fun changeColor()
    {
        when(viewPager2.currentItem){
            0->
            {
                iv1.setBackgroundColor(applicationContext.resources.getcolor)
                iv2.setBackgroundColor(applicationContext.resources.getColor(R.color.white))
                iv3.setBackgroundColor(applicationContext.resources.getColor(R.color.white))
            }
            1->
            {
                iv1.setBackgroundColor(applicationContext.resources.getColor(R.color.white))
                iv2.setBackgroundColor(applicationContext.resources.getColor(R.color.black))
                iv3.setBackgroundColor(applicationContext.resources.getColor(R.color.white))

            }
            2->
            {
                iv1.setBackgroundColor(applicationContext.resources.getColor(R.color.white))
                iv2.setBackgroundColor(applicationContext.resources.getColor(R.color.white))
                iv3.setBackgroundColor(applicationContext.resources.getColor(R.color.black))

            }
        }
    }*/

}

