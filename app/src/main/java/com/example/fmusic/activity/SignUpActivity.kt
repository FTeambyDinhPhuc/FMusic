package com.example.fmusic.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.fmusic.MainActivity
import com.example.fmusic.R
import com.example.fmusic.models.LoginResponseModel
import com.example.fmusic.models.ResponseModel
import com.example.fmusic.models.TaiKhoanModel
import com.example.fmusic.service_api.APIService
import com.example.fmusic.service_api.Dataservice
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class SignUpActivity : AppCompatActivity() {
    private lateinit var etUserName: EditText
    private lateinit var etPass: EditText
    private lateinit var etFullName: EditText
    private lateinit var etBirthday: EditText
    private lateinit var btnSignup: Button
    private lateinit var btnNavLogin: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        AnhXa()
        XuLyNhanNut()
    }

    private fun AnhXa() {
        etUserName = findViewById(R.id.etUsernameSignup)
        etPass = findViewById(R.id.etPassSignup)
        etFullName = findViewById(R.id.etFullNameSignup)
        etBirthday = findViewById(R.id.etBirthdatSignup)
        btnSignup = findViewById(R.id.btnSignup)
        btnNavLogin = findViewById(R.id.btnNavLogin)
    }

    private fun XuLyNhanNut() {
        btnSignup.setOnClickListener{
            signup()
        }

        btnNavLogin.setOnClickListener{
            finish()
        }
    }

    private fun signup() {
        var userName: String =  etUserName.text.toString().trim()
        var passWord: String =  etPass.text.toString().trim()
        var fullName: String =  etFullName.text.toString().trim()
        var birthDay: String =  etBirthday.text.toString().trim()


        val dataservice: Dataservice = APIService.getService
        val retrofitData = dataservice.signup(userName, passWord, fullName, birthDay)
        retrofitData.enqueue(object : Callback<ResponseModel> {
            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                val responseBody: ResponseModel? = response.body()
                if (responseBody != null) {
                    if (!responseBody.error) {
                        val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
                        intent.putExtra("userNameSignup", userName)
                        setResult(1,intent)
                       finish()
                    }
                    Toast.makeText(
                        this@SignUpActivity,
                        responseBody.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                Toast.makeText(this@SignUpActivity, t.message, Toast.LENGTH_SHORT).show()
            }

        })
    }
}