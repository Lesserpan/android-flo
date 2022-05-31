package com.example.flo.ui.song

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.ui.main.Main
import com.example.flo.ui.main.MainActivity
import com.example.flo.R
import com.example.flo.data.entities.Song
import com.example.flo.data.local.SongDatabase
import com.example.flo.databinding.ActivitySongBinding
import com.google.gson.Gson

class SongActivity : AppCompatActivity() {
    lateinit var binding : ActivitySongBinding
    /*lateinit var song :Song */            //seekbarthread 만드는 곳
    private var main: Main = Main()
    lateinit var timer: Timer
    private var mediaPlayer:MediaPlayer?=null
    private var gson : Gson = Gson()
    var boolpl : Int = 2


    val songs = arrayListOf<Song>()
    lateinit var songDB: SongDatabase
    var nowPos = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)//setContentView=>xml에 있는 것들을 가져와서 마음대로 쓸 수 있다.괄호 안에는 마음대로 쓸 id를 적어줘야 한다.


        initPlayList()
        initSong()
        initClickListener()


    }

    override fun onPause() {
        super.onPause()
       /* setPlayerStatus(false)*///사용자가 포커스를 잃었을 때 음악이 중지 된다.

        songs[nowPos].second = ((binding.songProgressSb.progress*songs[nowPos].playTime)/100)/1000
        songs[nowPos].isPlaying=false

        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)//이 두줄은 앱이 꺼져도 시간을 기억할 수 있게 해주는 것들//sharedpreferences는 앱 안에서 간단한 것들 기억
        val editor = sharedPreferences.edit() //에디터
        editor.putInt("songId", songs[nowPos].id)

        editor.apply()

        binding.songBtnDownIb.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)

            intent.putExtra("second", songs[nowPos].second)
            intent.putExtra("isPlaying", songs[nowPos].isPlaying)
            startActivity(intent)
        }

    }
    /*editor.putString("title",song.title)
    editor.putString("title",song.singer)*/


    override fun onDestroy() {
        super.onDestroy()
        timer.interrupt()
        mediaPlayer?.release()//미디어플레이어가 갖고 있던 리소스 해제
        mediaPlayer= null //미디어 플레이어 해제
    }


    private fun initPlayList(){
        songDB = SongDatabase.getInstance(this)!!
        songs.addAll(songDB.songDao().getSongs())
    }
    private fun initSong(){                                                         //seekbar thread 만드는 곳
        /*f(intent.hasExtra("title")&& intent.hasExtra("singer")){
            song=Song(
                intent.getStringExtra("title")!!,
                intent.getStringExtra("singer")!!,
                intent.getIntExtra("second", 0),
                intent.getIntExtra("playTime", 0),
                intent.getBooleanExtra("isPlaying", false),
                intent.getIntExtra("isRepeating",1),
                intent.getStringExtra("music")!!

            )
        }*/
        val spf = getSharedPreferences("song", MODE_PRIVATE)
        val songId = spf.getInt("songId", 0)

        nowPos = getPlayingSongPosition(songId)

        Log.d("now Song ID",songs[nowPos].id.toString())
        startTimer()
        setPlayer(songs[nowPos])
    }

    private fun initClickListener(){
        binding.songBtnDownIb.setOnClickListener{
            finish()
        }
        binding.songMiniplayerIv.setOnClickListener{
            setPlayerStatus(true)
        }
        binding.songPauseIv.setOnClickListener{
            setPlayerStatus(false)
        }
        binding.songRepeatInactiveIv.setOnClickListener{
            setRepeaterStatus(0)
        }
        binding.songRepeatActiveIv.setOnClickListener{
            setRepeaterStatus(1)
        }
        binding.songOnesongrepeatActiveIv.setOnClickListener{
            setRepeaterStatus(2)
        }
        binding.songRandomInactiveIv.setOnClickListener{
            setRandomerStatus(true)
        }
        binding.songRandomActiveIv.setOnClickListener{
            setRandomerStatus(false)
        }
        binding.songDislikeIv.setOnClickListener{
            setLike(songs[nowPos].isLike)
        }
        binding.songPlayerUnlikeOffIv.setOnClickListener{
            setLikeonoffStatus(true)
        }
        binding.songPlayerLikeOnIv.setOnClickListener{
            setLikeonoffStatus(false)
        }
        binding.songMiniplayerNextIv.setOnClickListener {
            moveSong(+1)
        }
        binding.songMiniplayerPreviousIv.setOnClickListener {
            moveSong(-1)
        }

    }


    private fun setLike(isLike:Boolean){
        songs[nowPos].isLike=!isLike
        songDB.songDao().updateIsLikeById(!isLike,songs[nowPos].id)
        if(!isLike){
            binding.songDislikeIv.setImageResource(R.drawable.ic_my_like_on)

        }else{
            binding.songDislikeIv.setImageResource(R.drawable.ic_my_like_off)

        }
    }

    private fun moveSong(direct: Int){
        if(nowPos + direct<0){
            Toast.makeText(this, "first song", Toast.LENGTH_SHORT).show()
            return
        }

        if(nowPos + direct>=songs.size){
            Toast.makeText(this, "last song", Toast.LENGTH_SHORT).show()
            return
        }

        nowPos +=direct

        timer.interrupt()
        startTimer()

        mediaPlayer?.release()//미디어플레이어가 갖고 있던 리소스 해제
        mediaPlayer= null //미디어 플레이어 해제

        setPlayer(songs[nowPos])
    }


    private fun getPlayingSongPosition(songId: Int): Int{
        for(i in 0 until songs.size){
            if(songs[i].id == songId){
                return i
            }
        }
        return 0
    }

    private fun setPlayer(song: Song){
        binding.songMusicTitleTv.text = song.title
        binding.songSingerNameTv.text = song.singer
        binding.songStartTimeTv.text = String.format("%02d:%02d",song.second/60, song.second%60)
        binding.songEndTimeTv.text = String.format("%02d:%02d",song.playTime/60, song.playTime%60)
        binding.songSinhoIv.setImageResource(song.coverImg!!)
        binding.songProgressSb.progress = (song.second * 1000 / song.playTime)
        val music = resources.getIdentifier(song.music, "raw", this.packageName)
        mediaPlayer = MediaPlayer.create(this,music)//음악 재생
        setPlayerStatus(song.isPlaying)


        if(song.isLike){
            binding.songDislikeIv.setImageResource(R.drawable.ic_my_like_on)

        }else{
            binding.songDislikeIv.setImageResource(R.drawable.ic_my_like_off)
        }
        /*binding.songDislikeIv.visibility= View.GONE
            binding.songLikeIv.visibility=View.VISIBLE*/

        /*binding.songLikeIv.visibility= View.GONE
            binding.songDislikeIv.visibility=View.VISIBLE*/



    }

    private fun startTimer(){
        timer = Timer(songs[nowPos].playTime,songs[nowPos].isPlaying)
        timer.start()
    }

    private fun setPlayerStatus(isPlaying : Boolean){
        songs[nowPos].isPlaying = isPlaying
        timer.isPlaying = isPlaying

        if(isPlaying){
            binding.songMiniplayerIv.visibility= View.GONE
            binding.songPauseIv.visibility=View.VISIBLE
            /*boolpl = 1*/
            mediaPlayer?.start()//음악 재생
        }
        else{
            binding.songMiniplayerIv.visibility= View.VISIBLE
            binding.songPauseIv.visibility=View.GONE
            if (mediaPlayer?.isPlaying == true){//음악 재생
                /*boolpl = 2*/
                mediaPlayer?.pause()
            }
        }

    }
    private fun setRepeaterStatus(isRepeating : Int){
        if(isRepeating==0){
            binding.songRepeatActiveIv.visibility= View.VISIBLE
            binding.songRepeatInactiveIv.visibility=View.GONE
            binding.songOnesongrepeatActiveIv.visibility=View.GONE
        }
        else if(isRepeating==1){
            binding.songRepeatActiveIv.visibility= View.GONE
            binding.songRepeatInactiveIv.visibility=View.GONE
            binding.songOnesongrepeatActiveIv.visibility=View.VISIBLE

        }
        else{
            binding.songRepeatActiveIv.visibility= View.GONE
            binding.songRepeatInactiveIv.visibility=View.VISIBLE
            binding.songOnesongrepeatActiveIv.visibility=View.GONE

        }
    }


    private fun setLikeonoffStatus(isLikingonoff : Boolean){
        if(isLikingonoff){
            binding.songPlayerLikeOnIv.visibility= View.VISIBLE
            binding.songPlayerUnlikeOffIv.visibility=View.GONE
        }
        else{
            binding.songPlayerLikeOnIv.visibility= View.GONE
            binding.songPlayerUnlikeOffIv.visibility=View.VISIBLE
        }
    }

    private fun setRandomerStatus(isRandomering : Boolean){
        if(isRandomering){
            binding.songRandomActiveIv.visibility= View.VISIBLE
            binding.songRandomInactiveIv.visibility=View.GONE
        }
        else{
            binding.songRandomActiveIv.visibility= View.GONE
            binding.songRandomInactiveIv.visibility=View.VISIBLE
        }
    }
    inner class Timer(private val playTime: Int, var isPlaying: Boolean=true): Thread(){

        private var second : Int = 0
        private var mills: Float= 0f

        override fun run(){
            super.run()
            try{
                while (true){
                    if(second >= playTime){
                        break
                    }
                    if (isPlaying){
                        Thread.sleep(50)
                        mills +=50

                        runOnUiThread {
                            binding.songProgressSb.progress = ((mills/playTime)*100).toInt()
                        }
                        if(mills%1000==0f){
                            runOnUiThread {
                                binding.songStartTimeTv.text = String.format("%02d:%02d",second/60, second%60)
                            }
                            second++
                        }
                    }
                }

            }catch (e: InterruptedException){
                Log.d("song","쓰레드가 죽었습니다.${e.message}")

            }
        }

    }
}


/*
binding.songDislikeIv.setOnClickListener{
    setLikeStatus(true)
}
binding.songLikeIv.setOnClickListener{
    setLikeStatus(false)
}*///함수 초기화

/*
fun setLikeStatus(isLiking : Boolean){
    if(isLiking){
        binding.songLikeIv.visibility= View.VISIBLE
        binding.songDislikeIv.visibility=View.GONE
    }
    else{
        binding.songLikeIv.visibility= View.GONE
        binding.songDislikeIv.visibility=View.VISIBLE
    }
}*///이미지 뷰를 바꾸면서 버튼처럼 보이게 해주는 함수



/*
try{
    while (true){
        if(second >= playTime){
            break
        }
        if (isPlaying){
            Thread.sleep(50)
            mills +=50

            runOnUiThread {
                binding.songProgressSb.progress = ((mills/playTime)*100).toInt()
            }
            if(mills%1000==0f){
                runOnUiThread {
                    binding.songStartTimeTv.text = String.format("%02d:%02d",second/60, second%60)
                }
                second++
            }
        }
    }

}catch (e: InterruptedException){
    Log.d("song","쓰레드가 죽었습니다.${e.message}")

}*/
