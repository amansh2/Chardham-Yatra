package com.example.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chardhamyatra.R
import com.example.chardhamyatra.databinding.ItemGuideRvBinding
import com.example.model.Guide

class GuidesListAdapter(val list:ArrayList<Guide>, val context: Context, val listener:OnItemClickedListener):RecyclerView.Adapter<GuidesListAdapter.GuidesViewHolder>() {
    class GuidesViewHolder(val binding:ItemGuideRvBinding):RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuidesViewHolder {
        return GuidesViewHolder(ItemGuideRvBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun getItemCount()= list.size

    override fun onBindViewHolder(holder: GuidesViewHolder, position: Int) {
        val model=list[position]
        holder.binding.apply {
            guideName.text=model.name
            users.text="("+model.users.size.toString()+")"
            price.text="₹"+model.pricePerDay.toString()+"/Day"
            ratingBar.rating=model.Rating
            Glide.with(context).load(model.image).centerCrop().placeholder(R.drawable.ic_board_placeholder).into(ivBoardImage)
            root.setOnClickListener {
                listener.onItemClicked(model)
            }
        }
    }
}

interface OnItemClickedListener{
    fun onItemClicked(model:Guide)
}