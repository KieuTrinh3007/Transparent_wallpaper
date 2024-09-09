package com.example.wallpaper.Intro

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.bmi.Base.BaseActivity
import com.example.wallpaper.Home
import com.example.wallpaper.R
import com.example.wallpaper.databinding.ActivityIntroBinding
import com.example.wallpaper.databinding.ActivityLanguageStartBinding

class IntroActivity : BaseActivity<ActivityIntroBinding, IntroViewModel>() {

    private var introAdapter: IntroAdapter? = null

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
        binding.viewPagerIntro.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
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

                    startActivity(Intent(this, Home::class.java))
                    finish()
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


}