package com.example.chardhamyatra.waterSports

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.chardhamyatra.R
import com.example.chardhamyatra.databinding.FragmentWaterListBinding

class waterList : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
       val binding=FragmentWaterListBinding.inflate(layoutInflater,container,false)
        binding.apply {
            nanital.setOnClickListener {
                findNavController().navigate(waterListDirections.actionWaterListToNanital1())
            }
            rishikesh.setOnClickListener {
                findNavController().navigate(waterListDirections.actionWaterListToRishikesh())
            }
        }
        return binding.root
    }

}