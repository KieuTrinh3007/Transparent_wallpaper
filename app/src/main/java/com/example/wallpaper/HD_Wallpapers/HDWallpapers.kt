package com.example.wallpaper.HD_Wallpapers

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.amazic.ads.callback.BannerCallBack
import com.amazic.ads.callback.InterCallback
import com.amazic.ads.util.Admob
import com.example.bmi.Base.BaseActivity
import com.example.wallpaper.Home
import com.example.wallpaper.Model.HdWallpaperModel
import com.example.wallpaper.R
import com.example.wallpaper.SettWallpaper.SetwallpaperActivity
import com.example.wallpaper.ads.InterManage
import com.example.wallpaper.databinding.ActivityHdWallpapersBinding
import com.google.ads.mediation.admob.AdMobAdapter
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError

class HDWallpapers : BaseActivity<ActivityHdWallpapersBinding, HdWallPaperViewModel>() {
    private var list: MutableList<HdWallpaperModel> = ArrayList()
    private var adapter: HdWallpaperAdapter? = null
    private var adView: AdView? = null
    private var handler: Handler? = null
    private lateinit var runnable: Runnable


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

        InterManage.loadInterAll(this@HDWallpapers)
        loadCollapBanner()

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

        binding.rcvHdWallpaper.layoutManager = layoutManager

        adapter = HdWallpaperAdapter(list, this)
        binding.rcvHdWallpaper.adapter = adapter


        adapter?.setOnItemClick { position ->

            InterManage.showInterAll(this@HDWallpapers, object : InterCallback() {
                override fun onNextAction() {
                    super.onNextAction()
                    Log.d("TAG", "onNextAction")
                    val intent = Intent(this@HDWallpapers, SetwallpaperActivity::class.java)
                    intent.putExtra("position", position)
                    startActivity(intent)
                }

                override fun onAdFailedToLoad(i: LoadAdError?) {
                    super.onAdFailedToLoad(i)
                    Log.d("TAG", "onAdFailedToLoad")
                }
            })

        }


    }



    override fun onDestroy() {
        super.onDestroy()
        handler?.removeCallbacks(runnable)
    }

    override fun onResume() {
        super.onResume()
        handler?.removeCallbacks(runnable)
        reloadCollapsibleNew()
//        adapter?.reloadNative()
        Log.d("TAG123", "onResume: ")
    }


    fun loadCollapBanner() {
        val collapseBanner = "ca-app-pub-3940256099942544/2014213617"
        binding.rlBanner.visibility = View.VISIBLE
        val collapseBannerList: MutableList<String> = ArrayList()
        collapseBannerList.add(collapseBanner)
        handler = Handler(Looper.getMainLooper())
        runnable = Runnable {
            adView?.destroy()
            adView = null
            handler?.postDelayed({
                adView = Admob.getInstance()
                    .loadCollapsibleBannerFloorWithReload(this@HDWallpapers,
                        collapseBannerList,
                        "bottom",
                        object : BannerCallBack() {
                            override fun onAdLoadSuccess() {
                                super.onAdLoadSuccess()
                                handler?.postDelayed(runnable, 20000L)
                            }
                        })
            }, 1000)
        }
        handler?.post(runnable)
    }

    fun reloadCollapsibleNew() {
        //reload Collapsible Banner
        binding.rlBanner.removeAllViews()
        val inflater = LayoutInflater.from(this@HDWallpapers)
        val newView = inflater.inflate(R.layout.layout_banner, binding.rlBanner, false)
        binding.rlBanner.addView(newView)
        loadCollapBanner()
    }

    }
    class SquareImageView(context: Context, attrs: AttributeSet) : AppCompatImageView(context, attrs) {
        override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
            super.onMeasure(widthMeasureSpec, widthMeasureSpec)
        }
    }




