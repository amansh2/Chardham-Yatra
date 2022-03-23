package com.example.chardhamyatra

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.chardhamyatra.databinding.FragmentChoosingPageBinding

// TODO: Rename parameter arguments, choose names that match

class choosing_page : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding=FragmentChoosingPageBinding.inflate(layoutInflater)
        binding.apply {
            charDhams.setOnClickListener {
                findNavController().navigate(choosing_pageDirections.actionChoosingPageToCharDhams())
            }
            waterSports.setOnClickListener {
                findNavController().navigate(choosing_pageDirections.actionChoosingPageToWaterList())
            }
            hillStations.setOnClickListener {
                findNavController().navigate(choosing_pageDirections.actionChoosingPageToHillsList())
            }
            trekking.setOnClickListener {
                findNavController().navigate(choosing_pageDirections.actionChoosingPageToKuaripass())
            }
        }
        return binding.root

    }

}