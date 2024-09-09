package com.example.wallpaper.Model

data class LanguageModel (
    var languageName: String,
    var code: String,
    var active: Boolean = false,
    var image: Int
)