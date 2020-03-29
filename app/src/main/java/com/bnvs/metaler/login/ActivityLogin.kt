package com.bnvs.metaler.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bnvs.metaler.R
import com.bnvs.metaler.data.token.AccessToken
import com.bnvs.metaler.data.token.SigninToken
import com.bnvs.metaler.data.token.source.TokenDataSource
import com.bnvs.metaler.data.token.source.TokenRepository
import com.bnvs.metaler.data.user.CheckMembershipRequest
import com.bnvs.metaler.data.user.CheckMembershipResponse
import com.bnvs.metaler.data.user.LoginRequest
import com.bnvs.metaler.data.user.LoginResponse
import com.bnvs.metaler.home.ActivityHome
import com.bnvs.metaler.network.RetrofitClient
import com.bnvs.metaler.termsagree.ActivityTermsAgree
import com.bnvs.metaler.util.DeviceInfo
import com.kakao.auth.ISessionCallback
import com.kakao.auth.Session
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.MeV2ResponseCallback
import com.kakao.usermgmt.response.MeV2Response
import com.kakao.util.exception.KakaoException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class ActivityLogin : AppCompatActivity() {

    private val TAG = "ActivityLogin"

    private lateinit var callback: SessionCallback
    private val retrofitClient = RetrofitClient.client
    private val tokenRepository = TokenRepository(this)

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
                    val kakao_id = result!!.id.toString()

                    // local 에 signin_token 존재하는지 확인
                    tokenRepository.getSigninToken(object : TokenDataSource.LoadSigninTokenCallback {
                        override fun onTokenloaded(token: SigninToken) {
                            val signinToken = token.signin_token
                            // local 에 access_token 존재하는지 확인
                            tokenRepository.getAccessToken(object: TokenDataSource.LoadAccessTokenCallback {
                                override fun onTokenloaded(token: AccessToken) {
                                    // access_Token 이 유효한지(발급받은지 24시간이 지나지 않은) 확인
                                    if (isTokenValid(token.valid_time)) {
                                        openHome()
                                    }else {
                                        // 로그인 api 호출하여 access_token 발급
                                        login(kakao_id, signinToken)
                                    }
                                }

                                override fun onTokenNotExist() {
                                    // 로그인 api 호출하여 access_token 발급
                                    login(kakao_id, signinToken)
                                }
                            })
                        }
                        override fun onTokenNotExist() {
                            // signin_token 존재하지 않음, 회원가입 여부확인 api 호출
                            retrofitClient.checkUserMembership(CheckMembershipRequest(kakao_id)).enqueue(object : Callback<CheckMembershipResponse> {
                                override fun onResponse(
                                    call: Call<CheckMembershipResponse>,
                                    response: Response<CheckMembershipResponse>
                                ) {
                                    if (response.isSuccessful) {
                                        Log.d(TAG, "Metaler api 응답 : $response")
                                        var responseData = response.body()
                                        if (responseData != null) {
                                            Log.d(TAG, "회원가입 여부 확인 : $responseData")
                                            when(responseData.message) {
                                                "you_can_join" -> {
                                                    // 회원가입 액티비티 실행
                                                    openTermsAgree()
                                                }
                                                else -> {
                                                    // signin_token local 에 저장후 login api 호출
                                                    tokenRepository.saveSigninToken(SigninToken(responseData.signin_token))
                                                    login(kakao_id, responseData.signin_token)
                                                }
                                            }
                                        }else {
                                            Log.d(TAG, "회원가입 여부 확인 : 응답이 null 값임")
                                        }
                                    }else {
                                        Log.d(TAG, "Metaler api 응답 response failed : " +
                                                "${response.errorBody().toString()}")
                                    }
                                }

                                override fun onFailure(call: Call<CheckMembershipResponse>, t: Throwable) {
                                    Log.d(TAG, "회원가입 여부 확인 실패 : $t")
                                }
                            })
                        }
                    })
                }

                override fun onSessionClosed(errorResult: ErrorResult?) {
                    // 로그인 도중 세션이 비정상적인 이유로 닫혔을 때
                    makeToast("세션이 닫혔습니다. 다시 시도해주세요 : ${errorResult.toString()}")
                }
            })
        }
        override fun onSessionOpenFailed(exception: KakaoException?) {
            // 로그인 세션이 정상적으로 열리지 않았을 때
            if (exception != null) {
                com.kakao.util.helper.log.Logger.e(exception)
                makeToast("로그인 도중 오류가 발생했습니다. 인터넷 연결을 확인해주세요 : $exception")
            }
        }

    }

    // access_token 유효시간과 현재시간을 비교하여
    // 아직 유효시간이 남았으면 true 를 반환
    private fun isTokenValid(validTime: String): Boolean {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val now = Date(System.currentTimeMillis())
        val validTime = dateFormat.parse(validTime)
        val duration = validTime.time - now.time
        return duration > 0
    }

    // access_token 유효시간(발급시간으로부터 24시간까지)을 계산하여 리턴하는 함수
    private fun getValidTime() : String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val calendar = Calendar.getInstance().apply {
            time = Date(System.currentTimeMillis())
            add(Calendar.DATE, 1)
        }

        return dateFormat.format(calendar.time)
    }
    
    private fun loginRequest(kakao_id: String, signin_token: String) : LoginRequest{
        val deviceInfo = DeviceInfo(this)
        return LoginRequest(
            kakao_id,
            signin_token,
            "push_token",
            deviceInfo.getDeviceId(),
            deviceInfo.getDeviceModel(),
            deviceInfo.getDeviceOs(),
            deviceInfo.getAppVersion()
        )
    }

    private fun login(kakao_id: String, signin_token: String) {
        retrofitClient.login(loginRequest(kakao_id, signin_token))
            .enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    if (response.isSuccessful) {
                        Log.d(TAG, "로그인 api 응답 : $response")
                        var responseData = response.body()
                        Log.d(TAG, "로그인 api 응답 body : $responseData")

                        if (responseData != null) {
                            // 발급받은 access_token local 에 저장후 home 탭 시작
                            tokenRepository.saveAccessToken(
                                AccessToken(responseData.access_token, getValidTime())
                            )
                            openHome()
                        }else {
                            Log.d(TAG, "로그인 api 응답 : 응답이 null 값임")
                        }

                    }else {
                        Log.d(TAG, "Metaler 로그인 api 응답 response failed : " +
                                "${response.errorBody().toString()}")
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.d(TAG, "로그인 실패 : $t")
                }
            })
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

    private fun makeToast(message: String) {
        Toast.makeText(this@ActivityLogin, message, Toast.LENGTH_SHORT).show()
    }

}
