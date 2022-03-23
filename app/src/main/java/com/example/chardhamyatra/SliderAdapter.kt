package com.example.chardhamyatra

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.chardhamyatra.databinding.ItemImageBinding
import com.smarteist.autoimageslider.SliderViewAdapter

class SliderAdapter(private val list: ArrayList<Int>,private val context: Context) :
    SliderViewAdapter<SliderAdapter.SliderViewHolder>() {
    class SliderViewHolder(var binding: ItemImageBinding) :
        SliderViewAdapter.ViewHolder(binding.root) {

    }

    override fun getCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?): SliderAdapter.SliderViewHolder {
        return SliderViewHolder(ItemImageBinding.inflate(LayoutInflater.from(parent?.context),parent,false))
    }

    override fun onBindViewHolder(viewHolder: SliderAdapter.SliderViewHolder?, position: Int) {
       viewHolder?.binding?.myimage?.setImageResource(list[position])
    }
}