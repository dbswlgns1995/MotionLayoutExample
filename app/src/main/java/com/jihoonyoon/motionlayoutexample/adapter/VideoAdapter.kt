package com.jihoonyoon.motionlayoutexample.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jihoonyoon.motionlayoutexample.R
import com.jihoonyoon.motionlayoutexample.model.VideoModel

class VideoAdapter(val callback: (String, String) -> Unit): ListAdapter<VideoModel, VideoAdapter.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
        = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_video, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(currentList[position])
    }

    inner class ViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        fun bind(videoModel: VideoModel){
            view.findViewById<TextView>(R.id.titleTextView).text = videoModel.title
            view.findViewById<TextView>(R.id.subTitleTextView).text = videoModel.subtitle
            val thumbnailImageView = view.findViewById<ImageView>(R.id.thumbnailImageView)

            Glide.with(thumbnailImageView.context)
                .load(videoModel.thumb)
                .into(thumbnailImageView)

            view.setOnClickListener {
                callback(videoModel.sources, videoModel.title)
            }
        }
    }

    companion object {
        private val diffUtil = object: DiffUtil.ItemCallback<VideoModel>(){
            override fun areItemsTheSame(oldItem: VideoModel, newItem: VideoModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: VideoModel, newItem: VideoModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}