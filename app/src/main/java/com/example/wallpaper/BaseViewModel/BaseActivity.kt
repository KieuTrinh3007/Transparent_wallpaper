package com.example.bmi.Base

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.example.bmi.Utils.SystemUtils

abstract class BaseActivity<VB : ViewBinding, V: ViewModel> : AppCompatActivity() {
    protected lateinit var binding: VB
    protected lateinit var viewModel: V

    abstract fun createBinding(): VB
    abstract fun setViewModel(): V

    protected open fun initView() {}
    protected open fun bindView() {}
    protected open fun viewModel() {}
    open fun initData() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SystemUtils.setLocale(this)
        binding = createBinding()
        setContentView(binding.root)
        binding.root.setBackgroundColor(Color.BLACK)
        viewModel = setViewModel()
        viewModel()
        initView()
        bindView()
        hideNavigation(this)


    }

    fun navigateTo(activityClass: Class<*>?) {

        startActivity(Intent(this, activityClass))
        finish()
    }

    protected fun navigateToAddPosition(activityClass: Class<*>?, pos: Int) {

            val intent = Intent(this, activityClass)
            intent.putExtra("position", pos)
            startActivity(intent)
            finish()
        }
private fun hideNavigation(activity: Activity) {
    try {
        if (activity == null) return
        val window: Window = activity.window ?: return

        // Clear the fullscreen flags
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)



        // Reset system UI visibility flags to default
        window.decorView.systemUiVisibility = (

                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                )
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

        // mau status bar
        //window.statusBarColor = ContextCompat.getColor(activity, R.color.transparent)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowInsetsController = window.insetsController
            window.statusBarColor = Color.TRANSPARENT // Làm cho thanh trạng thái trong suốt
        } else {
            window.statusBarColor =
                Color.TRANSPARENT // Chỉ định màu trong suốt cho các phiên bản Android thấp hơn
        }
        // Adjust window attributes to reset display cutout mode if necessary
        val lp = window.attributes
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            lp.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT
        }
        window.attributes = lp
    } catch (e: Exception) {
        e.printStackTrace()
    }

}
}

