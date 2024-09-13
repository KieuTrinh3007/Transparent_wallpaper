package com.example.wallpaper.HD_Wallpapers

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wallpaper.Model.HdWallpaperModel
import com.example.wallpaper.databinding.ItemRcvHdwallpapersBinding


class HdWallpaperAdapter( private val list: MutableList<HdWallpaperModel>?, private val context: Context
) :
    RecyclerView.Adapter<HdWallpaperAdapter.ViewHolder>() {
//    list: MutableList<HdWallpaperModel>? = list

    private var mListener: OnclickItem? = null

    fun setOnItemClick(listener: OnclickItem?) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HdWallpaperAdapter.ViewHolder {
        val binding: ItemRcvHdwallpapersBinding =
            ItemRcvHdwallpapersBinding.inflate(LayoutInflater.from(parent.context), parent, false);

        return ViewHolder(binding);
    }


    override fun onBindViewHolder(
        holder: HdWallpaperAdapter.ViewHolder,
        position: Int
    ) {
        list?.get(position)?.let { holder.binding.imgHdwallpaper.setImageResource(it.imageUrl) }

        holder.itemView.setOnClickListener(View.OnClickListener {
            if (mListener != null) {
                mListener!!.Onclick(holder.getAdapterPosition())
            }
        })
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    inner class ViewHolder(binding: ItemRcvHdwallpapersBinding) :
        RecyclerView.ViewHolder(binding.getRoot()) {
        var binding: ItemRcvHdwallpapersBinding = binding
    }


}