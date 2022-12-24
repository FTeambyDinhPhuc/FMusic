package com.example.fmusic.activity

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.net.toUri
import com.example.fmusic.MainActivity
import com.example.fmusic.R
import com.example.fmusic.models.BaiHatModel
import com.example.fmusic.models.RadioModel
import com.squareup.picasso.Picasso
import java.util.concurrent.TimeUnit

class PlayRadioActivity : AppCompatActivity() {

    private lateinit var imgPlayRadio: ImageView
    private lateinit var txtTenRadioPlay: TextView
    private lateinit var txtCurrentDurationRadio: TextView
    private lateinit var txtDurationRadio: TextView
    private lateinit var seekBarRadio: SeekBar
    private lateinit var btnPlayRadio: ImageButton
    private lateinit var btnBackRadio: ImageButton
    private lateinit var btnPreviousRadio: ImageButton
    private lateinit var btnNextRadio: ImageButton

    private var viTriRadio: Int = 0
    private lateinit var runnable: Runnable
    private  var handler = Handler()
    private var mediaPlayer: MediaPlayer = MainActivity.mediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_radio)
        AnhXa()

        val listRadio: ArrayList<RadioModel>? = intent.getParcelableArrayListExtra("listRadio")
        viTriRadio = intent.getIntExtra("viTriRadio", 0)

        if(listRadio != null){
            CreateMediaPlayer(listRadio)
            SetLayout(listRadio)
            SetSeekBar()
            XuKienNhanNut(listRadio)
            setCompletionRadio(listRadio)
        }
    }

    private fun AnhXa(){
        imgPlayRadio = findViewById(R.id.imgPlayRadio)
        txtTenRadioPlay = findViewById(R.id.txtTenBaiHatPlayRadio)
        txtCurrentDurationRadio = findViewById(R.id.txtCurrentDurationRadio)
        txtDurationRadio = findViewById(R.id.txtDurationRadio)
        seekBarRadio = findViewById(R.id.sbPlayRadioScreen)
        btnPlayRadio = findViewById(R.id.btnPlayRadio)
        btnBackRadio = findViewById(R.id.btnBackFromPlayRadioScreen)
        btnPreviousRadio = findViewById(R.id.btnPreviousFromPlayRadioScreen)
        btnNextRadio = findViewById(R.id.btnNextFromPlayRadioScreen)

    }

    private fun SetLayout(listRadio: ArrayList<RadioModel>){
        //GetListCaSiByBaiHat(listBaiHat[viTriBaiHat].id_baihat)
        Picasso.get().load(listRadio[viTriRadio].hinhradio).into(imgPlayRadio)
        txtTenRadioPlay.setText(listRadio[viTriRadio].tenradio)
        txtDurationRadio.setText(FormatDuration(mediaPlayer.duration.toLong()))
        btnPlayRadio.setImageResource(R.drawable.ic_pause)
    }

    private fun FormatDuration(duration: Long): String{
        val minutes = TimeUnit.MINUTES.convert(duration, TimeUnit.MILLISECONDS)
        val seconds = TimeUnit.SECONDS.convert(duration, TimeUnit.MILLISECONDS) - minutes* TimeUnit.SECONDS.convert(1, TimeUnit.MINUTES)
        return String.format("%02d:%02d", minutes, seconds)
    }


    private fun CreateMediaPlayer(listRadio: ArrayList<RadioModel>){
        try{
            if(mediaPlayer == null){
                mediaPlayer = MediaPlayer()
            }
            mediaPlayer.reset()
            mediaPlayer.setDataSource(this,listRadio[viTriRadio].duongdanradio.toUri())
            mediaPlayer.prepare()
            mediaPlayer.start()
        }catch (e: Exception){
            return
        }
    }

    private fun SetSeekBar(){
        seekBarRadio.progress = 0
        seekBarRadio.max = mediaPlayer.duration
        seekBarRadio.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if(fromUser){
                    mediaPlayer.seekTo(progress)
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        })
        runnable = Runnable {
            seekBarRadio.progress = mediaPlayer.currentPosition
            txtCurrentDurationRadio.setText(FormatDuration(mediaPlayer.currentPosition.toLong()))
            handler.postDelayed(runnable,1000 )
        }
        handler.postDelayed(runnable, 1000)
    }

    private fun XuKienNhanNut(listRadio: ArrayList<RadioModel>){
        btnPlayRadio.setOnClickListener{
            if(!mediaPlayer.isPlaying){
                mediaPlayer.start()
                btnPlayRadio.setImageResource(R.drawable.ic_pause)
            }else{
                mediaPlayer.pause()
                btnPlayRadio.setImageResource(R.drawable.ic_play_arrow)
            }
        }
        btnBackRadio.setOnClickListener{
            finish()
        }

        btnNextRadio.setOnClickListener{
            NextRadio(listRadio)
        }

        btnPreviousRadio.setOnClickListener{
            PreviousRadio(listRadio)
        }
    }

    private fun NextRadio(listRadio: ArrayList<RadioModel>){
        if(listRadio.size - 1 == viTriRadio){
            viTriRadio = 0
        }else{
            ++viTriRadio
        }
        CreateMediaPlayer(listRadio)
        SetLayout(listRadio)
        SetSeekBar()
    }

    private fun PreviousRadio(listRadio: ArrayList<RadioModel>){
        if(0 == viTriRadio){
            viTriRadio = listRadio.size -1
        }else{
            --viTriRadio
        }
        CreateMediaPlayer(listRadio)
        SetLayout(listRadio)
        SetSeekBar()
    }

    private fun setCompletionRadio(listRadio: ArrayList<RadioModel>){
        mediaPlayer.setOnCompletionListener {
            if(listRadio.size - 1 == viTriRadio){
                btnPlayRadio.setImageResource(R.drawable.ic_play_arrow)
                seekBarRadio.progress = 0
            }else{
                NextRadio(listRadio)
            }
        }
    }


}