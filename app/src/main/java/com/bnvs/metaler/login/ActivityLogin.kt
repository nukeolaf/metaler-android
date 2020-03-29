package com.bnvs.metaler.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bnvs.metaler.R
import com.bnvs.metaler.home.ActivityHome
import com.bnvs.metaler.network.RetrofitClient
import com.bnvs.metaler.termsagree.ActivityTermsAgree
import com.kakao.auth.ISessionCallback
import com.kakao.auth.Session
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.MeV2ResponseCallback
import com.kakao.usermgmt.response.MeV2Response
import com.kakao.util.exception.KakaoException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivityLogin : AppCompatActivity() {

    private val TAG = "ActivityLogin"

    private lateinit var callback: SessionCallback
    private val retrofitClient = RetrofitClient.client

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // SessionCallback 초기화
        callback = SessionCallback()
        // 현재 세션에 callback 붙이기
        Session.getCurrentSession().addCallback(callback)
        // 현재 앱에 유효한 카카오 로그인 토큰이 있다면 바로 로그인(자동 로그인과 유사)
        Session.getCurrentSession().checkAndImplicitOpen()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        super.onDestroy()
        Session.getCurrentSession().removeCallback(callback)
    }

    private inner class SessionCallback : ISessionCallback {
        override fun onSessionOpened() {
            // 로그인 세션이 열렸을 때
            UserManagement.getInstance().me( object : MeV2ResponseCallback() {
                override fun onSuccess(result: MeV2Response?) {
                    // 카카오 로그인이 성공했을 때
                    // Metaler 회원가입 여부 확인
                    Log.d(TAG, "카카오 아이디 : ${result!!.id}")
                    retrofitClient.checkUserMembership(
                        JSONObject().put("kakao_id", result!!.id)
                    ).enqueue(object : Callback<JSONObject> {
                        override fun onResponse(call: Call<JSONObject>, response: Response<JSONObject>) {
                            when(response.message()) {
                                "you_can_join" -> {
                                    openTermsAgree()
                                }
                                else -> openHome()
                            }
                        }
                        override fun onFailure(call: Call<JSONObject>, t: Throwable) {

                        }
                    })

                }

                override fun onSessionClosed(errorResult: ErrorResult?) {
                    // 로그인 도중 세션이 비정상적인 이유로 닫혔을 때
                    Toast.makeText(
                        this@ActivityLogin,
                        "세션이 닫혔습니다. 다시 시도해주세요 : ${errorResult.toString()}",
                        Toast.LENGTH_SHORT).show()
                }
            })
        }
        override fun onSessionOpenFailed(exception: KakaoException?) {
            // 로그인 세션이 정상적으로 열리지 않았을 때
            if (exception != null) {
                com.kakao.util.helper.log.Logger.e(exception)
                Toast.makeText(
                    this@ActivityLogin,
                    "로그인 도중 오류가 발생했습니다. 인터넷 연결을 확인해주세요 : $exception",
                    Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun openTermsAgree() {
        val intent = Intent(this, ActivityTermsAgree::class.java)
        startActivity(intent)
        finish()
    }

    private fun openHome() {
        val intent = Intent(this, ActivityHome::class.java)
        startActivity(intent)
        finish()
    }

}
