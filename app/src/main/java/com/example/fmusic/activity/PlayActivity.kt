package com.example.fmusic.activity

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.*
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fmusic.MainActivity
import com.example.fmusic.R
import com.example.fmusic.adapters.PlayListAdapter
import com.example.fmusic.models.*
import com.example.fmusic.service_api.APIService
import com.example.fmusic.service_api.Dataservice
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.util.concurrent.TimeUnit

class PlayActivity : AppCompatActivity() {
    private lateinit var imgPlay: ImageView
    private lateinit var txtTenBaiHatPlay: TextView
    private lateinit var txtTenCaSiPlay: TextView
    private lateinit var txtCurrentDurationMusic: TextView
    private lateinit var txtDurationMusic: TextView
    private lateinit var seekBar: SeekBar
    private lateinit var btnPlay: ImageButton
    private lateinit var btnBack: ImageButton
    private lateinit var btnPrevious: ImageButton
    private lateinit var btnNext: ImageButton
    private lateinit var btnYeuThich: ImageButton
    private var baiHatYeuThich: Boolean = false
    private var viTriBaiHat: Int = 0
    private lateinit var runnable: Runnable
    private  var handler = Handler()
    private var mediaPlayer: MediaPlayer = MainActivity.mediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)

        AnhXa()

        val listBaiHat: ArrayList<BaiHatModel>? = intent.getParcelableArrayListExtra("listBaiHat")
        viTriBaiHat = intent.getIntExtra("viTriBaiHat", 0)

        if(listBaiHat != null){
            CreateMediaPlayer(listBaiHat)
            SetLayout(listBaiHat)
            SetSeekBar()
            XuKienNhanNut(listBaiHat)
            setCompletionMusic(listBaiHat)
        }

    }

    private fun AnhXa(){
        imgPlay = findViewById(R.id.ImgPlay)
        btnPlay = findViewById(R.id.btnPlay)
        seekBar = findViewById(R.id.sbPlayScreen)
        txtTenBaiHatPlay = findViewById(R.id.txtTenBaiHatPlay)
        txtTenCaSiPlay = findViewById(R.id.txtTenCaSiPlay)
        btnBack = findViewById(R.id.btnBackFromPlayScreen)
        btnPrevious = findViewById(R.id.btnPreviousFromPlayScreen)
        btnNext = findViewById(R.id.btnNextFromPlayScreen)
        txtDurationMusic = findViewById(R.id.txtDurationMusic)
        txtCurrentDurationMusic = findViewById(R.id.txtCurrentDurationMusic)
        btnYeuThich = findViewById(R.id.btnYeuThichPlay)
    }
    private fun kiemTraYeuThich(listBaiHat: ArrayList<BaiHatModel>){
        val dataservice: Dataservice = APIService.getService
        val retrofitData = dataservice.getALlBaiHatYeuThichByTK(MainActivity.mTaiKhoan.id_taikhoan)
        retrofitData.enqueue(object : Callback<List<BaiHatModel>> {
            override fun onResponse(
                call: Call<List<BaiHatModel>>,
                response: Response<List<BaiHatModel>>
            ) {
                val listBaiHatYeuThich: List<BaiHatModel>? = response.body()
                if(listBaiHatYeuThich != null){
                    baiHatYeuThich =  false
                    if(listBaiHatYeuThich.size != 0){
                        listBaiHatYeuThich.forEach {
                            if( it.id_baihat == listBaiHat[viTriBaiHat].id_baihat){
                                baiHatYeuThich =  true
                            }
                        }
                    }
                    if(baiHatYeuThich){
                        btnYeuThich.setImageResource(R.drawable.ic_favorite_selected)
                    }else{
                        btnYeuThich.setImageResource(R.drawable.ic_favorite_border)
                    }
                }
            }

            override fun onFailure(call: Call<List<BaiHatModel>>, t: Throwable) {
                Toast.makeText(this@PlayActivity, t.message, Toast.LENGTH_SHORT).show()
            }

        })
    }
    private fun SetLayout(listBaiHat: ArrayList<BaiHatModel>){
        kiemTraYeuThich(listBaiHat)
        GetListCaSiByBaiHat(listBaiHat[viTriBaiHat].id_baihat)
        Picasso.get().load(listBaiHat[viTriBaiHat].hinhbaihat).into(imgPlay)
        txtTenBaiHatPlay.setText(listBaiHat[viTriBaiHat].tenbaihat)
        txtDurationMusic.setText(FormatDuration(mediaPlayer.duration.toLong()))
        btnPlay.setImageResource(R.drawable.ic_pause)
    }

    private fun CreateMediaPlayer(listBaiHat: ArrayList<BaiHatModel>){
        try{
            if(mediaPlayer == null){
                mediaPlayer = MediaPlayer()
            }
            mediaPlayer.reset()
            mediaPlayer.setDataSource(this,listBaiHat[viTriBaiHat].duongdannhac.toUri())
            mediaPlayer.prepare()
            mediaPlayer.start()
        }catch (e: Exception){
            return
        }
    }

    private fun SetSeekBar(){
        seekBar.progress = 0
        seekBar.max = mediaPlayer.duration
        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if(fromUser){
                    mediaPlayer.seekTo(progress)
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        })
        runnable = Runnable {
            seekBar.progress = mediaPlayer.currentPosition
            txtCurrentDurationMusic.setText(FormatDuration(mediaPlayer.currentPosition.toLong()))
            handler.postDelayed(runnable,1000 )
        }
        handler.postDelayed(runnable, 1000)
    }

    private fun XuKienNhanNut(listBaiHat: ArrayList<BaiHatModel>){
        btnPlay.setOnClickListener{
            if(!mediaPlayer.isPlaying){
                mediaPlayer.start()
                btnPlay.setImageResource(R.drawable.ic_pause)
            }else{
                mediaPlayer.pause()
                btnPlay.setImageResource(R.drawable.ic_play_arrow)
            }
        }
        btnBack.setOnClickListener{
            finish()
        }

        btnNext.setOnClickListener{
            NextSong(listBaiHat)
        }

        btnPrevious.setOnClickListener{
            PreviousSong(listBaiHat)
        }

        btnYeuThich.setOnClickListener{
            if(baiHatYeuThich){
                XoaBaiHatYeuThich(listBaiHat)
            }else{
                ThemBaiHatYeuThich(listBaiHat)
            }
        }
    }

    private fun setCompletionMusic(listBaiHat: ArrayList<BaiHatModel>){
        mediaPlayer.setOnCompletionListener {
            if(listBaiHat.size - 1 == viTriBaiHat){
                btnPlay.setImageResource(R.drawable.ic_play_arrow)
                seekBar.progress = 0
            }else{
                NextSong(listBaiHat)
            }

        }
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
                    txtTenCaSiPlay.setText(listCaSi[0].tencasi)
                }
            }
            override fun onFailure(call: Call<List<CaSiModel>>, t: Throwable) {
                Toast.makeText(this@PlayActivity, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun FormatDuration(duration: Long): String{
        val minutes = TimeUnit.MINUTES.convert(duration, TimeUnit.MILLISECONDS)
        val seconds = TimeUnit.SECONDS.convert(duration, TimeUnit.MILLISECONDS) - minutes*TimeUnit.SECONDS.convert(1, TimeUnit.MINUTES)
        return String.format("%02d:%02d", minutes, seconds)
    }


    private fun NextSong(listBaiHat: ArrayList<BaiHatModel>){
        if(listBaiHat.size - 1 == viTriBaiHat){
            viTriBaiHat = 0
        }else{
            ++viTriBaiHat
        }
        CreateMediaPlayer(listBaiHat)
        SetLayout(listBaiHat)
        SetSeekBar()
    }

    private fun PreviousSong(listBaiHat: ArrayList<BaiHatModel>){
        if(0 == viTriBaiHat){
            viTriBaiHat = listBaiHat.size -1
        }else{
            --viTriBaiHat
        }
        CreateMediaPlayer(listBaiHat)
        SetLayout(listBaiHat)
        SetSeekBar()
    }

    private fun ThemBaiHatYeuThich(listBaiHat: ArrayList<BaiHatModel>){
        val dataservice: Dataservice = APIService.getService
        val retrofitData = dataservice.themBaiHatYeuThich(MainActivity.mTaiKhoan.id_taikhoan, listBaiHat[viTriBaiHat].id_baihat)
        retrofitData.enqueue(object : Callback<ResponseModel> {
            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                val responseBody: ResponseModel? = response.body()
                if (responseBody != null) {
                    if (!responseBody.error) {
                        kiemTraYeuThich(listBaiHat)
                    }
                    Toast.makeText(
                        this@PlayActivity,
                        responseBody.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                Toast.makeText(this@PlayActivity, t.message, Toast.LENGTH_SHORT).show()
            }


        })
    }

    private fun XoaBaiHatYeuThich(listBaiHat: ArrayList<BaiHatModel>){
        val dataservice: Dataservice = APIService.getService
        val retrofitData = dataservice.xoaBaiHatYeuThich(MainActivity.mTaiKhoan.id_taikhoan, listBaiHat[viTriBaiHat].id_baihat)
        retrofitData.enqueue(object : Callback<ResponseModel> {
            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                val responseBody: ResponseModel? = response.body()
                if (responseBody != null) {
                    if (!responseBody.error) {
                        kiemTraYeuThich(listBaiHat)
                    }
                    Toast.makeText(
                        this@PlayActivity,
                        responseBody.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                Toast.makeText(this@PlayActivity, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

}