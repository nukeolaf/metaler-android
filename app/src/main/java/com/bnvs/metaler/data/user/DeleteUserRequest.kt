package com.bnvs.metaler.data.user

import org.json.JSONObject

data class DeleteUserRequest(
    val access_token: String,
    val user: List<JSONObject>
)