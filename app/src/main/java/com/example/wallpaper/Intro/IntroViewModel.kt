package com.example.wallpaper.Intro

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wallpaper.R

class IntroViewModel : ViewModel() {
    private val intro = MutableLiveData<List<IntroModel>>()
    val listIntro: LiveData<List<IntroModel>> = intro

    fun getData() {
        val list = listOf(
            IntroModel(R.drawable.img_intro1),
            IntroModel(R.drawable.img_intro2),
            IntroModel(R.drawable.img_intro3)
        )

        intro.value = list

    }
}