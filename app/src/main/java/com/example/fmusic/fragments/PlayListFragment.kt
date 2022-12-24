package com.example.fmusic.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fmusic.MainActivity
import com.example.fmusic.R
import com.example.fmusic.activity.PlayActivity
import com.example.fmusic.adapters.AlbumForYouRVAdapter
import com.example.fmusic.adapters.PlayListAdapter
import com.example.fmusic.models.BaiHatModel
import com.example.fmusic.service_api.APIService
import com.example.fmusic.service_api.Dataservice
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_play_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PlayListFragment(private var listTheo: String,private var idList: Int, private var hinhList: String, private var tenList: String) : Fragment() {
    private lateinit var mView: View
    private lateinit var playListAdapter: PlayListAdapter
    private lateinit var rvPlayList: RecyclerView
    private lateinit var imgPlayList: ImageView
    private lateinit var txtSoLuongBaiHat: TextView
    private lateinit var txtTenPlayList: TextView
    private lateinit var btnPlayList: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(R.layout.fragment_play_list, container, false)
        AnhXa()
        Picasso.get().load(hinhList).into(imgPlayList)
        txtTenPlayList.setText(tenList)
        if(listTheo == "Album"){
            GetListBaiHatByAlbum()
        }else if(listTheo == "Casi"){

        }

        return mView
    }


    private fun AnhXa(){
        rvPlayList = mView.findViewById(R.id.rvPlaylist)
        imgPlayList = mView.findViewById(R.id.imgPlayList)
        txtSoLuongBaiHat = mView.findViewById(R.id.txtSoLuongBaiHat)
        txtTenPlayList = mView.findViewById(R.id.txtTenPlayList)
        btnPlayList = mView.findViewById(R.id.btnPlayList)
    }

//    private fun GetListBaiHatByCaSi(){
//        val dataservice: Dataservice = APIService.getService
//        val retrofitData = dataservice.getListBaiHatByAlbum(idList)
//        retrofitData.enqueue(object : Callback<List<BaiHatModel>> {
//            override fun onResponse(
//                call: Call<List<BaiHatModel>>,
//                response: Response<List<BaiHatModel>>
//            ) {
//                val listBaiHat: ArrayList<BaiHatModel>? = response.body() as ArrayList<BaiHatModel>?
//                if(listBaiHat != null){
//                    mListBaiHat = listBaiHat
//                }
//            }
//            override fun onFailure(call: Call<List<BaiHatModel>>, t: Throwable) {
//                Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
//            }
//        })
//    }


    private fun GetListBaiHatByAlbum(){
        val dataservice: Dataservice = APIService.getService
        val retrofitData = dataservice.getListBaiHatByAlbum(idList)
        retrofitData.enqueue(object : Callback<List<BaiHatModel>> {
            override fun onResponse(
                call: Call<List<BaiHatModel>>,
                response: Response<List<BaiHatModel>>
            ) {
                val listBaiHat: ArrayList<BaiHatModel>? = response.body() as ArrayList<BaiHatModel>?
                if(listBaiHat != null){
                    txtSoLuongBaiHat.setText(listBaiHat.size.toString())
                    rvPlayList.layoutManager = LinearLayoutManager(activity)
                    playListAdapter = PlayListAdapter(listBaiHat)
                    rvPlayList.adapter = playListAdapter
                    btnPlayList.setOnClickListener(object : View.OnClickListener {
                        override fun onClick(view: View?) {
                            val intent = Intent(view!!.context, PlayActivity::class.java)
                            intent.putExtra("listBaiHat", listBaiHat)
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
}