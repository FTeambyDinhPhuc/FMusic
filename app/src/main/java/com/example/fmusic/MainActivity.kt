package com.example.fmusic

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import com.example.fmusic.activity.PlayActivity
import com.example.fmusic.fragments.*
import com.example.fmusic.models.BaiHatModel
import com.example.fmusic.service_api.APIService
import com.example.fmusic.service_api.Dataservice
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var musicLayout: LinearLayout
    private lateinit var radioLayout: LinearLayout
    private lateinit var searchLayout: LinearLayout
    private lateinit var libraryLayout: LinearLayout
    private lateinit var musicImage: ImageView
    private lateinit var radioImage: ImageView
    private lateinit var searchImage: ImageView
    private lateinit var libraryImage: ImageView


    companion object {
        var mediaPlayer: MediaPlayer = MediaPlayer()
        var idTaiKhoan: Int = 1
        lateinit var listBaiHatYeuThich: ArrayList<BaiHatModel>

        @JvmStatic
        fun GetListBaiHatYeuThich(context: Context){
            val dataservice: Dataservice = APIService.getService
            val retrofitData = dataservice.getListBaiHatYeuThichByTK(idTaiKhoan)
            retrofitData.enqueue(object : Callback<List<BaiHatModel>> {
                override fun onResponse(
                    call: Call<List<BaiHatModel>>,
                    response: Response<List<BaiHatModel>>
                ) {
                    val listBaiHat: ArrayList<BaiHatModel>? = response.body() as ArrayList<BaiHatModel>?
                    if(listBaiHat != null){
                        listBaiHatYeuThich = listBaiHat
                    }
                }
                override fun onFailure(call: Call<List<BaiHatModel>>, t: Throwable) {
                    Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                }
            })
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var moManHinh: String? = intent.getStringExtra("moManHinh")
        AnhXaView()
        GetListBaiHatYeuThich(this@MainActivity)

        if(moManHinh == null){
            //set fragment mặc định hiển thị
            supportFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragmentContainer, MusicFragment())
                .commit()
        }else{
            if(moManHinh == "PlayListFragment"){
                var listTheo: String? = intent.getStringExtra("listTheo")
                var hinhList: String? = intent.getStringExtra("hinhList")
                var tenList: String? = intent.getStringExtra("tenList")
                var idList: Int? = intent.getIntExtra("idList", 0)

                //set fragment
                supportFragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragmentContainer, PlayListFragment(listTheo!!, idList!!, hinhList!!, tenList!!))
                    .commit()
            }
        }



        XuLyBottomNavigationBar()
    }

    private fun AnhXaView(){
        musicLayout = findViewById<LinearLayout>(R.id.musicLayout)
        radioLayout = findViewById<LinearLayout>(R.id.radioLayout)
        searchLayout = findViewById<LinearLayout>(R.id.searchLayout)
        libraryLayout = findViewById<LinearLayout>(R.id.libraryLayout)

        musicImage = findViewById<ImageView>(R.id.musicImage)
        radioImage = findViewById<ImageView>(R.id.radioImage)
        searchImage = findViewById<ImageView>(R.id.searchImage)
        libraryImage = findViewById<ImageView>(R.id.libraryImage)
    }

    private fun XuLyBottomNavigationBar(){
        var selectedTab: Int = 1



        var nhanChuyenManHinh: View.OnClickListener? = null
        nhanChuyenManHinh = View.OnClickListener{
            if(it == musicLayout)
            {
                if(selectedTab != 1)
                {
                    //set fragment
                    supportFragmentManager.beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.fragmentContainer, MusicFragment())
                        .commit()

                    //Xử lý các nút không được chọn
                    radioImage.setImageResource(R.drawable.ic_radio)
                    searchImage.setImageResource(R.drawable.ic_search)
                    libraryImage.setImageResource(R.drawable.ic_library_music)

                    radioImage.setBackgroundColor(Color.TRANSPARENT)
                    searchImage.setBackgroundColor(Color.TRANSPARENT)
                    libraryImage.setBackgroundColor(Color.TRANSPARENT)

                    //Xử lý nút được chọn
                    musicImage.setImageResource(R.drawable.ic_album_selected)
                    musicImage.setBackgroundResource(R.drawable.round_back_icon_selected)

                    //Xử lý animation
//                    val scaleAnimation = ScaleAnimation(1.0f, 1.0f, 0.8f, 1f)
//                    scaleAnimation.setDuration(200)
//                    scaleAnimation.setFillAfter(true)
//                    homeLayout.startAnimation(scaleAnimation)

                    //set selectedTab
                    selectedTab = 1
                }
            }
            else if(it == radioLayout)
            {
                if(selectedTab != 2)
                {
                    //set fragment
                    supportFragmentManager.beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.fragmentContainer, RadioFragment())
                        .commit()

                    //Xử lý các nút không được chọn
                    musicImage.setImageResource(R.drawable.ic_album)
                    searchImage.setImageResource(R.drawable.ic_search)
                    libraryImage.setImageResource(R.drawable.ic_library_music)

                    musicImage.setBackgroundColor(Color.TRANSPARENT)
                    searchImage.setBackgroundColor(Color.TRANSPARENT)
                    libraryImage.setBackgroundColor(Color.TRANSPARENT)

                    //Xử lý nút được chọn
                    radioImage.setImageResource(R.drawable.ic_radio_selected)
                    radioImage.setBackgroundResource(R.drawable.round_back_icon_selected)

                    //Xử lý animation
//                    val scaleAnimation = ScaleAnimation(1.0f, 1.0f, 0.8f, 1f)
//                    scaleAnimation.setDuration(200)
//                    scaleAnimation.setFillAfter(true)
//                    homeLayout.startAnimation(scaleAnimation)

                    //set selectedTab
                    selectedTab = 2
                }
            }
            else if(it == searchLayout)
            {
                if(selectedTab != 3)
                {
                    //set fragment
                    supportFragmentManager.beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.fragmentContainer, SearchFragment())
                        .commit()

                    //Xử lý các nút không được chọn
                    musicImage.setImageResource(R.drawable.ic_album)
                    radioImage.setImageResource(R.drawable.ic_radio)
                    libraryImage.setImageResource(R.drawable.ic_library_music)

                    musicImage.setBackgroundColor(Color.TRANSPARENT)
                    radioImage.setBackgroundColor(Color.TRANSPARENT)
                    libraryImage.setBackgroundColor(Color.TRANSPARENT)

                    //Xử lý nút được chọn
                    searchImage.setImageResource(R.drawable.ic_search_selected)
                    searchImage.setBackgroundResource(R.drawable.round_back_icon_selected)

                    //Xử lý animation
//                    val scaleAnimation = ScaleAnimation(1.0f, 1.0f, 0.8f, 1f)
//                    scaleAnimation.setDuration(200)
//                    scaleAnimation.setFillAfter(true)
//                    searchLayout.startAnimation(scaleAnimation)

                    //set selectedTab
                    selectedTab = 3
                }
            }
            else if(it == libraryLayout)
            {
                if(selectedTab != 4)
                {
                    //set fragment
                    supportFragmentManager.beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.fragmentContainer, LibraryFragment())
                        .commit()

                    //Xử lý các nút không được chọn
                    musicImage.setImageResource(R.drawable.ic_album)
                    radioImage.setImageResource(R.drawable.ic_radio)
                    searchImage.setImageResource(R.drawable.ic_search)

                    musicImage.setBackgroundColor(Color.TRANSPARENT)
                    radioImage.setBackgroundColor(Color.TRANSPARENT)
                    searchImage.setBackgroundColor(Color.TRANSPARENT)

                    //Xử lý nút được chọn
                    libraryImage.setImageResource(R.drawable.ic_library_music_selected)
                    libraryImage.setBackgroundResource(R.drawable.round_back_icon_selected)

                    //Xử lý animation
//                    val scaleAnimation = ScaleAnimation(1.0f, 1.0f, 0.8f, 1f)
//                    scaleAnimation.setDuration(200)
//                    scaleAnimation.setFillAfter(true)
//                    libraryLayout.startAnimation(scaleAnimation)

                    //set selectedTab
                    selectedTab = 4
                }
            }
        }

        musicLayout.setOnClickListener(nhanChuyenManHinh)
        radioLayout.setOnClickListener(nhanChuyenManHinh)
        searchLayout.setOnClickListener(nhanChuyenManHinh)
        libraryLayout.setOnClickListener(nhanChuyenManHinh)

    }


}