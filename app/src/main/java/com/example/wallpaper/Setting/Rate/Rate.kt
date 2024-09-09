package com.example.wallpaper.Setting.Rate

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity

class Rate : AppCompatActivity() {
    companion object {
        private const val RATED_KEY = "rated"
        private var sharedPreferences: SharedPreferences? = null

        // Khởi tạo instance SharedPreferences
        fun initialize(sharedPreferences: SharedPreferences) {
            Rate.sharedPreferences = sharedPreferences
        }

        // Kiểm tra xem mục đã được đánh giá chưa
        fun isRated(): Boolean {
            return sharedPreferences?.getBoolean(RATED_KEY, false) ?: false
        }

        // Đặt trạng thái đánh giá
        fun setRated(isRated: Boolean) {
            sharedPreferences?.edit()?.putBoolean(RATED_KEY, isRated)?.apply()
        }
    }
}