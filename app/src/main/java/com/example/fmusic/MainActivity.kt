package com.example.fmusic

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.fmusic.fragments.HomeFragment
import com.example.fmusic.fragments.LibraryFragment
import com.example.fmusic.fragments.SearchFragment

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        XuLyBottomNavigationBar()
    }

    private fun XuLyBottomNavigationBar(){
        var selectedTab: Int = 1
        val homeLayout = findViewById<LinearLayout>(R.id.homeLayout)
        val searchLayout = findViewById<LinearLayout>(R.id.searchLayout)
        val libraryLayout = findViewById<LinearLayout>(R.id.libraryLayout)

        val homeImage = findViewById<ImageView>(R.id.homeImage)
        val searchImage = findViewById<ImageView>(R.id.searchImage)
        val libraryImage = findViewById<ImageView>(R.id.libraryImage)

        //set fragment mặc định hiển thị
        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.fragmentContainer, HomeFragment())
            .commit()

        var nhanChuyenManHinh: View.OnClickListener? = null
        nhanChuyenManHinh = View.OnClickListener{
            if(it == homeLayout)
            {
                if(selectedTab != 1)
                {
                    //set fragment
                    supportFragmentManager.beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.fragmentContainer, HomeFragment())
                        .commit()

                    //Xử lý các nút không được chọn
                    searchImage.setImageResource(R.drawable.ic_search_24)
                    libraryImage.setImageResource(R.drawable.ic_library_music_24)

                    searchImage.setBackgroundColor(Color.TRANSPARENT)
                    libraryImage.setBackgroundColor(Color.TRANSPARENT)

                    //Xử lý nút được chọn
                    homeImage.setImageResource(R.drawable.ic_home_selected_24)
                    homeImage.setBackgroundResource(R.drawable.round_back_icon_selected)

                    //Xử lý animation
                    val scaleAnimation = ScaleAnimation(1.0f, 1.0f, 0.8f, 1f)
                    scaleAnimation.setDuration(200)
                    scaleAnimation.setFillAfter(true)
                    homeLayout.startAnimation(scaleAnimation)

                    //set selectedTab
                    selectedTab = 1
                }
            }
            else if(it == searchLayout)
            {
                if(selectedTab != 2)
                {
                    //set fragment
                    supportFragmentManager.beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.fragmentContainer, SearchFragment())
                        .commit()

                    //Xử lý các nút không được chọn
                    homeImage.setImageResource(R.drawable.ic_home_24)
                    libraryImage.setImageResource(R.drawable.ic_library_music_24)

                    homeImage.setBackgroundColor(Color.TRANSPARENT)
                    libraryImage.setBackgroundColor(Color.TRANSPARENT)

                    //Xử lý nút được chọn
                    searchImage.setImageResource(R.drawable.ic_search_selected_24)
                    searchImage.setBackgroundResource(R.drawable.round_back_icon_selected)

                    //Xử lý animation
                    val scaleAnimation = ScaleAnimation(1.0f, 1.0f, 0.8f, 1f)
                    scaleAnimation.setDuration(200)
                    scaleAnimation.setFillAfter(true)
                    searchLayout.startAnimation(scaleAnimation)

                    //set selectedTab
                    selectedTab = 2
                }
            }
            else if(it == libraryLayout)
            {
                if(selectedTab != 3)
                {
                    //set fragment
                    supportFragmentManager.beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.fragmentContainer, LibraryFragment())
                        .commit()

                    //Xử lý các nút không được chọn
                    homeImage.setImageResource(R.drawable.ic_home_24)
                    searchImage.setImageResource(R.drawable.ic_search_24)

                    homeImage.setBackgroundColor(Color.TRANSPARENT)
                    searchImage.setBackgroundColor(Color.TRANSPARENT)

                    //Xử lý nút được chọn
                    libraryImage.setImageResource(R.drawable.ic_library_music_selected_24)
                    libraryImage.setBackgroundResource(R.drawable.round_back_icon_selected)

                    //Xử lý animation
                    val scaleAnimation = ScaleAnimation(1.0f, 1.0f, 0.8f, 1f)
                    scaleAnimation.setDuration(200)
                    scaleAnimation.setFillAfter(true)
                    libraryLayout.startAnimation(scaleAnimation)

                    //set selectedTab
                    selectedTab = 3
                }
            }
        }

        homeLayout.setOnClickListener(nhanChuyenManHinh)
        searchLayout.setOnClickListener(nhanChuyenManHinh)
        libraryLayout.setOnClickListener(nhanChuyenManHinh)

    }
}