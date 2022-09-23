package com.demoalbum

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load



@BindingAdapter("bind:image_url")
fun loadImage(imageView: ImageView, url: String?) {
    imageView.load(url)
}