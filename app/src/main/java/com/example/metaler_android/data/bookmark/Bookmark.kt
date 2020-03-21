package com.example.metaler_android.data.bookmark

data class Bookmark(
    val bookmark_id: String,
    val title: String,
    val nickname: Int,
    val date: String,
    val tags: List<String>,
    val attach_urls: List<String>,
    val like: Int,
    val dis_like: Int,
    val is_bookmark: Boolean
)