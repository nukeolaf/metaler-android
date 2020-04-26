package com.bnvs.metaler.data.addeditpost.source.repository

import com.bnvs.metaler.data.addeditpost.model.*
import com.bnvs.metaler.data.addeditpost.source.AddEditPostDataSource
import com.bnvs.metaler.data.addeditpost.source.remote.AddEditPostRemoteDataSource
import okhttp3.MultipartBody

class AddEditPostRepository : AddEditPostDataSource {

    private val addEditPostRemoteDataSource = AddEditPostRemoteDataSource

    override fun addPost(
        request: AddEditPostRequest,
        onSuccess: (response: AddPostResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        addEditPostRemoteDataSource.addPost(request, onSuccess, onFailure)
    }

    override fun editPost(
        postId: Int,
        request: AddEditPostRequest,
        onSuccess: (response: EditPostResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        addEditPostRemoteDataSource.editPost(postId, request, onSuccess, onFailure)
    }

    override fun deletePost(
        postId: Int,
        onSuccess: (response: DeletePostResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        addEditPostRemoteDataSource.deletePost(postId, onSuccess, onFailure)
    }

    override fun uploadFile(
        file: MultipartBody.Part,
        onSuccess: (response: UploadFileResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        addEditPostRemoteDataSource.uploadFile(file, onSuccess, onFailure)
    }
}