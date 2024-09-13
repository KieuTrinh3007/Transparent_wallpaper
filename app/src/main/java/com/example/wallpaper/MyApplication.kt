package com.example.wallpaper

import android.app.Activity
import android.os.Bundle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ProcessLifecycleOwner
import com.amazic.ads.util.AdsApplication
import com.amazic.ads.util.AppOpenManager
import com.google.android.material.color.DynamicColors

class MyApplication : AdsApplication() {
    override fun onCreate() {
        super.onCreate()

        AppOpenManager.getInstance().disableAppResumeWithActivity(Splash::class.java)

        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                // Handle activity creation
            }

            override fun onActivityStarted(activity: Activity) {

            }

            override fun onActivityResumed(activity: Activity) {

            }

            override fun onActivityPaused(activity: Activity) {

            }

            override fun onActivityStopped(activity: Activity) {

            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

            }

            override fun onActivityDestroyed(activity: Activity) {

            }
        })

    }
    override fun getAppTokenAdjust(): String = "null"

    override fun getFacebookID(): String = "null"

    override fun buildDebug(): Boolean = true

    override fun initAppOpenResume(): String {
        return "ca-app-pub-3940256099942544/9257395921"
    }

    override fun isSetUpAdjust(): Boolean {
        return true
    }
}