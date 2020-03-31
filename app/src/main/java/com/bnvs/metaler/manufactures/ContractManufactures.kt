package com.bnvs.metaler.manufactures

import com.bnvs.metaler.BasePresenter
import com.bnvs.metaler.BaseView
import com.bnvs.metaler.data.post.Post
import com.bnvs.metaler.util.TapBarContract

/**
 * Contract interface 는
 * View 와 Presenter 에 사용될 함수를 정의하고 관리하는데 사용
 * */

interface ContractManufactures {
    interface View : BaseView<Presenter>, TapBarContract.View {
        fun showPosts(posts: List<Post>)

        fun showPostDetailUi()

        fun showSearchUi()

        fun showSearchTags()

        fun clearSearchTagBar()

        fun deleteSearchTag()
    }

    interface Presenter : BasePresenter, TapBarContract.Presenter {
        fun loadPosts()

        fun refreshPosts()

        fun openPostDetail(postId: Int)

        fun addBookmark(postId: Int)

        fun deleteBookmark(postId: Int)

        fun openSearch()

        fun addSearchTag()

        fun clearSearchTagBar()

        fun deleteSearchTag()
    }
}