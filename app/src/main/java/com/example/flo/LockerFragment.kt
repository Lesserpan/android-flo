package com.example.flo

import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.flo.databinding.ActivityMainBinding
import com.example.flo.databinding.FragmentLockerBinding
import com.example.flo.databinding.FragmentSavedfileBinding
import com.google.android.material.tabs.TabLayoutMediator

class LockerFragment : Fragment() {
    lateinit var binding: FragmentLockerBinding
    private val information = arrayListOf("저장한곡", "음악파일","저장앨범")


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLockerBinding.inflate(inflater, container, false)

        val lockerAdapter = LockerVPAdapter(this)
        binding.lockerContentVp.adapter = lockerAdapter
        TabLayoutMediator(binding.lockerContentTb, binding.lockerContentVp){
                tab, position ->
            tab.text = information[position]
        }.attach()


        binding.lockerLoginTv.setOnClickListener {
            startActivity(Intent(activity, LoginActivity::class.java))
        }

        val songDB = SongDatabase.getInstance(requireContext())!!
        val userId = getJwt()
        val likedAlbums = songDB.albumDao().getLikedAlbums(userId)

        return binding.root

    }

    override fun onStart() {
        super.onStart()

        initViews()
    }


    private fun initViews(){
        val jwt:Int=getJwt()
        if (jwt==0) {
            binding.lockerLoginTv.text = "로그인"

            binding.lockerLoginTv.setOnClickListener {
                startActivity(Intent(activity, LoginActivity::class.java))
            }
        }else{
            binding.lockerLoginTv.text="로그아웃"

            binding.lockerLoginTv.setOnClickListener {
                //로그아웃 진행
                logout()
                startActivity(Intent(activity, MainActivity::class.java))
            }
        }
    }


    private fun getJwt():Int{
        val spf = activity?.getSharedPreferences("auth",AppCompatActivity.MODE_PRIVATE)

        return spf!!.getInt("jwt",0)
    }


    private fun logout(){
        val spf=activity?.getSharedPreferences("auth",AppCompatActivity.MODE_PRIVATE)
        val editor = spf!!.edit()

        editor.remove("jwt")
        editor.apply()
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




