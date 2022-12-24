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
import com.example.fmusic.MainActivity
import com.example.fmusic.R
import com.example.fmusic.activity.PlayActivity
import com.example.fmusic.adapters.PlayListAdapter
import com.example.fmusic.adapters.PlayListRadioAdapter
import com.example.fmusic.models.BaiHatModel
import com.example.fmusic.service_api.APIService
import com.example.fmusic.service_api.Dataservice
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LibraryFragment : Fragment() {

    private lateinit var mView: View
    private lateinit var txtSoLuongBaiHatFavourite: TextView
    private lateinit var btnPlayListFavourite: ImageButton
    private lateinit var rvPlayListFavourite: RecyclerView
    private lateinit var playListFavouriteAdapter: PlayListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(R.layout.fragment_library, container, false)
        AnhXa()
        GetListAllBaiHatYeuThichByTK()
        return mView
    }

    private fun AnhXa() {
        txtSoLuongBaiHatFavourite = mView.findViewById(R.id.txtSoLuongBaiHatListFavourite)
        btnPlayListFavourite = mView.findViewById(R.id.btnPlayListFavourite)
        rvPlayListFavourite = mView.findViewById(R.id.rvPlaylistFavourite)
    }

    private fun GetListAllBaiHatYeuThichByTK(){
        val dataservice: Dataservice = APIService.getService
        val retrofitData = dataservice.getALlBaiHatYeuThichByTK(MainActivity.mTaiKhoan.id_taikhoan)
        retrofitData.enqueue(object : Callback<List<BaiHatModel>> {
            override fun onResponse(
                call: Call<List<BaiHatModel>>,
                response: Response<List<BaiHatModel>>
            ) {
                val listBaiHatYeuThich: List<BaiHatModel>? = response.body()
                if(listBaiHatYeuThich != null){
                    rvPlayListFavourite.layoutManager = LinearLayoutManager(activity )
                    playListFavouriteAdapter = PlayListAdapter(listBaiHatYeuThich as ArrayList<BaiHatModel>)
                    rvPlayListFavourite.adapter = playListFavouriteAdapter
                    txtSoLuongBaiHatFavourite.setText(listBaiHatYeuThich.size.toString())
                    btnPlayListFavourite.setOnClickListener(object : View.OnClickListener {
                        override fun onClick(view: View?) {
                            val intent = Intent(view!!.context, PlayActivity::class.java)
                            intent.putExtra("listBaiHat", listBaiHatYeuThich)
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