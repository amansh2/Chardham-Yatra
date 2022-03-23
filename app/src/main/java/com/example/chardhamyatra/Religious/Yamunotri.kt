package com.example.chardhamyatra.Religious

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.chardhamyatra.R
import com.example.chardhamyatra.SliderAdapter
import com.example.chardhamyatra.databinding.FragmentYamunotriBinding
import com.smarteist.autoimageslider.SliderView


class Yamunotri : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val list= arrayListOf(R.drawable.yamunotriimage, R.drawable.y2, R.drawable.y3)
        val binding= FragmentYamunotriBinding.inflate(inflater,container,false)
        val adapter= SliderAdapter(list,activity!!.applicationContext)

        binding.slider.apply {
            setSliderAdapter(adapter)
            autoCycleDirection= SliderView.LAYOUT_DIRECTION_LTR
            scrollTimeInSec=2
            isAutoCycle=true
            startAutoCycle()
        }
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_item, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.stay) Stay()
        if (item.itemId == R.id.food) Food()
        if(item.itemId== R.id.location)Location()
        return super.onOptionsItemSelected(item)
    }

    private fun Location() {
        val uri =
            Uri.parse("geo:30.996942344442086, 78.45918418467778")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.setPackage("com.google.android.apps.maps")
        startActivity(intent)
    }

    private fun Food() {
        val uri =
            Uri.parse("geo:30.996942344442086, 78.45918418467778?q=restaurants")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.setPackage("com.google.android.apps.maps")
        startActivity(intent)
    }

    private fun Stay() {
        val uri =
            Uri.parse("geo:30.996942344442086, 78.45918418467778?q=hotels")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.setPackage("com.google.android.apps.maps")
        startActivity(intent)
    }

}