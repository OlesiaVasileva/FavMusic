package com.olesix.favmusic.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.olesix.favmusic.R
import com.olesix.favmusic.model.AlbumModel
import com.squareup.picasso.Picasso

class AlbumAdapter() :
    PagedListAdapter<AlbumModel, AlbumAdapter.AlbumViewHolder>(AlbumComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return AlbumViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val albumModel: AlbumModel? = getItem(position)
        if (albumModel != null) {
            holder.bind(albumModel)
        }
    }

    class AlbumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.txt_title)
        private val yearView: TextView = itemView.findViewById(R.id.txt_year)
        private val labelView: TextView = itemView.findViewById(R.id.txt_label)
        private val thumbView: ImageView = itemView.findViewById(R.id.image_album)

        fun bind(album: AlbumModel) {
            if (album.thumb != null && album.thumb != "") {
                Log.d("LogTag", album.thumb)
                Picasso.get().load(album.thumb).into(thumbView)
            } else {
                thumbView.setImageDrawable(
                    ContextCompat.getDrawable(
                        itemView.context,
                        R.drawable.no_image_available
                    )
                )
            }
            textView.text = album.title
            yearView.text = album.year.toString()
            labelView.text = album.label
        }
    }

    companion object {
        private val AlbumComparator = object :
            DiffUtil.ItemCallback<AlbumModel>() {
            override fun areItemsTheSame(oldItem: AlbumModel, newItem: AlbumModel) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: AlbumModel, newItem: AlbumModel) =
                oldItem == newItem
        }
    }
}