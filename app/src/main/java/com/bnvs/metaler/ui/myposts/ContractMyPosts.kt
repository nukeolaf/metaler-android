package com.bnvs.metaler.ui.myposts

import com.bnvs.metaler.BasePresenter
import com.bnvs.metaler.BaseView
import com.bnvs.metaler.data.myposts.model.MyPost
import com.bnvs.metaler.data.myposts.model.MyPostsRequest
import com.bnvs.metaler.util.TapBarContract

interface ContractMyPosts {
    interface View : BaseView<Presenter>, TapBarContract.View {
        fun showMyPostsList(myPosts: List<MyPost>)

        fun showPostDetailUi(postId: Int)
    }

    interface Presenter : BasePresenter, TapBarContract.Presenter {

        fun openMaterialsList(myPostsRequest: MyPostsRequest)

        fun openManufacturesList(myPostsRequest: MyPostsRequest)

        fun requestPosts(categoryType: String): MyPostsRequest

        fun openPostDetail(postId: Int)
    }
}