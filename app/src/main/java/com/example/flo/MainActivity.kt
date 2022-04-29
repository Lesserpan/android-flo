package com.example.flo

import android.content.Intent
import android.media.MediaPlayer
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
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var timer: SongActivity.Timer
    private var main: Main =Main()

    private var gson: Gson =Gson()
    private var song: Song =Song()
    private var mediaPlayer: MediaPlayer?=null





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_FLO)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initBottomNavigation()

        val song = Song(binding.mainMiniplayerTitleTv.text.toString(), binding.mainMiniplayerSingerTv.text.toString(),0,60,false,1,"lee_sinhodeung")//음악 재생 시작






        binding.mainPlayerCl.setOnClickListener{
            val intent = Intent(this,SongActivity::class.java)
            intent.putExtra("title", song.title)
            intent.putExtra("singer", song.singer)
            intent.putExtra("second", song.second)
            intent.putExtra("playTime", song.playTime)
            intent.putExtra("isPlaying", song.isPlaying)
            intent.putExtra("isRepeating",song.isRepeating)
            intent.putExtra("music",song.music)//음악 재생
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

    private fun setMiniPlayer(song: Song){
        binding.mainMiniplayerTitleTv.text = song.title
        binding.mainMiniplayerSingerTv.text = song.singer
        binding.mainProgressSb.progress = (song.second*100000)/song.playTime
    }



    override fun onStart() {
        super.onStart()
        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val songJson = sharedPreferences.getString("songData",null)
        val music = resources.getIdentifier(song.music, "raw", this.packageName)
        mediaPlayer = MediaPlayer.create(this, R.raw.lee_sinhodeung)



       fun setMiniplayerStatus(isPlaying : Boolean){
           main.isPlaying = isPlaying
           timer.isPlaying = isPlaying
            if(isPlaying){
                binding.mainMiniplayerIv.visibility= View.GONE
                binding.mainPauseIv.visibility=View.VISIBLE

                mediaPlayer?.start()//음악 재생
            }
            else{
                binding.mainMiniplayerIv.visibility= View.VISIBLE
                binding.mainPauseIv.visibility=View.GONE
                if (mediaPlayer?.isPlaying == true){//음악 재생
                    mediaPlayer?.pause()
                }
            }
        }

        binding.mainMiniplayerIv.setOnClickListener{ setMiniplayerStatus(true)}
        binding.mainPauseIv.setOnClickListener { setMiniplayerStatus(false)}

        song = if(songJson == null){
            Song("신호등","이무진",0,60,true,1,"lee_sinhodeung")
        }else{
            gson.fromJson(songJson, Song::class.java)
        }
        setMiniPlayer(song)
    }
    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()//미디어플레이어가 갖고 있던 리소스 해제
        mediaPlayer= null //미디어 플레이어 해제
    }
}



/*    <ImageView
    android:id="@+id/main_miniplayer_iv"
    android:layout_width="40dp"
    android:layout_height="40dp"
    android:src="@drawable/btn_miniplayer_play"/>

    <ImageView
    android:id="@+id/main_pause_iv"
    android:layout_width="40dp"
    android:layout_height="40dp"
    android:visibility="gone"
    android:src="@drawable/btn_miniplay_pause"/>*/
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

