package com.example.wallpaper

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import com.amazic.ads.callback.InterCallback
import com.amazic.ads.util.AppOpenManager
import com.amazic.ads.util.manager.native_ad.NativeBuilder
import com.amazic.ads.util.manager.native_ad.NativeManager
import com.example.bmi.Base.BaseActivity
import com.example.bmi.Base.BaseViewModel
import com.example.bmi.Utils.SharePrefUtils
import com.example.wallpaper.HD_Wallpapers.HDWallpapers
import com.example.wallpaper.Language.SettingLanguage
import com.example.wallpaper.Setting.Rate.Rate
import com.example.wallpaper.Setting.Rate.RatingDialog
import com.example.wallpaper.ads.InterManage
import com.example.wallpaper.databinding.ActivityHomeBinding
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.tasks.Task
import com.google.android.material.navigation.NavigationView
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory



class Home : BaseActivity<ActivityHomeBinding, BaseViewModel>() {

    var navigationView: NavigationView? = null
    private var ratingDialog: RatingDialog? = null
    private var check = false

    private var nativeManager: NativeManager? = null
    private var isSharing = false

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

        InterManage.loadInterAll(this@Home)

        try {

            val list: MutableList<String> = ArrayList()
            list.add(getString(R.string.native_language))
            val builder = NativeBuilder(
                this, binding.nativeframeHomeAds,
                R.layout.ads_native_shimmer_home, R.layout.ads_native_layout_home
            )
            builder.setListIdAd(list)
            nativeManager = NativeManager(this, this, builder)


        } catch (e: Exception) {
            e.printStackTrace()
            binding.nativeframeHomeAds.removeAllViews()
            binding.nativeframeHomeAds.setVisibility(View.INVISIBLE)
        }

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

            InterManage.showInterAll(this@Home, object : InterCallback() {
                override fun onNextAction() {
                    super.onNextAction()
                    Log.d("TAG", "onNextAction")
                    startActivity()
                }

                override fun onAdFailedToLoad(i: LoadAdError?) {
                    super.onAdFailedToLoad(i)
                    Log.d("TAG", "onAdFailedToLoad")
                }
            })


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

                        AppOpenManager.getInstance().disableAppResumeWithActivity(Home::class.java)
                    }


                }

                R.id.nav_feedback -> {

                }

                R.id.nav_policy -> {
                    if (!check) {
                        check = true
                        openPrivacyPolicy()
                        AppOpenManager.getInstance().disableAppResumeWithActivity(Home::class.java)
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


        val launchCount = SharePrefUtils.getAppLaunchCount(this)


        if (launchCount % 3 == 0 || launchCount % 5 == 0 || launchCount % 7 == 0) {
            SharePrefUtils.setHasShownRateDialog(this, false)
        }

        if (!SharePrefUtils.isRated(this)
            && (launchCount % 3 == 0 || launchCount % 5 == 0 || launchCount % 7 == 0)
            && !SharePrefUtils.hasShownRateDialog(this)) {

            showRateDialog()
            SharePrefUtils.setHasShownRateDialog(this, true)
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
        ratingDialog?.setCanceledOnTouchOutside(false)
        ratingDialog?.init(this@Home, object : RatingDialog.OnPress {
            override fun send(s: Int) {

                handleRating(s)

                ratingDialog?.dismiss()


            }

            override fun rating(s: Int) {
                handleRating(s)

                ratingDialog?.dismiss()
            }

            override fun cancel() {
                ratingDialog?.dismiss()
                check = false
            }

            override fun later() {
                ratingDialog?.dismiss()
                check = false
            }

            override fun gotIt() {
                ratingDialog?.dismiss()
                check = false
            }
        })

        ratingDialog?.show()
        ratingDialog?.setOnDismissListener {
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

    override fun onResume() {
        super.onResume()
        AppOpenManager.getInstance().enableAppResumeWithActivity(Home::class.java)

        check = false
    }

    override fun onBackPressed() {
        super.onBackPressed()
       finishAffinity()
    }

    private fun startActivity() {
        val intent = Intent(this@Home, HDWallpapers::class.java)
        startActivity(intent)
        finish()

    }

    private fun handleRating(rating: Int) {
        if (rating in 1..3) {
            ratingDialog?.dismiss()
            showFeedbackDialog()
        } else if (rating in 4..5) {
            ratingDialog?.dismiss()
            onRateAppNew()
        }
        binding.nav.menu.findItem(R.id.nav_rate).isVisible = false
        SharePrefUtils.forceRated(this@Home)

    }

    @SuppressLint("InflateParams")
    private fun showFeedbackDialog() {

        if (ratingDialog?.isShowing == true) {

            ratingDialog?.setOnDismissListener {
                displayFeedbackDialog()
            }
        } else {

            displayFeedbackDialog()
        }
    }

    @SuppressLint("MissingInflatedId")
    private fun displayFeedbackDialog() {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater


        val dialogLayout = inflater.inflate(R.layout.dialog_feedback, null)
        builder.setView(dialogLayout)
        val alertDialog = builder.create()
        alertDialog.setCanceledOnTouchOutside(false)

        alertDialog.window?.setBackgroundDrawableResource(R.drawable.round_dialog_br)



        val submitButton = dialogLayout.findViewById<TextView>(R.id.btn_ok)
        val feedbackInput = dialogLayout.findViewById<TextView>(R.id.tvTitle)
        val cancelButton = dialogLayout.findViewById<ImageView>(R.id.img_close)

        submitButton.setOnClickListener {
            alertDialog.dismiss()
        }

        cancelButton.setOnClickListener() {
            alertDialog.dismiss()
        }

        alertDialog.show()
    }


}


