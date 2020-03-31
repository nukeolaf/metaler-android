package com.bnvs.metaler.data.post

/**
 * Categories, Manufactures, Bookmarks, 내가쓴 글 확인 탭에서
 * 게시물의 리스트를 구성하는 각각의 게시물 데이터를 정의하는 데이터 클래스
 * */

data class Post(
    val post_id: Int,
    val title: String,
    val nickname: String,
    val date: String,
    val tags: List<String>,
    val attach_url: String,
    val like: Int,
    val dis_like: Int,
    var is_bookmark: Boolean
)