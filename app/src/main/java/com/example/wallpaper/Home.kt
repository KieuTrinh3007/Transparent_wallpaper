package com.example.wallpaper

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.core.view.GravityCompat
import com.example.bmi.Base.BaseActivity
import com.example.bmi.Base.BaseViewModel
import com.example.bmi.Utils.SharePrefUtils
import com.example.wallpaper.HD_Wallpapers.HDWallpapers
import com.example.wallpaper.Language.SettingLanguage
import com.example.wallpaper.Setting.Rate.Rate
import com.example.wallpaper.Setting.Rate.RatingDialog
import com.example.wallpaper.databinding.ActivityHomeBinding
import com.google.android.gms.tasks.Task
import com.google.android.material.navigation.NavigationView
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory


class Home : BaseActivity<ActivityHomeBinding, BaseViewModel>() {

    var navigationView: NavigationView? = null
    private var ratingDialog: RatingDialog? = null
    private var check = false


    override fun createBinding(): ActivityHomeBinding {
        return ActivityHomeBinding.inflate(layoutInflater)
    }

    override fun setViewModel(): BaseViewModel {
        return BaseViewModel()
    }

    override fun viewModel() {
    }


    override fun initView() {
        super.initView()
//        window.statusBarColor = Color.parseColor("#101828") // Màu sẽ trùng với màu background của bạn
        window.navigationBarColor = Color.TRANSPARENT
        navigationView = binding.nav


        binding.imgMenu.setOnClickListener { binding.drawerLayoutHome.openDrawer(GravityCompat.START) }


        binding.nav.getHeaderView(1)

        val width = binding.tvTransparentWallpaper.paint.measureText("Tianjin, China")
        val shader: Shader = LinearGradient(
            0f, 0f, width * 2, 0f,
            intArrayOf(
                Color.parseColor("#6A41FB"),
                Color.parseColor("#B3557F"),
                Color.parseColor("#D45E48"),
                Color.parseColor("#FF6900")
            ), null, Shader.TileMode.CLAMP
        )
        binding.tvTransparentWallpaper.paint.shader = shader

        binding.llHDWP.setOnClickListener {
            startActivity(Intent(this, HDWallpapers::class.java))
        }


        binding.imgMenu.setOnClickListener {
            if (!binding.drawerLayoutHome.isDrawerOpen(GravityCompat.START)) {
                binding.drawerLayoutHome.openDrawer(GravityCompat.START)
            }
        }



        navigationView!!.setNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.nav_language ->
                    if (!check) {
                        check = true
                        navigateTo(SettingLanguage::class.java)
                    }

                R.id.nav_rate -> {
                    if (!check) {
                        check = true
                        showRateDialog()
                    }


                }

                R.id.nav_share -> {
                    if (!check) {
                        check = true
                        share()
                    }


                }

                R.id.nav_feedback -> {
                    // Xử lý khi chọn mục feedback
                }

                R.id.nav_policy -> {
                    if (!check) {
                        check = true
                        openPrivacyPolicy()
                    }
                }
            }
            // Không đóng drawerLayoutHome ở đây
            true
        }


        if (SharePrefUtils.isRated(this@Home)) {
            binding.nav.menu.findItem(R.id.nav_rate).isVisible = false
        } else {
            binding.nav.menu.findItem(R.id.nav_rate).isVisible = true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == androidx.appcompat.R.id.home) {
            binding.drawerLayoutHome.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openPrivacyPolicy() {
        val privacyPolicyUrl = "https://github.com/"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(privacyPolicyUrl))
        startActivity(intent)

    }

    private fun share() {
        val intentShare = Intent(Intent.ACTION_SEND)
        intentShare.setType("text/plain")
        intentShare.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name))
        intentShare.putExtra(
            Intent.EXTRA_TEXT,
            getString(R.string.app_name) + "https://play.google.com/store/apps/details?id=" + packageName
        )
        startActivity(Intent.createChooser(intentShare, "Share"))
    }


    private fun showRateDialog() {

        check = true

        val ratingDialog = RatingDialog(this@Home)
        ratingDialog.setCanceledOnTouchOutside(false)
        ratingDialog!!.init(this@Home, object : RatingDialog.OnPress {
            override fun send(s: Int) {

                binding.nav.menu.findItem(R.id.nav_rate).isVisible = false
                SharePrefUtils.forceRated(this@Home)
                ratingDialog!!.dismiss()
                check = false

            }

            override fun rating(s: Int) {
                onRateAppNew()
                binding.nav.menu.findItem(R.id.nav_rate).isVisible = false
                SharePrefUtils.forceRated(this@Home)
                ratingDialog!!.dismiss()
                check = false
            }

            override fun cancel() {
                ratingDialog!!.dismiss()
                check = false
            }

            override fun later() {
                ratingDialog!!.dismiss()
                check = false
            }

            override fun gotIt() {
                ratingDialog!!.dismiss()
                check = false
            }
        })

        ratingDialog!!.show()
        ratingDialog!!.setOnDismissListener {
            check = false
        }

    }

    private fun rateAppOnStoreNew() {
        val packageName = baseContext.packageName
        val uri: Uri = Uri.parse("market://details?id=$packageName")
        val goToMarket = Intent(Intent.ACTION_VIEW, uri)
        goToMarket.addFlags(
            Intent.FLAG_ACTIVITY_NO_HISTORY or
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK
        )
        try {
            startActivity(goToMarket)
        } catch (e: ActivityNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=$packageName")
                )
            )
        }
    }

    private fun onRateAppNew() {
        val manager: ReviewManager?
        var reviewInfo: ReviewInfo?
        manager = ReviewManagerFactory.create(this)
        val request: Task<ReviewInfo> = manager.requestReviewFlow()
        request.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                SharePrefUtils.forceRated(this)
                reviewInfo = task.result
                val flow: Task<Void> =
                    manager.launchReviewFlow(this, reviewInfo!!)
                flow.addOnSuccessListener {
                    rateAppOnStoreNew()
                }
            }
        }
    }

    private fun hideRateMenuItem() {
        val rateMenuItem = navigationView!!.menu.findItem(R.id.nav_rate)
        rateMenuItem?.setVisible(true)
    }

    override fun onResume() {
        super.onResume()
        check = false
    }

    override fun onBackPressed() {
        super.onBackPressed()
       finishAffinity()
    }

}


