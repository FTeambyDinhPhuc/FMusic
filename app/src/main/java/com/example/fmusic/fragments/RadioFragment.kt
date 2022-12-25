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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fmusic.R
import com.example.fmusic.activity.PlayActivity
import com.example.fmusic.activity.PlayRadioActivity
import com.example.fmusic.adapters.PlayListRadioAdapter
import com.example.fmusic.models.RadioModel
import com.example.fmusic.service_api.APIService
import com.example.fmusic.service_api.Dataservice
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RadioFragment : Fragment() {

    private lateinit var mView: View
    private lateinit var txtSoLuongBaiHatRadio: TextView
    private lateinit var btnPlayListRadio: ImageButton
    private lateinit var rvPlayListRadio: RecyclerView
    private lateinit var playListRadioAdapter: PlayListRadioAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(R.layout.fragment_radio, container, false)
        AnhXa()
        GetListAllRadio()
        return mView
    }

    private fun AnhXa(){
        txtSoLuongBaiHatRadio = mView.findViewById(R.id.txtSoLuongBaiHatListRadio)
        btnPlayListRadio = mView.findViewById(R.id.btnPlayListRadio)
        rvPlayListRadio = mView.findViewById(R.id.rvPlaylistRadio)
    }

    private fun GetListAllRadio(){
        val dataservice: Dataservice = APIService.getService
        val retrofitData = dataservice.getAllRadio()
        retrofitData.enqueue(object : Callback<List<RadioModel>> {
            override fun onResponse(
                call: Call<List<RadioModel>>,
                response: Response<List<RadioModel>>
            ) {
                val listRadio: List<RadioModel>? = response.body()
                if(listRadio != null){
                    rvPlayListRadio.layoutManager = LinearLayoutManager(activity )
                    playListRadioAdapter = PlayListRadioAdapter(listRadio as ArrayList<RadioModel>)
                    rvPlayListRadio.adapter = playListRadioAdapter
                    txtSoLuongBaiHatRadio.setText(listRadio.size.toString())
                    btnPlayListRadio.setOnClickListener(object : View.OnClickListener {
                        override fun onClick(view: View?) {
                            val intent = Intent(view!!.context, PlayRadioActivity::class.java)
                            intent.putExtra("listRadio", listRadio)
                            intent.putExtra("viTriRadio", 0)
                            startActivity(intent)
                        }

                    })
                }
            }

            override fun onFailure(call: Call<List<RadioModel>>, t: Throwable) {
                Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
            }

        })
    }


}