package com.bnvs.metaler.data.posts.model

data class PostsResponse(
    val is_next: Boolean,
    val post_count: Int,
    val posts: List<Post>
)