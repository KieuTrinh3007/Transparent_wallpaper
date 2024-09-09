package com.example.wallpaper.Setting

import com.example.bmi.Base.BaseActivity
import com.example.bmi.Base.BaseViewModel
import com.example.wallpaper.databinding.ActivitySettingScreenBinding

class SettingActivity : BaseActivity<ActivitySettingScreenBinding, BaseViewModel>() {

    private var check = false


    override fun createBinding(): ActivitySettingScreenBinding {
        return ActivitySettingScreenBinding.inflate(layoutInflater)
    }

    override fun setViewModel(): BaseViewModel {
        return BaseViewModel()
    }

    override fun initView() {
        super.initView()





    }

}