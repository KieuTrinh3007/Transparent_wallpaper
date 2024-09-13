package com.example.wallpaper.ads

import android.annotation.SuppressLint
import android.app.Activity
import com.amazic.ads.callback.InterCallback
import com.amazic.ads.util.Admob
import com.amazic.ads.util.AdsConsentManager
import com.example.wallpaper.R
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd

object InterManage {
    var isLoadAll = false
    var interAll: InterstitialAd? = null

    fun loadInterAll(activity: Activity) {
        try {

            Admob.getInstance()
                .loadInterAdsFloor(activity,
                    mutableListOf(activity.getString(R.string.inter_intro)),
                    object : InterCallback() {
                        override fun onAdLoadSuccess(interstitialAd: InterstitialAd) {
                            super.onAdLoadSuccess(interstitialAd)
                            interAll = interstitialAd
                            isLoadAll = false
                        }


                        override fun onAdFailedToLoad(i: LoadAdError?) {
                            super.onAdFailedToLoad(i)
                            isLoadAll = false
                            interAll = null

                        }
                    })

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    fun showInterAll(activity: Activity, interCallback: InterCallback) {
        try {

            val currentTime = System.currentTimeMillis()
            val timeInterStart = 15000L
            if (
                !Admob.isShowAllAds
                || (currentTime - Constants.timeFromStart) < timeInterStart
            ) {
                interCallback.onNextAction()
                return
            } else {
                Admob.getInstance().showInterAds(activity, interAll, object : InterCallback() {
                    override fun onNextAction() {
                        super.onNextAction()
                        Admob.getInstance().setOpenActivityAfterShowInterAds(true)
                        isLoadAll = false
                        interAll = null
                        interCallback.onNextAction()
                    }

                })
            }
        } catch (e: Exception) {
            e.printStackTrace()
            interCallback.onNextAction()
        }

    }
}