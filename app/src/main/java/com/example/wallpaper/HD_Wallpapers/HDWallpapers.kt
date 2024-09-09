package com.example.wallpaper.HD_Wallpapers

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.bmi.Base.BaseActivity
import com.example.wallpaper.Home
import com.example.wallpaper.Model.HdWallpaperModel
import com.example.wallpaper.R
import com.example.wallpaper.SettWallpaper.SetwallpaperActivity
import com.example.wallpaper.databinding.ActivityHdWallpapersBinding

class HDWallpapers : BaseActivity<ActivityHdWallpapersBinding, HdWallPaperViewModel>() {
    private var list: MutableList<HdWallpaperModel> = ArrayList()
    private var adapter: HdWallpaperAdapter? = null

    override fun createBinding(): ActivityHdWallpapersBinding {
        return ActivityHdWallpapersBinding.inflate(layoutInflater)
    }

    override fun setViewModel(): HdWallPaperViewModel {
        return HdWallPaperViewModel()
    }

    override fun viewModel() {
    }

    override fun initView() {
        super.initView()

        supportActionBar?.hide()

        binding.ivBack.setOnClickListener {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }



        list.add(HdWallpaperModel(1, R.drawable.hd1))
        list.add(HdWallpaperModel(2, R.drawable.hd8))
        list.add(HdWallpaperModel(3, R.drawable.hd3))
        list.add(HdWallpaperModel(4, R.drawable.hd2))
        list.add(HdWallpaperModel(5, R.drawable.hd4))
        list.add(HdWallpaperModel(6, R.drawable.hd6))
        list.add(HdWallpaperModel(7, R.drawable.hd3))
        list.add(HdWallpaperModel(8, R.drawable.hd1))
        list.add(HdWallpaperModel(9, R.drawable.hd7))
        list.add(HdWallpaperModel(10, R.drawable.hd2))



        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
//        val space = resources.getDimensionPixelSize(R.dimen.space)
//
//        binding.rcvHdWallpaper.addItemDecoration(SpacesItemDecoration(space))
        binding.rcvHdWallpaper.layoutManager = layoutManager

        adapter = HdWallpaperAdapter(list, this)
        binding.rcvHdWallpaper.adapter = adapter


        adapter?.setOnItemClick { position ->
            val intent = Intent(this@HDWallpapers, SetwallpaperActivity::class.java)
            intent.putExtra("position", position)
            startActivity(intent)
        }
    }
    }

    class SpacesItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            outRect.left = space
            outRect.right = space
            outRect.bottom = space

            if (parent.getChildLayoutPosition(view) < 2) {
                outRect.top = space
            }
        }
    }

    class SquareImageView(context: Context, attrs: AttributeSet) : AppCompatImageView(context, attrs) {
        override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
            super.onMeasure(widthMeasureSpec, widthMeasureSpec)
        }
    }
