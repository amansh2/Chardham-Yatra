package com.example.chardhamyatra.Religious

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.chardhamyatra.R
import com.example.chardhamyatra.databinding.FragmentCharDhamsBinding


class CharDhams : Fragment(R.layout.fragment_char_dhams) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentCharDhamsBinding.bind(view)
        binding.apply {
            gangotri.setOnClickListener {
                findNavController().navigate(CharDhamsDirections.actionCharDhamsToGangotri())
            }
            yamunotri.setOnClickListener {
                findNavController().navigate(CharDhamsDirections.actionCharDhamsToYamunotri())
            }
            badrinath.setOnClickListener {
                findNavController().navigate(CharDhamsDirections.actionCharDhamsToBadrinath())
            }
            kedarnath.setOnClickListener {
                findNavController().navigate(CharDhamsDirections.actionCharDhamsToKedarnath())
            }
        }
    }
}