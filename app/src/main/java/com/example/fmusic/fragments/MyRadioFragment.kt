package com.example.fmusic.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fmusic.MainActivity
import com.example.fmusic.R
import com.example.fmusic.activity.LoginActivity
import com.example.fmusic.activity.SignUpActivity
import com.example.fmusic.activity.UpRadioActivity
import com.example.fmusic.adapters.MyRadioAdapter
import com.example.fmusic.models.RadioModel
import com.example.fmusic.service_api.APIService
import com.example.fmusic.service_api.Dataservice
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MyRadioFragment : Fragment() {
    private lateinit var mView: View
    private lateinit var btnLogout: ImageButton
    private lateinit var rvMyRadio: RecyclerView
    private lateinit var myRadioAdapter: MyRadioAdapter
    private lateinit var txtSoLuongMyRadio: TextView
    private lateinit var btnNavUpRadio: FloatingActionButton
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(R.layout.fragment_my_radio, container, false)
        AnhXa()
        GetListRadioByTK()
        SuKienNhanNut()
        return mView
    }



    private fun AnhXa() {
        btnLogout = mView.findViewById(R.id.btnLogout)
        rvMyRadio = mView.findViewById(R.id.rvListMyRadio)
        txtSoLuongMyRadio = mView.findViewById(R.id.txtSoLuongMyRadio)
        btnNavUpRadio = mView.findViewById(R.id.btnNavRadio)
    }

    private fun SuKienNhanNut() {
        btnNavUpRadio.setOnClickListener{
            val intent = Intent(activity, UpRadioActivity::class.java)
           // startActivity(intent)
            getNewAddRadio.launch(intent)
        }
        btnLogout.setOnClickListener{
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }

    val getNewAddRadio = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult? ->
        if(result != null){
            val resultCode: Int = result.resultCode
            if(resultCode == 22){
                GetListRadioByTK()
            }
        }
    }


    private fun GetListRadioByTK(){
        val dataservice: Dataservice = APIService.getService
        val retrofitData = dataservice.getListRadioByTK(MainActivity.mTaiKhoan.id_taikhoan)
        retrofitData.enqueue(object : Callback<List<RadioModel>> {
            override fun onResponse(
                call: Call<List<RadioModel>>,
                response: Response<List<RadioModel>>
            ) {
                val listMyRadio: List<RadioModel>? = response.body()
                if(listMyRadio != null){
                    rvMyRadio.layoutManager = LinearLayoutManager(activity )
                    myRadioAdapter = MyRadioAdapter(listMyRadio as ArrayList<RadioModel>, activity)
                    rvMyRadio.adapter = myRadioAdapter
                    txtSoLuongMyRadio.setText(listMyRadio.size.toString())
                }
            }

            override fun onFailure(call: Call<List<RadioModel>>, t: Throwable) {
                Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
            }


        })
    }

}