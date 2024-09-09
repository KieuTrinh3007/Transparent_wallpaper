package com.example.wallpaper.Language

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bmi.Base.BaseActivity
import com.example.bmi.Utils.SystemUtils
import com.example.wallpaper.Home
import com.example.wallpaper.databinding.ActivityLanguageStartBinding
import com.example.wallpaper.databinding.ActivitySettingLanguageBinding

class SettingLanguage : BaseActivity<ActivitySettingLanguageBinding, LanguageViewModel>() {
    private var adapter: LanguageAdapter? = null

    override fun createBinding(): ActivitySettingLanguageBinding {
        return ActivitySettingLanguageBinding.inflate(layoutInflater)
    }

    override fun setViewModel(): LanguageViewModel {
        return LanguageViewModel()
    }




    override fun initView() {
        super.initView()


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

    override fun onBackPressed() {
        super.onBackPressed()
        // Điều hướng về màn hình trước đó thay vì thoát ứng dụng
        startActivity(Intent(this, Home::class.java))
        finish()
    }



}
