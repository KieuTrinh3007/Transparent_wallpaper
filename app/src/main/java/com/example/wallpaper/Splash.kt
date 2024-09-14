package com.example.wallpaper

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.OnBackPressedCallback
import com.amazic.ads.callback.AdCallback
import com.amazic.ads.callback.InterCallback
import com.amazic.ads.util.Admob
import com.amazic.ads.util.AdsConsentManager
import com.amazic.ads.util.AdsSplash
import com.amazic.ads.util.manager.banner.BannerManager
import com.example.bmi.Base.BaseActivity
import com.example.bmi.Base.BaseViewModel
import com.example.bmi.Utils.SharePrefUtils
import com.example.bmi.Utils.SystemUtils
import com.example.wallpaper.Language.LanguageStartActivity
import com.example.wallpaper.ads.Constants
import com.example.wallpaper.databinding.ActivitySplashBinding
import com.google.android.gms.ads.LoadAdError
import com.google.firebase.perf.util.Constants.PREFS_NAME
import java.util.Locale


class Splash : BaseActivity<ActivitySplashBinding, BaseViewModel>() {
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var bannerManager: BannerManager
private lateinit var adsConsentManager : AdsConsentManager;
    override fun createBinding() = ActivitySplashBinding.inflate(layoutInflater)
    override fun setViewModel() = BaseViewModel()


    override fun initView() {
        super.initView()
        val currentLanguage = Locale.getDefault().language
        SystemUtils.saveDeviceLanguage(this, currentLanguage)

        SharePrefUtils.increaseAppLaunchCount(this)
        Log.d("aa", SharePrefUtils.getAppLaunchCount(this).toString())
        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val isFirstLaunch = sharedPreferences.getBoolean("FIRST_LAUNCH_KEY", true)

         adsConsentManager = AdsConsentManager(this)
        loadBanner()
        handler.postDelayed({

            adsConsentManager.requestUMP(
                !AdsConsentManager.getConsentResult(this@Splash)
            ) { result: Boolean ->
                if (result) {
                    Admob.getInstance().initAdmod(this@Splash)

                    initShowAdsSplash()
                } else {
                    startMainActivity()
                }
            }
        }, 5000)
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
            }
        })

    }

    override fun onResume() {
        super.onResume()
//        handler.postDelayed({
//        initShowAdsSplash()
//        }, 3000)

    }

    private val adCallback: AdCallback = object : AdCallback() {
        override fun onNextAction() {
            super.onNextAction()
            startMainActivity()

        }

    }
    private val interCallback: InterCallback = object : InterCallback() {

        override fun onNextAction() {
            super.onNextAction()
            Constants.timeFromStart = System.currentTimeMillis()
            startMainActivity()
        }

        override fun onAdFailedToLoad(i: LoadAdError) {
            super.onAdFailedToLoad(i)
            startMainActivity()
        }

    }


    private fun initShowAdsSplash() {
        Admob.getInstance().setTimeInterval(20000L)
        val adsSplash = AdsSplash.init(true, true, "30_70")
        adsSplash.showAdsSplash(
            this@Splash,
            listOf(getString(R.string.open_splash)),
            listOf(getString(R.string.inter_splash)),
            adCallback,
            interCallback
        )

    }

    private fun startMainActivity() {
        val intent = Intent(this, LanguageStartActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun loadBanner() {
        Admob.getInstance().loadBanner(this, getString(com.amazic.ads.R.string.ads_test_banner))
    }
}
