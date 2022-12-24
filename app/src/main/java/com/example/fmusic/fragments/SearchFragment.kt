package com.example.fmusic.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fmusic.R
import com.example.fmusic.adapters.PlayListAdapter
import com.example.fmusic.models.BaiHatModel
import com.example.fmusic.service_api.APIService
import com.example.fmusic.service_api.Dataservice
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment() {
    private lateinit var mView: View
    private lateinit var searchView: SearchView
    private lateinit var rvListBaiHatSearch: RecyclerView
    private lateinit var playListSearchAdapter: PlayListAdapter
    private lateinit var txtSoLuongBaiHatTimDuoc: TextView
    private var mListBaiHat: List<BaiHatModel>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView= inflater.inflate(R.layout.fragment_search, container, false)
        AnhXa()
        GetListAllBaiHat()
        XuLyNutNhan()
        return mView
    }



    private fun AnhXa() {
        searchView = mView.findViewById(R.id.searchPlaylistBaiHat)
        rvListBaiHatSearch = mView.findViewById(R.id.rvPlaylistSearch)
        txtSoLuongBaiHatTimDuoc = mView.findViewById(R.id.txtSoLuongBaiHatListSeach)
    }

    private fun XuLyNutNhan() {
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return false
            }
        })
    }

    private fun filterList(newText: String?){
        var filteredList: MutableList<BaiHatModel>  = ArrayList<BaiHatModel>()
        mListBaiHat!!.forEach{
            if (it.tenbaihat.toLowerCase().contains(newText!!.toLowerCase())){
                filteredList.add(it)
            }
        }
        if (filteredList.isEmpty()){
            Toast.makeText(activity, "Không tìm thấy bài hát", Toast.LENGTH_SHORT).show()
        }else{
            playListSearchAdapter.setFilteredList(filteredList as ArrayList<BaiHatModel>)
            txtSoLuongBaiHatTimDuoc.setText(filteredList.size.toString())
        }
    }

    private fun GetListAllBaiHat(){
        val dataservice: Dataservice = APIService.getService
        val retrofitData = dataservice.getAllBaiHat()
        retrofitData.enqueue(object : Callback<List<BaiHatModel>> {
            override fun onResponse(
                call: Call<List<BaiHatModel>>,
                response: Response<List<BaiHatModel>>
            ) {
                mListBaiHat = response.body()
                if(mListBaiHat != null){
                    rvListBaiHatSearch.layoutManager = LinearLayoutManager(activity)
                    playListSearchAdapter = PlayListAdapter(mListBaiHat as ArrayList<BaiHatModel>)
                    rvListBaiHatSearch.adapter = playListSearchAdapter
                    txtSoLuongBaiHatTimDuoc.setText(mListBaiHat!!.size.toString())
                }
            }
            override fun onFailure(call: Call<List<BaiHatModel>>, t: Throwable) {
                Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }


}