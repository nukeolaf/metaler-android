package com.bnvs.metaler.data.post

data class PostsCategory(
    val category_type: String,
    val page: Int,
    val limit: Int,
    val search_type: String,
    val search_word: String
)