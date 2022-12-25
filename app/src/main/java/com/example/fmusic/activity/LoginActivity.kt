package com.example.fmusic.activity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.fmusic.MainActivity
import com.example.fmusic.R
import com.example.fmusic.models.LoginResponseModel
import com.example.fmusic.models.TaiKhoanModel
import com.example.fmusic.service_api.APIService
import com.example.fmusic.service_api.Dataservice
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var etUserName: EditText
    private lateinit var etPass: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnNavSignUp: Button
    private lateinit var mProgressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mProgressDialog = ProgressDialog(this@LoginActivity)
        mProgressDialog.setMessage("Logging in...")
        AnhXa()
        XuLyNhanNut()
    }

    private fun AnhXa(){
        etUserName = findViewById(R.id.etUsername)
        etPass = findViewById(R.id.etPass)
        btnLogin = findViewById(R.id.btnLogin)
        btnNavSignUp = findViewById(R.id.btnNavSignup)
    }

    private fun XuLyNhanNut(){
        btnLogin.setOnClickListener{
             login()
        }
        btnNavSignUp.setOnClickListener{
            navSignup()
        }
    }

    private fun navSignup() {
        val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
        //startActivity(intent)
        getUsernameSignupSusses.launch(intent)
    }

    val getUsernameSignupSusses = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult? ->
      if(result != null){
          val resultCode: Int = result.resultCode
          val data: Intent? = result.data
          if(resultCode == 1){
              etUserName.setText(data!!.getStringExtra("userNameSignup"))
              etPass.setText("")
          }
      }
    }

    private fun login(){
        mProgressDialog.show()
        var userName: String =  etUserName.text.toString().trim()
        var passWord: String =  etPass.text.toString().trim()
        val dataservice: Dataservice = APIService.getService
        val retrofitData = dataservice.login(userName, passWord)
        retrofitData.enqueue(object : Callback<LoginResponseModel> {
            override fun onResponse(
                call: Call<LoginResponseModel>,
                response: Response<LoginResponseModel>
            ) {
                val responseBody: LoginResponseModel? = response.body()
                if (responseBody != null) {
                    if (!responseBody.error) {
                        if(responseBody.data.size > 0){
                            val taikhoan: TaiKhoanModel = responseBody.data[0]
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            intent.putExtra("taiKhoan", taikhoan)
                            startActivity(intent)
                            finish()
                        }
                    }
                    mProgressDialog.dismiss()
                    Toast.makeText(
                        this@LoginActivity,
                        responseBody.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<LoginResponseModel>, t: Throwable) {
                mProgressDialog.dismiss()
                Toast.makeText(this@LoginActivity, t.message, Toast.LENGTH_SHORT).show()
            }

        })
    }
}