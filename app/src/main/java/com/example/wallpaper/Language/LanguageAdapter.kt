package com.example.wallpaper.Language

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.wallpaper.Model.LanguageModel
import com.example.wallpaper.R
import com.example.wallpaper.databinding.ItemLanguageBinding

class LanguageAdapter(
    private val context: Context,
    private var list: List<LanguageModel>,
    private val listener: (LanguageModel) -> Unit
) : RecyclerView.Adapter<LanguageAdapter.ViewHolder>() {

    private var selectedPosition = list.indexOfFirst { it.active }

    inner class ViewHolder(val binding: ItemLanguageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: LanguageModel, isSelected: Boolean) {

            binding.tvCountry.text = item.languageName
            binding.imgFrag.setImageResource(item.image)


            if (isSelected) {
                binding.root.setBackgroundResource(R.drawable.custom_item_frag_selected)
                binding.tvCountry.setTextColor(ContextCompat.getColor(context, R.color.white))
                binding.rdoButtton.setBackgroundResource(R.drawable.custom_radio_selected)
            } else {
                binding.root.setBackgroundResource(R.drawable.custom_item_frag_unselected)
                binding.tvCountry.setTextColor(ContextCompat.getColor(context, R.color.white))
                binding.rdoButtton.setBackgroundResource(R.drawable.custom_radio_unselected)
            }

            binding.root.setOnClickListener {
                val previousPosition = selectedPosition
                selectedPosition = adapterPosition

                if (previousPosition != -1) {
                    list[previousPosition].active = false
                    notifyItemChanged(previousPosition)
                }

                list[selectedPosition].active = true
                notifyItemChanged(previousPosition)
                notifyItemChanged(selectedPosition)

                listener(item)
            }

            binding.rdoButtton.setOnClickListener {
                val previousPosition = selectedPosition
                selectedPosition = adapterPosition

                if (previousPosition != -1) {
                    list[previousPosition].active = false
                    notifyItemChanged(previousPosition)
                }

                list[selectedPosition].active = true
                notifyItemChanged(previousPosition)
                notifyItemChanged(selectedPosition)

                listener(item)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemLanguageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        // Cập nhật selectedPosition ban đầu nếu item.active = true
        val isSelected = position == selectedPosition || item.active
        holder.bind(item, isSelected)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateData(newList: List<LanguageModel>) {
        list = newList
        selectedPosition = list.indexOfFirst { it.active }
        notifyDataSetChanged()
    }

    fun setSelectedLanguage(selectedLanguage: LanguageModel) {
        val previousPosition = selectedPosition
        selectedPosition = list.indexOfFirst { it == selectedLanguage }
        if (previousPosition != -1) {
            notifyItemChanged(previousPosition)
        }
        if (selectedPosition != -1) {
            notifyItemChanged(selectedPosition)
        }
    }
}