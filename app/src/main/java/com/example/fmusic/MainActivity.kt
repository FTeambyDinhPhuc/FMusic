package com.example.fmusic

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.fmusic.fragments.*

class MainActivity : AppCompatActivity() {

    private lateinit var musicLayout: LinearLayout
    private lateinit var radioLayout: LinearLayout
    private lateinit var searchLayout: LinearLayout
    private lateinit var libraryLayout: LinearLayout

    private lateinit var musicImage: ImageView
    private lateinit var radioImage: ImageView
    private lateinit var searchImage: ImageView
    private lateinit var libraryImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AnhXaView()
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

        //set fragment mặc định hiển thị
        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.fragmentContainer, MusicFragment())
            .commit()

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