package com.example.fmusic.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fmusic.R
import com.example.fmusic.activity.PlayActivity
import com.example.fmusic.adapters.AlbumForYouRVAdapter
import com.example.fmusic.models.AlbumModel
import com.example.fmusic.models.BaiHatModel
import com.example.fmusic.models.CaSiModel
import com.example.fmusic.service_api.APIService
import com.example.fmusic.service_api.Dataservice
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MusicFragment() : Fragment() {
    private lateinit var mView: View
    private lateinit var rvAlbumForYou: RecyclerView
    private lateinit var txtHello: TextView
    private lateinit var albumAdapter: AlbumForYouRVAdapter
    private lateinit var imgBaiHatForYou: ImageView
    private lateinit var txtTenBaiHatForYou: TextView
    private lateinit var txtTenCaSiForYou: TextView
    private lateinit var btnPlayBaiHatForYou: ImageButton
    private lateinit var imgCaSiNewRelease: CircleImageView
    private lateinit var txtTenCaSiNewRelease: TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(R.layout.fragment_music, container, false)
        AnhXa()
        SetHello()
        GetListAllBaiHat()
        GetListAlbum()
        return mView
    }

    private fun AnhXa(){
        txtHello = mView.findViewById(R.id.txtHello)
        imgCaSiNewRelease = mView.findViewById(R.id.imgCasiNewRelease)
        txtTenCaSiNewRelease = mView.findViewById(R.id.txtCasiNewRelease)
        imgBaiHatForYou = mView.findViewById(R.id.imgUpgradeCardForYou)
        txtTenBaiHatForYou = mView.findViewById(R.id.txtTenBaiHatCardForYou)
        txtTenCaSiForYou = mView.findViewById(R.id.txtTenCasiCardForYou)
        btnPlayBaiHatForYou = mView.findViewById(R.id.btnPlayMusicNewRelease)
        rvAlbumForYou = mView.findViewById(R.id.rvAlbumForYou)
    }

    private fun SetHello(){
        var cal = Calendar.getInstance()
        var dinhDangLayGio = SimpleDateFormat("HH")
        var gio = dinhDangLayGio.format(cal.time).toInt()
        var hello: String = "hello"
        if(gio > 0 && gio < 11){
            hello = "Good morning"
        }else if(gio > 10 && gio < 18){
            hello = "Good afternoon"
        }else{
            hello = "Good evening"
        }
        txtHello.setText(hello)
    }

    private fun GetListAlbum(){
        val dataservice: Dataservice = APIService.getService
        val retrofitData = dataservice.getAllAlbum()
        retrofitData.enqueue(object : Callback<List<AlbumModel>> {
            override fun onResponse(
                call: Call<List<AlbumModel>>,
                response: Response<List<AlbumModel>>
            ) {
                val listAlbum: List<AlbumModel>? = response.body()
                if(listAlbum != null){
                    rvAlbumForYou.layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false )
                    albumAdapter = AlbumForYouRVAdapter(listAlbum)
                    rvAlbumForYou.adapter = albumAdapter
                }
            }
            override fun onFailure(call: Call<List<AlbumModel>>, t: Throwable) {
                Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun GetListAllBaiHat(){
        val dataservice: Dataservice = APIService.getService
        val retrofitData = dataservice.getAllBaiHat()
        retrofitData.enqueue(object : Callback<List<BaiHatModel>> {
            override fun onResponse(
                call: Call<List<BaiHatModel>>,
                response: Response<List<BaiHatModel>>
            ) {
                val listBaiHat: ArrayList<BaiHatModel>? = response.body() as ArrayList<BaiHatModel>?
                if(listBaiHat != null){
                    Picasso.get().load(listBaiHat[0].hinhbaihat).into(imgBaiHatForYou)
                    txtTenBaiHatForYou.setText(listBaiHat[0].tenbaihat)
                    GetListCaSiByBaiHat(listBaiHat[0].id_baihat)
                    btnPlayBaiHatForYou.setOnClickListener(object : View.OnClickListener {
                        override fun onClick(view: View?) {
                            val intent = Intent(view!!.context, PlayActivity::class.java)
                            intent.putParcelableArrayListExtra("listBaiHat", listBaiHat)
                            intent.putExtra("viTriBaiHat", 0)
                            startActivity(intent)
                        }

                    })
                }
            }
            override fun onFailure(call: Call<List<BaiHatModel>>, t: Throwable) {
                Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun GetListCaSiByBaiHat(idBaiHat: Int){
        val dataservice: Dataservice = APIService.getService
        val retrofitData = dataservice.getCaSiByBaiHat(idBaiHat)
        retrofitData.enqueue(object : Callback<List<CaSiModel>> {
            override fun onResponse(
                call: Call<List<CaSiModel>>,
                response: Response<List<CaSiModel>>
            ) {
                val listCaSi: List<CaSiModel>? = response.body()
                if(listCaSi != null){
                    Picasso.get().load(listCaSi[0].hinhcasi).into(imgCaSiNewRelease)
                    txtTenCaSiNewRelease.setText(listCaSi[0].tencasi)
                    txtTenCaSiForYou.setText(listCaSi[0].tencasi)
                }
            }
            override fun onFailure(call: Call<List<CaSiModel>>, t: Throwable) {
                Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
            }


        })
    }

}