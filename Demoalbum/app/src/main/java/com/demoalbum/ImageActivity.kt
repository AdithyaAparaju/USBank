package com.demoalbum

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.demoalbum.databinding.ActivityImageBinding
import com.demoalbum.databinding.ActivityPhotosBinding

class ImageActivity : AppCompatActivity() {

    private var url: String? = null
    private var albumTitle: String? = null
    private lateinit var binding: ActivityImageBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_image)
        url = intent?.extras?.getString("url")
        albumTitle = intent?.extras?.getString("title")

        binding.url = url
        binding.toolBar.tvName.text = albumTitle
        binding.toolBar.ivMenu.setOnClickListener {
            finish()
        }
    }
}