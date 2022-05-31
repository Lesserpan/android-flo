package com.example.flo.ui.pannel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentBannerBinding
import com.example.flo.databinding.FragmentPannelblackBinding
import com.example.flo.databinding.FragmentPannelmintBinding

class PannelMintFragment : Fragment() {

    lateinit var binding : FragmentPannelmintBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPannelmintBinding.inflate(inflater, container, false)
        return binding.root
    }
}