package com.example.wallpaper.Language

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.amazic.ads.callback.BannerCallBack
import com.amazic.ads.util.Admob
import com.example.bmi.Base.BaseActivity
import com.example.bmi.Utils.SystemUtils
import com.example.wallpaper.Home
import com.example.wallpaper.R
import com.example.wallpaper.databinding.ActivityLanguageStartBinding
import com.example.wallpaper.databinding.ActivitySettingLanguageBinding
import com.google.android.gms.ads.AdView

class SettingLanguage : BaseActivity<ActivitySettingLanguageBinding, LanguageViewModel>() {
    private var adapter: LanguageAdapter? = null
    private var adView: AdView? = null
    private var handler: Handler? = null
    private lateinit var runnable: Runnable

    override fun createBinding(): ActivitySettingLanguageBinding {
        return ActivitySettingLanguageBinding.inflate(layoutInflater)
    }

    override fun setViewModel(): LanguageViewModel {
        return LanguageViewModel()
    }



    override fun initView() {
        super.initView()

        loadCollapBanner()
        viewModel = LanguageViewModel()

        adapter = LanguageAdapter(this, emptyList()) { language ->
            viewModel.setSelectedLanguage(this, language)
        }

        binding.rcvLanguageStart.layoutManager = LinearLayoutManager(this)
        binding.rcvLanguageStart.adapter = adapter
        viewModel.languages.observe(this) { languages ->
            adapter?.updateData(languages)
        }


        viewModel.selectedLanguage.observe(this) { selectedLanguage ->
            adapter?.setSelectedLanguage(selectedLanguage)
        }
        // Initialize data
        viewModel.languageSetting(this)


        binding.imgSave.setOnClickListener() {
            val selectedLanguage = viewModel.selectedLanguage.value
            if (selectedLanguage != null) {
                viewModel.setLocale(this, selectedLanguage.code)
                SystemUtils.saveLocale(this, selectedLanguage.code)
            }
            onBackPressed()
        }

        binding.ivBack.setOnClickListener() {
            startActivity(Intent(this, Home::class.java))
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
                        .loadCollapsibleBannerFloorWithReload(this@SettingLanguage,
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
            val inflater = LayoutInflater.from(this@SettingLanguage)
            val newView = inflater.inflate(R.layout.layout_banner, binding.rlBanner, false)
            binding.rlBanner.addView(newView)
            loadCollapBanner()
        }

    override fun onBackPressed() {
        super.onBackPressed()
        // Điều hướng về màn hình trước đó thay vì thoát ứng dụng
        startActivity(Intent(this, Home::class.java))
        finish()
    }

    }






