package com.example.journalapp

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.firebase.Timestamp


data class Journal(
    val title:String,
    val thoughts: String,
    val imageUrl: String,

    val userId:String,
    val timeAdded: Timestamp,
    var username: String

){
    // Binding Adapter
    // images to display into imageviews in custom views
    object DataBindingAdapter{
        @BindingAdapter("imageUrl")
        @JvmStatic
        fun setImageByRes(imageView: ImageView, imageUrl: String){
            Glide.with(imageView.context)
                .load(imageUrl.toUri().buildUpon().scheme("https").build())
                .into(imageView)

        }
    }
}
