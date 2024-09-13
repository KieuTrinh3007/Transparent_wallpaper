package com.example.wallpaper

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import com.amazic.ads.callback.InterCallback
import com.amazic.ads.util.manager.native_ad.NativeBuilder
import com.amazic.ads.util.manager.native_ad.NativeManager
import com.example.bmi.Base.BaseActivity
import com.example.bmi.Base.BaseViewModel
import com.example.wallpaper.ads.InterManage
import com.example.wallpaper.databinding.ActivityDoneScreenBinding
import com.google.android.gms.ads.LoadAdError

class DoneScreen : BaseActivity<ActivityDoneScreenBinding, BaseViewModel>() {

    private var nativeManager: NativeManager? = null

    override fun createBinding() = ActivityDoneScreenBinding.inflate(layoutInflater)
    override fun setViewModel() = BaseViewModel()


    override fun initView() {
        super.initView()
        InterManage.loadInterAll(this@DoneScreen)
        try {

            val list: MutableList<String> = ArrayList()
            list.add(getString(R.string.native_language))
            val builder = NativeBuilder(
                this, binding.nativeAds,
                R.layout.ads_native_shimmer_done, R.layout.ads_native_layout_done
            )
            builder.setListIdAd(list)
            nativeManager = NativeManager(this, this, builder)


        } catch (e: Exception) {
            e.printStackTrace()
            binding.nativeAds.removeAllViews()
            binding.nativeAds.setVisibility(View.INVISIBLE)
        }

        binding.btnDone.setOnClickListener(){

            InterManage.showInterAll(this@DoneScreen, object : InterCallback() {
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


        }
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
            }
        })
    }
    private fun startActivity() {
        startActivity(Intent(this, Home::class.java))
        finish()
    }

}