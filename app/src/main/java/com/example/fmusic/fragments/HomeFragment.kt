package com.example.fmusic.fragments

import android.app.PendingIntent.getService
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fmusic.MainActivity
import com.example.fmusic.R
import com.example.fmusic.models.TaiXeModel
import com.example.fmusic.service_api.APIService
import com.example.fmusic.service_api.Dataservice
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {
    private lateinit var mView: View
    private lateinit var txtFmHome: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_home, container, false)
        txtFmHome = mView.findViewById(R.id.txtFmHome)
        CallApi()
        return mView
    }
    private fun CallApi(){
       val dataservice: Dataservice = APIService.getService
        val retrofitData = dataservice.getTaiXe(1)
        retrofitData.enqueue(object : Callback<TaiXeModel>{
            override fun onResponse(call: Call<TaiXeModel>, response: Response<TaiXeModel>) {
                val TaiXe = response.body()
                if(TaiXe!= null){
                    txtFmHome.setText(TaiXe.data.diachi)
                }else
                {
                    txtFmHome.setText("Khong co gi")
                }
            }

            override fun onFailure(call: Call<TaiXeModel>, t: Throwable) {


            }

        })
    }


}