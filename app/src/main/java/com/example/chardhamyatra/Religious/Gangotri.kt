package com.example.chardhamyatra.Religious

import android.content.Intent
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.chardhamyatra.R
import com.example.chardhamyatra.SliderAdapter
import com.example.chardhamyatra.databinding.FragmentGangotriBinding
import com.smarteist.autoimageslider.SliderView


class Gangotri : Fragment() {

    //private lateinit var binding: FragmentGangotriBinding
//private lateinit var adapter: SliderAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val list =
            arrayListOf<Int>(R.drawable.gangotriimage, R.drawable.g2, R.drawable.g3, R.drawable.g4)
        val binding = FragmentGangotriBinding.inflate(inflater, container, false)
        val adapter = SliderAdapter(list, activity!!.applicationContext)

        binding.slider.apply {
            setSliderAdapter(adapter)
            autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR
            scrollTimeInSec = 2
            isAutoCycle = true
            startAutoCycle()
        }
        setHasOptionsMenu(true)
        return binding.root
    }

    fun Stay() {
        val uri =
            Uri.parse("geo:30.995185719930433, 78.93982529143062?q=hotels")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.setPackage("com.google.android.apps.maps")
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_item, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.stay) Stay()
        if (item.itemId == R.id.food) Food()
        if(item.itemId==R.id.location) Location()
        return super.onOptionsItemSelected(item)
    }

    private fun Location() {
        val uri =
            Uri.parse("geo:30.995185719930433, 78.93982529143062")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.setPackage("com.google.android.apps.maps")
        startActivity(intent)
    }

    private fun Food() {
        val uri =
            Uri.parse("geo:30.995185719930433, 78.93982529143062?q=restaurants")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.setPackage("com.google.android.apps.maps")
        startActivity(intent)
    }

}