package com.example.fmusic.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.fmusic.MainActivity
import com.example.fmusic.R
import com.example.fmusic.activity.PlayActivity
import com.example.fmusic.models.AlbumModel
import com.squareup.picasso.Picasso

class AlbumForYouRVAdapter(private var listAlbum: List<AlbumModel>): RecyclerView.Adapter<AlbumForYouRVAdapter.ViewHold>() {
    inner class ViewHold(itemView : View) : RecyclerView.ViewHolder(itemView){
        val imgAlbumItem : ImageView = itemView.findViewById(R.id.imgAlbumItem)
        val txtTenAlbumItem : TextView = itemView.findViewById(R.id.txtTenAlbumItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHold {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_album, parent, false)
        return ViewHold(view)
    }

    override fun onBindViewHolder(holder: ViewHold, position: Int) {
        Picasso.get().load(listAlbum[position].duongdanhinhalbum).into(holder.imgAlbumItem)
        holder.txtTenAlbumItem.setText(listAlbum[position].tenalbum)
        holder.itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                val intent = Intent(view!!.context, MainActivity::class.java)
                intent.putExtra("moManHinh", "PlayListFragment")
                intent.putExtra("listTheo", "Album")
                intent.putExtra("hinhList", listAlbum[holder.adapterPosition].duongdanhinhalbum)
                intent.putExtra("tenList", listAlbum[holder.adapterPosition].tenalbum)
                intent.putExtra("idList", listAlbum[holder.adapterPosition].id_album)
                view!!.context.startActivity(intent)
            }
        })
    }

    override fun getItemCount(): Int {
        return listAlbum.size
    }
}