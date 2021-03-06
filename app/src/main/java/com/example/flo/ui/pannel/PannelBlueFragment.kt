package com.example.flo.ui.pannel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentBannerBinding
import com.example.flo.databinding.FragmentPannelblackBinding
import com.example.flo.databinding.FragmentPannelblueBinding

class PannelBlueFragment : Fragment() {

    lateinit var binding : FragmentPannelblueBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPannelblueBinding.inflate(inflater, container, false)
        return binding.root
    }
}