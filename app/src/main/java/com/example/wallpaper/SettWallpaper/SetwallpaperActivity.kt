package com.example.wallpaper.SettWallpaper


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.WallpaperManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PixelFormat
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.bmi.Base.BaseActivity
import com.example.wallpaper.Model.HdWallpaperModel
import com.example.wallpaper.R
import com.example.wallpaper.databinding.ActivitySetwallpaperBinding
import com.example.wallpaper.databinding.DialogChooseScreenBinding
import com.example.wallpaper.DoneScreen
import java.io.IOException
import kotlin.math.abs

class SetwallpaperActivity : BaseActivity<ActivitySetwallpaperBinding, SetwallpaperViewModel>() {

    private var setWPAdapter: SetwallpaperAdapter? = null
    private val list: MutableList<HdWallpaperModel> = mutableListOf()
    private var index = 0

    override fun createBinding(): ActivitySetwallpaperBinding {
        return ActivitySetwallpaperBinding.inflate(layoutInflater)
    }

    override fun setViewModel(): SetwallpaperViewModel {
        return SetwallpaperViewModel()
    }


    override fun initView() {
        super.initView()
        supportActionBar?.hide()
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        list.add(HdWallpaperModel(1, R.drawable.hd1))
        list.add(HdWallpaperModel(2, R.drawable.hd8))
        list.add(HdWallpaperModel(3, R.drawable.hd3))
        list.add(HdWallpaperModel(4, R.drawable.hd2))
        list.add(HdWallpaperModel(5, R.drawable.hd4))
        list.add(HdWallpaperModel(6, R.drawable.hd6))
        list.add(HdWallpaperModel(7, R.drawable.hd3))
        list.add(HdWallpaperModel(8, R.drawable.hd1))
        list.add(HdWallpaperModel(9, R.drawable.hd8))
        list.add(HdWallpaperModel(10, R.drawable.hd2))


        setWPAdapter = SetwallpaperAdapter(this, list)
        binding.viewPagerSetWallpaper.setPageTransformer(setUpTransformer())
        binding.viewPagerSetWallpaper.setClipToPadding(false)
        binding.viewPagerSetWallpaper.setClipChildren(false)
        binding.viewPagerSetWallpaper.offscreenPageLimit = 3
        binding.viewPagerSetWallpaper.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        binding.viewPagerSetWallpaper.adapter = setWPAdapter

        val intent = intent
        val position = intent.getIntExtra("position", 0)
        index = position
        Log.d("position", "onCreate: $position")
        binding.viewPagerSetWallpaper.setCurrentItem(position)

        binding.btnSetWallpaper.setOnClickListener { v -> showDialogChoose() }
        setWPAdapter?.notifyDataSetChanged()
        binding.imgBackT.setOnClickListener {
            onBackPressed()
        }

        // dot
        binding.viewPagerSetWallpaper.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                when (position) {
                    0 -> {
                        dotDefault()
                        binding.dot1.setImageResource(R.drawable.dot_not_select)
                    }

                    1 -> {
                        dotDefault()
                        binding.dot2.setImageResource(R.drawable.dot_not_select)
                    }

                    2 -> {
                        dotDefault()
                        binding.dot3.setImageResource(R.drawable.dot_not_select)
                    }

                    3 -> {
                        dotDefault()
                        binding.dot1.setImageResource(R.drawable.dot_not_select)
                    }

                    4 -> {
                        dotDefault()
                        binding.dot2.setImageResource(R.drawable.dot_not_select)
                    }

                    5 -> {
                        dotDefault()
                        binding.dot3.setImageResource(R.drawable.dot_not_select)
                    }

                    6 -> {
                        dotDefault()
                        binding.dot1.setImageResource(R.drawable.dot_not_select)
                    }

                    7 -> {
                        dotDefault()
                        binding.dot2.setImageResource(R.drawable.dot_not_select)
                    }

                    8 -> {
                        dotDefault()
                        binding.dot3.setImageResource(R.drawable.dot_not_select)
                    }

                    9 -> {
                        dotDefault()
                        binding.dot1.setImageResource(R.drawable.dot_not_select)
                    }
                }

            }
        })

    }


    override fun onDestroy() {
        super.onDestroy()
        // Xóa view overlay khi activity bị destroy
        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
//        windowManager.removeViewImmediate(overlayView)
    }


    private fun setUpTransformer(): CompositePageTransformer {
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.14f
        }
        return transformer
    }



    private fun dotDefault() {
        binding.dot1.setImageResource(R.drawable.dot_selectwp)
        binding.dot2.setImageResource(R.drawable.dot_selectwp)
        binding.dot3.setImageResource(R.drawable.dot_selectwp)

    }


    private fun showDialogChoose() {
        val layoutInflater = layoutInflater
        val dialogChooseScreenBinding: DialogChooseScreenBinding = DialogChooseScreenBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(this)

        builder.setView(dialogChooseScreenBinding.getRoot())
        val dialog = builder.create()


        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.show()

        dialogChooseScreenBinding.cstLockScreen.setOnClickListener { v ->
            dialogChooseScreenBinding.imgradioLockScreen.setVisibility(View.VISIBLE)
            dialogChooseScreenBinding.imgradioHomeScreen.setVisibility(View.GONE)
            dialogChooseScreenBinding.imgradioBothScreen.setVisibility(View.GONE)
        }

        dialogChooseScreenBinding.cstHomeScreen.setOnClickListener { v ->
            dialogChooseScreenBinding.imgradioLockScreen.setVisibility(View.GONE)
            dialogChooseScreenBinding.imgradioHomeScreen.setVisibility(View.VISIBLE)
            dialogChooseScreenBinding.imgradioBothScreen.setVisibility(View.GONE)
        }

        dialogChooseScreenBinding.cstBothScreen.setOnClickListener { v ->
            dialogChooseScreenBinding.imgradioLockScreen.setVisibility(View.GONE)
            dialogChooseScreenBinding.imgradioHomeScreen.setVisibility(View.GONE)
            dialogChooseScreenBinding.imgradioBothScreen.setVisibility(View.VISIBLE)
        }

        dialogChooseScreenBinding.btnSelectOptions.setOnClickListener { v ->
            val currentItem: Int = binding.viewPagerSetWallpaper.getCurrentItem()
            val currentModel: HdWallpaperModel = list!!.get(currentItem)

            val drawable =
                resources.getDrawable(currentModel.imageUrl) as BitmapDrawable
            val bitmap = drawable.bitmap
            val wallpaperManager =
                WallpaperManager.getInstance(this)
            try {
                if (dialogChooseScreenBinding.imgradioLockScreen.getVisibility() === View.VISIBLE) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK)
                        Toast.makeText(
                            this,
                            R.string.lock_screen,
                            Toast.LENGTH_SHORT
                        ).show()
                        startActivity(
                            Intent(
                                this,
                                DoneScreen::class.java
                            )
                        )
                    } else {
                        Toast.makeText(
                            this,
                            "Setting lock screen wallpaper requires Android 7.0 or higher",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else if (dialogChooseScreenBinding.imgradioHomeScreen.getVisibility() === View.VISIBLE) {
                    wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_SYSTEM)
                    startActivity(
                        Intent(
                            this,
                            DoneScreen::class.java
                        )
                    )
                } else if (dialogChooseScreenBinding.imgradioBothScreen.getVisibility() === View.VISIBLE) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        wallpaperManager.setBitmap(
                            bitmap,
                            null,
                            true,
                            WallpaperManager.FLAG_LOCK or WallpaperManager.FLAG_SYSTEM
                        )
                        startActivity(
                            Intent(
                                this,
                                DoneScreen::class.java
                            )
                        )
                    } else {
                        wallpaperManager.setBitmap(bitmap)
                        Toast.makeText(
                            this,
                            "Home screen wallpaper set successfully (setting lock screen requires Android 7.0 or higher)",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this,
                        "Vui lòng chọn màn hình",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                dialog.dismiss()
            } catch (e: IOException) {
                Toast.makeText(
                    this,
                    "Failed to set wallpaper",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

}