package com.demoalbum.photos

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.demoalbum.CustomProgressDialog
import com.demoalbum.ImageActivity
import com.demoalbum.MyViewModelFactory
import com.demoalbum.R
import com.demoalbum.databinding.ActivityPhotosBinding
import com.demoalbum.network.ApiService
import com.demoalbum.network.NetworkConnectionInterceptor

class PhotosActivity : AppCompatActivity(), PhotoItemClickListener {

    private lateinit var progressDialog: CustomProgressDialog
    private var viewModel: PhotosViewModel? = null
    private lateinit var binding: ActivityPhotosBinding
    private var albumId: String? = null
    private var albumTitle: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_photos)
        val networkConnectionInterceptor = NetworkConnectionInterceptor(this)
        val apiService = ApiService(networkConnectionInterceptor)
        val viewModelFactory = MyViewModelFactory(apiService)
        viewModel = ViewModelProvider(this, viewModelFactory)[PhotosViewModel::class.java]

        binding.viewModel = viewModel
        progressDialog = CustomProgressDialog(this)

        viewModel?.isApiException?.observe(this) {
            Toast.makeText(this, getString(R.string.something_wrong), Toast.LENGTH_LONG).show()
        }

        viewModel?.isNetworkError?.observe(this) {
            Toast.makeText(this, getString(R.string.no_data_network), Toast.LENGTH_LONG).show()
        }


        viewModel?.progressDialog?.observe(this) {
            if (it) progressDialog.show() else progressDialog.dismiss()
        }

        albumId = intent?.extras?.getString("id")
        albumTitle = intent?.extras?.getString("title")

        binding.toolBar.tvName.text = albumTitle

        binding.toolBar.ivMenu.setOnClickListener {
            finish()
        }
        viewModel?.getListOfImages()
        viewModel?.imagesList?.observe(this) { list ->
            val filteredList: List<PhotosData>? = list?.filter { s -> s.albumId == albumId }
            binding.rvList.apply {
                adapter = list?.let { PhotosAdapter(filteredList!!, this@PhotosActivity) }
                layoutManager = GridLayoutManager(applicationContext, 2)
            }
        }
    }

    override fun onPhotoClick(photosData: PhotosData) {
        val intent = Intent(this@PhotosActivity, ImageActivity::class.java)
        intent.putExtra("url", photosData.url)
        intent.putExtra("title", photosData.title)
        startActivity(intent)
    }
}