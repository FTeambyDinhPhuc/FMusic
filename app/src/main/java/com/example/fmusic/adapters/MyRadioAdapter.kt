package com.example.fmusic.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.example.fmusic.MainActivity
import com.example.fmusic.R
import com.example.fmusic.activity.PlayRadioActivity
import com.example.fmusic.fragments.MyRadioFragment
import com.example.fmusic.fragments.PlayListFragment
import com.example.fmusic.models.*
import com.example.fmusic.service_api.APIService
import com.example.fmusic.service_api.Dataservice
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyRadioAdapter(listRadio: ArrayList<RadioModel>, private var context: FragmentActivity?): RecyclerView.Adapter<MyRadioAdapter.ViewHold>() {

    private var mListMyRadio: ArrayList<RadioModel> = listRadio

    inner class ViewHold(itemView : View) : RecyclerView.ViewHolder(itemView){
        val txtTenRadioItem : TextView = itemView.findViewById(R.id.txtTenRadioItem)
        val txtTenTacGiaRadio : TextView = itemView.findViewById(R.id.txtTaiKhoanRadioItem)
        val btnXoa : ImageButton = itemView.findViewById(R.id.btnDeleteRadio)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHold {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_my_radio, parent, false)
        return ViewHold(view)
    }

    override fun onBindViewHolder(holder: ViewHold, position: Int) {
        holder.txtTenRadioItem.setText(mListMyRadio[position].tenradio)
        holder.txtTenTacGiaRadio.setText(MainActivity.mTaiKhoan.tenhienthi)
        holder.itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                val intent = Intent(view!!.context, PlayRadioActivity::class.java)
                intent.putExtra("listRadio", mListMyRadio)
                intent.putExtra("viTriRadio", holder.adapterPosition)
                view!!.context.startActivity(intent)
            }
        })

        holder.btnXoa.setOnClickListener{
            xoaRadio(mListMyRadio[position].id_radio, position)
        }
    }

    override fun getItemCount(): Int {
        return mListMyRadio.size
    }


    private fun xoaRadio(idRadio: Int, position: Int){
        val dataservice: Dataservice = APIService.getService
        val retrofitData = dataservice.xoaRadio(idRadio)
        retrofitData.enqueue(object : Callback<ResponseModel> {
            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                val responseBody: ResponseModel? = response.body()
                if (responseBody != null) {
                    if (!responseBody.error) {
                        mListMyRadio.removeAt(position)
                        notifyDataSetChanged()
                    }
                    Toast.makeText(
                        context,
                        responseBody.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
            }


        })
    }


}