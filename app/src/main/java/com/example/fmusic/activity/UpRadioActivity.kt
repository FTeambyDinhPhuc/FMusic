package com.example.fmusic.activity

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.example.fmusic.MainActivity
import com.example.fmusic.R
import com.example.fmusic.models.LoginResponseModel
import com.example.fmusic.models.ResponseModel
import com.example.fmusic.models.ResponseUpFileModel
import com.example.fmusic.models.TaiKhoanModel
import com.example.fmusic.service_api.APIService
import com.example.fmusic.service_api.Dataservice
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class UpRadioActivity : AppCompatActivity() {
    private lateinit var etNameRadio: EditText
    private lateinit var txtTenFile: TextView
    private lateinit var btnSelect: Button
    private lateinit var btnUpRadio: Button
    private lateinit var btnbackFromUpRadioScreen: Button
    private lateinit var mFile: File
    private lateinit var mProgressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_up_radio)
        mProgressDialog = ProgressDialog(this@UpRadioActivity)
        mProgressDialog.setMessage("Uploading...")
        AnhXa()
        XuLyNhanNut()
    }


    private fun AnhXa() {
        etNameRadio = findViewById(R.id.etNameRadioUpRadio)
        txtTenFile = findViewById(R.id.txtFileNameUpFileScreen)
        btnSelect = findViewById(R.id.btnSelectFileUpRadio)
        btnUpRadio = findViewById(R.id.btnUpRadio)
        btnbackFromUpRadioScreen = findViewById(R.id.btnBackFromUpfileScreen)
    }

    private fun XuLyNhanNut() {
        btnSelect.setOnClickListener{
            openGallery()
        }

        btnUpRadio.setOnClickListener{
            upFile()
        }
    }

    private fun openGallery() {
        val intent = Intent()
        intent.setType("audio/*")
        intent.setAction(Intent.ACTION_GET_CONTENT)
        getAudio.launch(Intent.createChooser(intent, "Select Audio"))
    }

    val getAudio = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult? ->
       if(result != null){
           if(result.resultCode == Activity.RESULT_OK){
               val data: Intent? = result.data
               if(data != null){
                   val uri: Uri = data.data!!
                   val strRealPath = RealPathUtil.getRealPath(this@UpRadioActivity, uri)
                    mFile = File(strRealPath)
                   txtTenFile.setText(mFile.name)
               }
           }
       }
    }


    private fun upFile(){
        mProgressDialog.show()
        val requestBodyFile: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), mFile)
        val multipartBodyFile: MultipartBody.Part = MultipartBody.Part.createFormData("file", mFile.name,requestBodyFile)
        val dataservice: Dataservice = APIService.getService
        val retrofitData = dataservice.upFile(multipartBodyFile)
        retrofitData.enqueue(object : Callback<ResponseUpFileModel> {
            override fun onResponse(
                call: Call<ResponseUpFileModel>,
                response: Response<ResponseUpFileModel>
            ) {
                val responseBody: ResponseUpFileModel? = response.body()
                if (responseBody != null) {
                    if (!responseBody.error) {
                        var fileName: String = responseBody.filename
                        var duongDanRadio: String = responseBody.url
                        upRadio(duongDanRadio, fileName)
                    }
                    Toast.makeText(
                        this@UpRadioActivity,
                        responseBody.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            override fun onFailure(call: Call<ResponseUpFileModel>, t: Throwable) {
                Toast.makeText(
                    this@UpRadioActivity,
                    t.message,
                    Toast.LENGTH_SHORT
                ).show()
            }


        })
    }

    private fun upRadio(duongDanFile: String, tenFile: String){
        var cal = Calendar.getInstance()
        var dinhDangLayNgayDang = SimpleDateFormat("dd/MM/yyyy")
        var ngayDang = dinhDangLayNgayDang.format(cal.time).toString()

        val dataservice: Dataservice = APIService.getService
        val retrofitData = dataservice.themRaido(MainActivity.mTaiKhoan.id_taikhoan,MainActivity.mTaiKhoan.tenhienthi+ " - "+etNameRadio.text.toString().trim(),ngayDang,duongDanFile,tenFile, MainActivity.mTaiKhoan.hinh)
        retrofitData.enqueue(object : Callback<ResponseModel> {
            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                val responseBody: ResponseModel? = response.body()
                if (responseBody != null) {
                    if (!responseBody.error) {
                        finish()
                    }
                    mProgressDialog.dismiss()
                    Toast.makeText(
                        this@UpRadioActivity,
                        responseBody.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                mProgressDialog.dismiss()
                Toast.makeText(this@UpRadioActivity, t.message, Toast.LENGTH_SHORT).show()
            }


        })
    }


}