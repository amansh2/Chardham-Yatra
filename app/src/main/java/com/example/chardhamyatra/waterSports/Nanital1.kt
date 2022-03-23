package com.example.chardhamyatra.waterSports

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.chardhamyatra.R
import com.example.chardhamyatra.SliderAdapter
import com.example.chardhamyatra.databinding.FragmentNanital1Binding
import com.example.chardhamyatra.databinding.FragmentNanitalBinding
import com.smarteist.autoimageslider.SliderView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Nanital1.newInstance] factory method to
 * create an instance of this fragment.
 */
class Nanital1 : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val list= arrayListOf(R.drawable.nanital1, R.drawable.nanital2)
        val binding= FragmentNanital1Binding.inflate(inflater,container,false)
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
            Uri.parse("geo:29.389026912819265, 79.45389659157708")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.setPackage("com.google.android.apps.maps")
        startActivity(intent)
    }

    private fun Food() {
        val uri =
            Uri.parse("geo:29.389026912819265, 79.45389659157708?q=restaurants")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.setPackage("com.google.android.apps.maps")
        startActivity(intent)
    }

    private fun Stay() {
        val uri =
            Uri.parse("geo:29.389026912819265, 79.45389659157708?q=hotels")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.setPackage("com.google.android.apps.maps")
        startActivity(intent)
    }
}