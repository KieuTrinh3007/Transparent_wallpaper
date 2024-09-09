package com.example.wallpaper

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import com.example.bmi.Base.BaseActivity
import com.example.bmi.Base.BaseViewModel
import com.example.wallpaper.databinding.ActivityDoneScreenBinding

class DoneScreen : BaseActivity<ActivityDoneScreenBinding, BaseViewModel>() {

    override fun createBinding() = ActivityDoneScreenBinding.inflate(layoutInflater)
    override fun setViewModel() = BaseViewModel()
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(binding.root)
//
//        // Ẩn ActionBar
//        supportActionBar?.hide()
//
//        // Tắt cả Status Bar và Navigation Bar
//        window.decorView.systemUiVisibility = (
//                View.SYSTEM_UI_FLAG_IMMERSIVE
//                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                        or View.SYSTEM_UI_FLAG_FULLSCREEN
//                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                )
//
//        // Đảm bảo ẩn cả ActionBar khi ẩn Status Bar và Navigation Bar
//        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
//
//
//
//
//
//    }

    override fun initView() {
        super.initView()

        binding.btnDone.setOnClickListener(){
            startActivity(Intent(this, Home::class.java))
            finish()
        }
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
            }
        })
    }
}