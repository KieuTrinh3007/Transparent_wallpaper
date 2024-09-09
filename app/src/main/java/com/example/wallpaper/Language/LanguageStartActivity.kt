package com.example.wallpaper.Language

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bmi.Base.BaseActivity
import com.example.bmi.Utils.SystemUtils
import com.example.wallpaper.Intro.IntroActivity
import com.example.wallpaper.Model.LanguageModel
import com.example.wallpaper.R
import com.example.wallpaper.databinding.ActivityHomeBinding
import com.example.wallpaper.databinding.ActivityLanguageStartBinding
import java.util.Locale

class LanguageStartActivity : BaseActivity<ActivityLanguageStartBinding, LanguageViewModel>() {

    private var adapter: LanguageAdapter? = null
    override fun createBinding() = ActivityLanguageStartBinding.inflate(layoutInflater)

    override fun setViewModel() = LanguageViewModel()



    override fun initView() {
        super.initView()

        supportActionBar?.hide()
        restoreLocale()
        viewModel = LanguageViewModel()

        adapter = LanguageAdapter(this, mutableListOf()) { language ->
            viewModel.setSelectedLanguage(this, language)

        }


        binding.rcvLanguageStart.layoutManager = LinearLayoutManager(this)
        binding.rcvLanguageStart.adapter = adapter
        viewModel.languages.observe(this) { languages ->
            adapter?.updateData(languages)
        }


        viewModel.selectedLanguage.observe(this) { selectedLanguage ->
            adapter?.setSelectedLanguage(selectedLanguage)
            updateSaveButtonVisibility(selectedLanguage)
        }
        // Initialize data
        viewModel.languageStart(this)


        binding.imgSave.setOnClickListener {
            val selectedLanguage = viewModel.selectedLanguage.value
            if (selectedLanguage != null) {
                viewModel.setLocale(this, selectedLanguage.code)
                saveLanguage(selectedLanguage.code)
                startActivity(Intent(this, IntroActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Please select a language", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun restoreLocale() {
        val sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val languageCode = sharedPreferences.getString("selected_language", "en")
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    private fun updateSaveButtonVisibility(selectedLanguage: LanguageModel?) {
        binding.imgSave.visibility = if (selectedLanguage != null) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    private fun saveLanguage(languageCode: String) {
        val sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("KEY_LANGUAGE", languageCode)
        editor.apply()
    }

}
