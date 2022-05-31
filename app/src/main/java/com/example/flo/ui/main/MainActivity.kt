package com.example.flo.ui.main

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.flo.R
import com.example.flo.SearchFragment
import com.example.flo.data.entities.Album
import com.example.flo.data.entities.Song
import com.example.flo.data.local.SongDatabase
import com.example.flo.databinding.ActivityMainBinding
import com.example.flo.ui.main.home.HomeFragment
import com.example.flo.ui.main.locker.LockerFragment
import com.example.flo.ui.main.look.LookFragment
import com.example.flo.ui.song.SongActivity
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var timer: SongActivity.Timer
    private var main: Main = Main()

    private var gson: Gson =Gson()
    private var song: Song = Song()
    private var mediaPlayer: MediaPlayer?=null





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_FLO)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        inputDummySongs()
        inputDummyAlbums()
        initBottomNavigation()

        /*val song = Song(binding.mainMiniplayerTitleTv.text.toString(), binding.mainMiniplayerSingerTv.text.toString(),0,60,false,1,"lee_sinhodeung")*///음악 재생 시작






        binding.mainPlayerCl.setOnClickListener{
           val editor = getSharedPreferences("song", MODE_PRIVATE).edit()
            editor.putInt("songId",song.id)
            editor.apply()

            val intent = Intent(this, SongActivity::class.java)
            startActivity(intent)
        }

        /*val intent = Intent(this,SongActivity::class.java)
        intent.putExtra("title", song.title)
        intent.putExtra("singer", song.singer)
        intent.putExtra("second", song.second)
        intent.putExtra("playTime", song.playTime)
        intent.putExtra("isPlaying", song.isPlaying)
        intent.putExtra("isRepeating",song.isRepeating)
        intent.putExtra("music",song.music)//음악 재생
        startActivity(intent)*/

        Log.d("MAIN/JWT_TO_SERVER",getJwt().toString())
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


    private fun getJwt():String?{
        val spf = this.getSharedPreferences("auth2",AppCompatActivity.MODE_PRIVATE)

        return spf!!.getString("jwt","")
    }





    override fun onStart() {
        super.onStart()

        val spf = getSharedPreferences("song", MODE_PRIVATE)
        val songId = spf.getInt("SongId",0)

        val songDB = SongDatabase.getInstance(this)!!

        song = if(songId ==0){
            songDB.songDao().getSong(1)
        }else{
            songDB.songDao().getSong(songId)
        }

        Log.d("song ID",song.id.toString())


               /*song = if(songJson == null){
            Song("신호등","이무진",0,60,true,1,"lee_sinhodeung")
        }else{
            gson.fromJson(songJson, Song::class.java)
        }*/
        setMiniPlayer(song)
    }

    private fun setMiniPlayer(song: Song){
        binding.mainMiniplayerTitleTv.text = song.title
        binding.mainMiniplayerSingerTv.text = song.singer
        binding.mainProgressSb.progress = (song.second*100000)/song.playTime
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()//미디어플레이어가 갖고 있던 리소스 해제
        mediaPlayer= null //미디어 플레이어 해제
    }


    private fun inputDummySongs(){
        val songDB = SongDatabase.getInstance(this)!!
        val songs = songDB.songDao().getSongs()

        if(songs.isNotEmpty()) return

        songDB.songDao().insert(
            Song(
                "Butter",
                "BTS",
                0,
                164,
                false,
                0,
                "butter",
                R.drawable.img_album_exp,
                false
            )
        )

        songDB.songDao().insert(
            Song(
                "라일락",
                "아이유",
                0,
                214,
                false,
                0,
                "lilac",
                R.drawable.img_album_exp2,
                false
            )
        )

        songDB.songDao().insert(
            Song(
                "신호등",
                "이무진",
                0,
                231,
                false,
                0,
                "lee_sinhodeung",
                R.drawable.img_album_exp3,
                false
            )
        )

        songDB.songDao().insert(
            Song(
                "Next Level",
                "aespa",
                0,
                221,
                false,
                0,
                "nextlevel",
                R.drawable.img_album_exp4,
                false
            )
        )

        songDB.songDao().insert(
            Song(
                "Weekend",
                "태연",
               0,
                233,
                false,
                0,
                "weekend",
                R.drawable.img_album_exp5,
                false
            )
        )

        songDB.songDao().insert(
            Song(
                "사랑인가 봐",
                "멜로망스",
                0,
                185,
                false,
                0,
                "love",
                R.drawable.img_album_exp6,
                false
            )
        )

        val _songs = songDB.songDao().getSongs()
        Log.d("DB data", _songs.toString())


    }

    private fun inputDummyAlbums(){
        val songDB = SongDatabase.getInstance(this)!!
        val albums = songDB.albumDao().getAlbums()

        if(albums.isNotEmpty()) return

        songDB.albumDao().insert(
            Album(
                0,
                "Butter","방탄소년단 (BTS)", R.drawable.img_album_exp
            )
        )

        songDB.albumDao().insert(
            Album(
                1,
                "IU 5th Album 'LILAC'","아이유(IU)", R.drawable.img_album_exp2
            )
        )
        songDB.albumDao().insert(
            Album(
                2,
                "이무진 1집","이무진", R.drawable.img_album_exp3
            )
        )
        songDB.albumDao().insert(
            Album(
                3,
                "iScreaM Vol.10:Next Level Remixes","에스파 (AESPA)", R.drawable.img_album_exp4
            )
        )
        songDB.albumDao().insert(
            Album(
                4,
                "Weekend","태연(Taeyeom)", R.drawable.img_album_exp5
            )
        )

        songDB.albumDao().insert(
            Album(
                5,
                "사랑인가봐","멜로망스", R.drawable.img_album_exp6
            )
        )

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

