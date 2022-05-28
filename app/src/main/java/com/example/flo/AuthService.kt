package com.example.flo

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthService {//데이터 묶어주기3
    private lateinit var signUpView: SignUpView
    private lateinit var loginView: LoginView

    fun setSignUpView(signUpView: SignUpView){
        this.signUpView = signUpView
    }

    fun setLoginView(loginView: LoginView){
        this.loginView = loginView
    }

    fun signUp(user: User){

        val signUpService = getRerofit().create(AuthRetrofitinterface::class.java)

        signUpService.signUp(user).enqueue(object:Callback<AuthResponse>{
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                if (response.isSuccessful && response.code() == 200) {
                    val signUpResponse:AuthResponse = response.body()!!

                    Log.d("SIGNUP/SUCCESS", response.toString())

                    when(signUpResponse.code){
                        1000->signUpView.onSignUpSuccess()
                        else->signUpView.onSignUpFailure()
                    }
                }
            }


            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("SIGNUP/FAILURE", t.message.toString())
            }
        })

        Log.d("SIGNUP", "HELLO")
    }

    fun login(user:User){
        val loginService = getRerofit().create(AuthRetrofitinterface::class.java)

        loginService.login(user).enqueue(object: Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                if (response.isSuccessful && response.code() == 200) {
                    Log.d("LOGIN/SUCCESS", response.toString())
                    val loginResponse: AuthResponse = response.body()!!
                    when (val code = loginResponse.code) {
                        1000 -> loginView.onLoginSuccess(code, loginResponse.result!!)
                        else -> loginView.onLoginFailure()
                    }
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("LOGIN/FAILURE", t.message.toString())
            }

        })

        Log.d("LOGIN", "HELLO")
    }

}