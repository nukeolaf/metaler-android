package com.bnvs.metaler.data.nickname.source

import android.content.Context
import com.bnvs.metaler.data.nickname.source.remote.NicknameRemoteDataSource

class NicknameRepository(context: Context) : NicknameDataSource {

    private val nicknameRemoteDataSource = NicknameRemoteDataSource

    override fun modifyNickname(callback: NicknameDataSource.ModifyNicknameCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}