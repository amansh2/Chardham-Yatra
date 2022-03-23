package com.example.chardhamyatra.HillStations

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.chardhamyatra.R
import com.example.chardhamyatra.databinding.FragmentHillsListBinding


class HillsList : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
     val binding=FragmentHillsListBinding.inflate(layoutInflater,container,false)
        binding.apply {
            dehradun.setOnClickListener {
                findNavController().navigate(HillsListDirections.actionHillsListToDehradun())
            }
            masoori.setOnClickListener {
                findNavController().navigate(HillsListDirections.actionHillsListToMasoori())
            }
            nanital.setOnClickListener {
                findNavController().navigate(HillsListDirections.actionHillsListToNanital())
            }
        }
        return binding.root
    }

}