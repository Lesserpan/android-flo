package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentInsidecountryBinding


class InsidecountryFragment : Fragment() {

    lateinit var binding: FragmentInsidecountryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInsidecountryBinding.inflate(inflater, container,false)

        return binding.root
    }
}