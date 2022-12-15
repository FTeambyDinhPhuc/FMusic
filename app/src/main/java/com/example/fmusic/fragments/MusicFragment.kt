package com.example.fmusic.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.fmusic.R
import com.example.fmusic.models.TaiXeModel
import com.example.fmusic.service_api.APIService
import com.example.fmusic.service_api.Dataservice
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MusicFragment : Fragment() {
    private lateinit var mView: View
    private lateinit var txtFmMusic: TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_music, container, false)
        txtFmMusic = mView.findViewById(R.id.txtFmMusic)
        CallApi()
        return mView
    }

    private fun CallApi(){
        val dataservice: Dataservice = APIService.getService
        val retrofitData = dataservice.getTaiXe(1)
        retrofitData.enqueue(object : Callback<TaiXeModel> {
            override fun onResponse(call: Call<TaiXeModel>, response: Response<TaiXeModel>) {
                val TaiXe = response.body()
                if(TaiXe!= null){
                    txtFmMusic.setText(TaiXe.data.diachi)
                }else
                {
                    txtFmMusic.setText("Khong co gi")
                }
            }
            override fun onFailure(call: Call<TaiXeModel>, t: Throwable) {}
        })
    }

}