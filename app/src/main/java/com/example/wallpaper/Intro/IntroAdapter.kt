package com.example.wallpaper.Intro

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wallpaper.R
import com.example.wallpaper.databinding.ItemSlideIntroBinding

class IntroAdapter(val context: Context, var list: List<IntroModel>) : RecyclerView.Adapter<IntroAdapter.IntroViewHolder>()  {

    inner class IntroViewHolder(val binding: ItemSlideIntroBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: IntroModel, context: Context) {
            Glide.with(context).load(data.image).into(binding.imgIntro)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntroViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_slide_intro, parent, false)
        return IntroViewHolder(ItemSlideIntroBinding.bind(view))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: IntroViewHolder, position: Int) {
        holder.bind(list[position], context)
    }

}