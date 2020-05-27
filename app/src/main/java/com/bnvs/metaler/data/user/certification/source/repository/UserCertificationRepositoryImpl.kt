package com.bnvs.metaler.data.user.certification.source.repository

import com.bnvs.metaler.data.user.certification.model.*
import com.bnvs.metaler.data.user.certification.source.remote.UserCertificationRemoteDataSource

class UserCertificationRepositoryImpl(
    private val userCertificationRemoteDataSource: UserCertificationRemoteDataSource
) : UserCertificationRepository {

    override fun addUser(
        request: AddUserRequest,
        onSuccess: (response: AddUserResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        userCertificationRemoteDataSource.addUser(request, onSuccess, onFailure)
    }

    override fun checkMembership(
        request: CheckMembershipRequest,
        onSuccess: (response: CheckMembershipResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        userCertificationRemoteDataSource.checkMembership(request, onSuccess, onFailure)
    }

    override fun login(
        request: LoginRequest,
        onSuccess: (response: LoginResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        userCertificationRemoteDataSource.login(request, onSuccess, onFailure)
    }
}