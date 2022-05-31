package com.example.flo.ui.main.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.flo.*
import com.example.flo.data.entities.Album
import com.example.flo.data.local.SongDatabase
import com.example.flo.databinding.FragmentHomeBinding
import com.example.flo.ui.banner.BannerFragment
import com.example.flo.ui.banner.BannerVPAdapter
import com.example.flo.ui.main.MainActivity
import com.example.flo.ui.main.MainVPAdapter
import com.example.flo.ui.main.album.AlbumFragment
import com.example.flo.ui.main.album.AlbumRVAdapter
import com.example.flo.ui.pannel.PannelBlackFragment
import com.example.flo.ui.pannel.PannelBlueFragment
import com.example.flo.ui.pannel.PannelMintFragment
import com.google.gson.Gson

private lateinit var songDB : SongDatabase

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    private val albumDatas = ArrayList<Album>()


    private var currentPage:Int=0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)


        //데이터 리스트 생성 더미 데이터
        /*albumDatas.apply {
            add(Album("Lilac","아이유 (IU)", R.drawable.img_album_exp2))
            add(Album("Butter","방탄소년단 (BTS)", R.drawable.img_album_exp))
            add(Album("Sinhodeung", "이무진", R.drawable.img_album_exp3))
            add(Album("Next level", "에스파(aespa)", R.drawable.img_album_exp4))
            add(Album("Weekend", "태연(Taeyeon)", R.drawable.img_album_exp5))
            add(Album("사랑인가봐", "멜로망스", R.drawable.img_album_exp6))
        }*/
        //데이터 리스트 생성 더미 데이터
        songDB = SongDatabase.getInstance(requireContext())!!
        albumDatas.addAll(songDB.albumDao().getAlbums())



        // 더미데이터랑 Adapter 연결
        val albumRVAdapter = AlbumRVAdapter(albumDatas)
        //리사이클러뷰에 어댑터를 연결
        binding.homeTodayMusicAlbumRv.adapter = albumRVAdapter
        binding.homeTodayMusicAlbumRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)



        albumRVAdapter.setMyItemClickListener(object: AlbumRVAdapter.MyItemClickListener{
            override fun onItemClick(album: Album) /*ClickListener 연결하기*/ {
                (context as MainActivity).supportFragmentManager.beginTransaction()/*ClickListener 연결하기*/
                    .replace(R.id.main_frm, AlbumFragment()/*ClickListener 연결하기*/ .apply {
                        arguments = Bundle().apply {
                            val gson = Gson()
                            val albumJson = gson.toJson(album)
                            putString("album",albumJson)
                        }
                    })
                    .commitAllowingStateLoss()//ClickListener 연결하기
            }

            override fun onRemoveaAlbum(position: Int) {
                albumRVAdapter.removeItem(position)
            }

        })//ClickListener 연결하기

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


        return binding.root


    }

    /*override fun onStart() {
        super.onStart()

        lateinit var homesong :Song
        fun inithomeSong() {
            homesong = Song(
                intent.getIntExtra("second", 0),
                intent.getBooleanExtra("isPlaying", false)
            )
        }

    }*/

    private fun changeAlbumFragment(album: Album) {
        (context as MainActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, AlbumFragment().apply {
                arguments = Bundle().apply {
                    val gson = Gson()
                    val albumJson = gson.toJson(album)
                    putString("album", albumJson)
                }
            })
            .commitAllowingStateLoss()
    }



}


