package com.example.wallpaper.Intro

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.amazic.ads.callback.InterCallback
import com.amazic.ads.util.Admob
import com.amazic.ads.util.manager.native_ad.NativeBuilder
import com.amazic.ads.util.manager.native_ad.NativeManager
import com.example.bmi.Base.BaseActivity
import com.example.wallpaper.Home
import com.example.wallpaper.R
import com.example.wallpaper.ads.InterManage
import com.example.wallpaper.databinding.ActivityIntroBinding
import com.example.wallpaper.databinding.ActivityLanguageStartBinding
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd

class IntroActivity : BaseActivity<ActivityIntroBinding, IntroViewModel>() {

    private var introAdapter: IntroAdapter? = null

    private var nativeManager: NativeManager? = null
    var interAll: InterstitialAd? = null
    override fun createBinding(): ActivityIntroBinding {
        return ActivityIntroBinding.inflate(layoutInflater)
    }

    override fun setViewModel(): IntroViewModel {
        return IntroViewModel()
    }


    override fun viewModel() {
        super.viewModel()
        viewModel.getData()
        viewModel.listIntro.observe(this) {
            introAdapter?.list = it
            introAdapter = IntroAdapter(this, it)
            binding.viewPagerIntro.offscreenPageLimit = 5
            binding.viewPagerIntro.adapter = introAdapter
            introAdapter?.notifyDataSetChanged()
        }
    }

    override fun initView() {
        super.initView()
        InterManage.loadInterAll(this@IntroActivity)
        try {

            val list: MutableList<String> = ArrayList()
            list.add(getString(R.string.native_language))
            val builder = NativeBuilder(
                this, binding.nativeAds,
                R.layout.ads_native_shimer_intro, R.layout.ads_native_layout_intro
            )
            builder.setListIdAd(list)
            nativeManager = NativeManager(this, this, builder)


        } catch (e: Exception) {
            e.printStackTrace()
            binding.nativeAds.removeAllViews()
            binding.nativeAds.setVisibility(View.INVISIBLE)
        }

        binding.viewPagerIntro.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> {

                        binding.tvTitle.text = getString(R.string.transparency)
                        binding.tvContent.text = getString(R.string.reveal_your_beautiful)
                        dotDefault()
                        binding.dot1.setImageResource(R.drawable.dot_select)
                    }

                    1 -> {
                        binding.tvTitle.text = getString(R.string.crystal)
                        binding.tvContent.text = getString(R.string.clarity_and_style)
                        dotDefault()
                        binding.dot2.setImageResource(R.drawable.dot_select)
                    }

                    2 -> {
                        binding.tvTitle.text = getString(R.string.clearVision)
                        binding.tvContent.text = getString(R.string.Elevate_your)
                        dotDefault()
                        binding.dot3.setImageResource(R.drawable.dot_select)
                    }

                }
            }
        })

        binding.tvNext.setOnClickListener {

            if (binding.viewPagerIntro.currentItem >= 2) {

                InterManage.showInterAll(this@IntroActivity, object : InterCallback() {
                    override fun onNextAction() {
                        super.onNextAction()
                        Log.d("TAG", "onNextAction")
                        startActivity()
                    }

                    override fun onAdFailedToLoad(i: LoadAdError?) {
                        super.onAdFailedToLoad(i)
                        Log.d("TAG", "onAdFailedToLoad")
                    }
                })

            } else {
                binding.viewPagerIntro.currentItem = binding.viewPagerIntro.currentItem + 1
            }
        }
    }

    private fun dotDefault() {
        binding.dot1.setImageResource(R.drawable.dot_not_select)
        binding.dot2.setImageResource(R.drawable.dot_not_select)
        binding.dot3.setImageResource(R.drawable.dot_not_select)

    }


    private fun startActivity() {
        startActivity(Intent(this, Home::class.java))
        finish()
    }
}