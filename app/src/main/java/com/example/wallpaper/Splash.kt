package com.example.wallpaper

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowManager
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import com.example.bmi.Base.BaseActivity
import com.example.bmi.Base.BaseViewModel
import com.example.bmi.Utils.SystemUtils
import com.example.wallpaper.Language.LanguageStartActivity

import com.example.wallpaper.databinding.ActivitySplashBinding
import java.util.Locale

class Splash : BaseActivity<ActivitySplashBinding, BaseViewModel>() {
    private val handler = Handler(Looper.getMainLooper())
    override fun createBinding() = ActivitySplashBinding.inflate(layoutInflater)
    override fun setViewModel() = BaseViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)




    }

    override fun initView() {
        super.initView()

        val currentLanguage = Locale.getDefault().language
        SystemUtils.saveDeviceLanguage(this, currentLanguage)

        handler.postDelayed({
            val intent = Intent(this, LanguageStartActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
            }
        })
    }
}
