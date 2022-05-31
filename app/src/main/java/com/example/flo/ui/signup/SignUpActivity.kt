package com.example.flo.ui.signup

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.data.entities.User
import com.example.flo.data.remote.AuthService
import com.example.flo.databinding.ActivitySignupBinding


class SignUpActivity : AppCompatActivity(), SignUpView {//데이터 묶어주기2

    lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signUpSignUpBtn.setOnClickListener {
            signUp()


        }
    }
    private fun getUser(): User {
        val email: String = binding.signUpIdEt.text.toString() + "@" + binding.signUpDirectInputEt.text.toString()
        val pwd: String = binding.signUpPasswordEt.text.toString()
        val name: String = binding.signUpNameEt.text.toString()//이름 가져오기 2

        return User(email, pwd, name)//이름 가져오기 3
    }

    /*private fun signUp(){
        if(binding.signUpIdEt.text.toString().isEmpty()||binding.signUpDirectInputEt.text.toString().isEmpty()) {
            Toast.makeText(this,"이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
            return
        }
        if(binding.signUpNameEt.text.toString().isEmpty()) {
            Toast.makeText(this,"이름을 입력하시지 않았습니다.", Toast.LENGTH_SHORT).show()
            return
        }
        if(binding.signUpPasswordEt.text.toString()!=binding.signUpPasswordCheckEt.text.toString()) {
            Toast.makeText(this,"비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        val userDB=SongDatabase.getInstance(this)!!
        userDB.userDao().insert(getUser())

        val user = userDB.userDao().getUsers()
        Log.d("SIGNUPACT",user.toString())

    } 룸 DB 사용할때 쓰는 코드 (내부저장소)*/



    private fun signUp() {
        if (binding.signUpIdEt.text.toString()
                .isEmpty() || binding.signUpDirectInputEt.text.toString().isEmpty()) {
            Toast.makeText(this, "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        if (binding.signUpNameEt.text.toString()
                .isEmpty()) {
            Toast.makeText(this, "이름 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        if (binding.signUpPasswordEt.text.toString() != binding.signUpPasswordCheckEt.text.toString()) {
            Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            return
        }



        val authService = AuthService()
        authService.setSignUpView(this)

        authService.signUp(getUser())
    }


    override fun onSignUpSuccess() {//데이터 묶어주기2
        finish()
    }

    override fun onSignUpFailure() {//데이터 묶어주기2 이후 AuthService 만들어주기
        TODO("Not yet implemented")
    }


}