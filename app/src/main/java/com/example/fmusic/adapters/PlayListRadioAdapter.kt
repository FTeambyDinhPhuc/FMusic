package com.example.fmusic.adapters


import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fmusic.R
import com.example.fmusic.activity.PlayRadioActivity
import com.example.fmusic.models.BaiHatModel
import com.example.fmusic.models.RadioModel
import com.squareup.picasso.Picasso

class PlayListRadioAdapter(listRadio: ArrayList<RadioModel>):RecyclerView.Adapter<PlayListRadioAdapter.ViewHold>() {
    private var mListRadio: ArrayList<RadioModel> = listRadio

    inner class ViewHold(itemView : View) : RecyclerView.ViewHolder(itemView){
        val imgBaiHatItem : ImageView = itemView.findViewById(R.id.imgBaiHatItem)
        val txtTenBaiHatItem : TextView = itemView.findViewById(R.id.txtTenBaiHatItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHold {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_bai_hat, parent, false)
        return ViewHold(view)
    }

    override fun onBindViewHolder(holder: ViewHold, position: Int) {
        Picasso.get().load(mListRadio[position].hinhradio).into(holder.imgBaiHatItem)
        holder.txtTenBaiHatItem.setText(mListRadio[position].tenradio)
        holder.itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                val intent = Intent(view!!.context, PlayRadioActivity::class.java)
                intent.putExtra("listRadio", mListRadio)
                intent.putExtra("viTriRadio", holder.adapterPosition)
                view!!.context.startActivity(intent)
            }
        })
    }

    override fun getItemCount(): Int {
        return mListRadio.size
    }
}