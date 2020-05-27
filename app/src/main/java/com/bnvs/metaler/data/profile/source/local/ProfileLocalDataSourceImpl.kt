package com.bnvs.metaler.data.profile.source.local

import android.content.Context
import com.bnvs.metaler.data.profile.model.Profile
import com.bnvs.metaler.data.user.certification.model.User
import com.google.gson.GsonBuilder

class ProfileLocalDataSourceImpl(context: Context) : ProfileLocalDataSource {

    private val sharedPreferences =
        context.getSharedPreferences("LOCAL_PROFILE_DATA", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    override fun getProfile(
        onProfileLoaded: (profile: Profile) -> Unit,
        onProfileNotExist: () -> Unit
    ) {
        val userInfo = sharedPreferences.getString("userInfo", null)
        if (userInfo == null) {
            onProfileNotExist()
            return
        }

        val user = GsonBuilder().create().fromJson(userInfo, User::class.java)

        if (user != null) {
            onProfileLoaded(
                Profile(
                    user.profile_nickname,
                    user.profile_image_url,
                    user.profile_email
                )
            )
        } else {
            onProfileNotExist()
        }
    }

    override fun getUserInfo(
        onUserInfoLoaded: (user: User) -> Unit,
        onUserInfoNotExist: () -> Unit
    ) {
        val userInfo = sharedPreferences.getString("userInfo", null)
        if (userInfo != null) {
            onUserInfoLoaded(GsonBuilder().create().fromJson(userInfo, User::class.java))
        } else {
            onUserInfoNotExist()
        }
    }

    override fun saveUserInfo(user: User) {
        editor.putString("userInfo", GsonBuilder().create().toJson(user))
        editor.commit()
    }
}