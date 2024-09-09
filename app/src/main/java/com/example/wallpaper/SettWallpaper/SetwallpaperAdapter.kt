package com.example.wallpaper.SettWallpaper

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wallpaper.Model.HdWallpaperModel
import com.example.wallpaper.R
import com.example.wallpaper.databinding.ItemSlideSetwallpaperBinding

class SetwallpaperAdapter(val context: Context, var list: MutableList<HdWallpaperModel>) : RecyclerView.Adapter<SetwallpaperAdapter.SetwallpaperViewHolder>() {

    inner class SetwallpaperViewHolder(val binding: ItemSlideSetwallpaperBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: HdWallpaperModel, context: Context) {
            Glide.with(context).load(data.imageUrl).into(binding.introSetWP)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SetwallpaperViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_slide_setwallpaper, parent, false)
        return SetwallpaperViewHolder(ItemSlideSetwallpaperBinding.bind(view))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: SetwallpaperViewHolder, position: Int) {
        holder.bind(list[position], context)
    }
}


