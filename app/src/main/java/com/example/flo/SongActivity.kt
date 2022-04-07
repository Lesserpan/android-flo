package com.example.flo

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {
    lateinit var binding : ActivitySongBinding
    lateinit var song :Song             //seekbarthread 만드는 곳
    lateinit var timer: Timer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)//setContentView=>xml에 있는 것들을 가져와서 마음대로 쓸 수 있다.괄호 안에는 마음대로 쓸 id를 적어줘야 한다.

        initSong()
        setPlayer(song)

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
            setLikeStatus(true)
        }
        binding.songLikeIv.setOnClickListener{
            setLikeStatus(false)
        }
        binding.songPlayerUnlikeOffIv.setOnClickListener{
            setLikeonoffStatus(true)
        }
        binding.songPlayerLikeOnIv.setOnClickListener{
            setLikeonoffStatus(false)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        timer.interrupt()
    }

    private fun initSong(){                                                         //seekbar thread 만드는 곳
        if(intent.hasExtra("title")&& intent.hasExtra("singer")){
            song=Song(
                intent.getStringExtra("title")!!,
                intent.getStringExtra("singer")!!,
                intent.getIntExtra("second", 0),
                intent.getIntExtra("playTime", 0),
                intent.getBooleanExtra("isPlaying", false)

            )
        }
        startTimer()
    }


    private fun setPlayer(song: Song){
        binding.songMusicTitleTv.text = intent.getStringExtra("title")!!
        binding.songSingerNameTv.text = intent.getStringExtra("singer")!!
        binding.songStartTimeTv.text = String.format("%02d:%02d",song.second/60, song.second%60)
        binding.songEndTimeTv.text = String.format("%02d:%02d",song.playTime/60, song.playTime%60)
        binding.songProgressSb.progress = (song.second * 1000 / song.playTime)

        setPlayerStatus(song.isPlaying)
    }

    private fun startTimer(){
        timer = Timer(song.playTime,song.isPlaying)
        timer.start()
    }

    private fun setPlayerStatus(isPlaying : Boolean){
        song.isPlaying = isPlaying
        timer.isPlaying = isPlaying
        if(isPlaying){
            binding.songMiniplayerIv.visibility= View.GONE
            binding.songPauseIv.visibility=View.VISIBLE
        }
        else{
            binding.songMiniplayerIv.visibility= View.VISIBLE
            binding.songPauseIv.visibility=View.GONE
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
    private fun setLikeStatus(isLiking : Boolean){
        if(isLiking){
            binding.songLikeIv.visibility= View.VISIBLE
            binding.songDislikeIv.visibility=View.GONE
        }
        else{
            binding.songLikeIv.visibility= View.GONE
            binding.songDislikeIv.visibility=View.VISIBLE
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
