package com.bnvs.metaler.data.myposts.source

import com.bnvs.metaler.data.myposts.MyPosts
import com.bnvs.metaler.data.myposts.MyPostsRequest

interface MyPostsDataSource {

    fun getMyPosts(
        request: MyPostsRequest,
        onSuccess: (response: MyPosts) -> Unit,
        onFailure: (e: Throwable) -> Unit
    )

}