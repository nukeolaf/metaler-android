package com.bnvs.metaler.data.bookmarks.source

import android.content.Context
import com.bnvs.metaler.data.bookmarks.source.remote.BookmarksRemoteDataSource

class BookmarksRepository(context: Context) : BookmarksDataSource {

    private val bookmarksRemoteDataSource = BookmarksRemoteDataSource

    override fun deleteBookmark(callback: BookmarksDataSource.DeleteBookmarkCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addBookmark(callback: BookmarksDataSource.AddBookmarkCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}