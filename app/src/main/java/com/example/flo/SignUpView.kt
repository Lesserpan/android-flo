package com.example.flo

interface SignUpView {// data 묶어주기(Activity랑 AuthService 묶어줌)1 이후 signupactivity랑 연결
    fun onSignUpSuccess()
    fun onSignUpFailure()

}