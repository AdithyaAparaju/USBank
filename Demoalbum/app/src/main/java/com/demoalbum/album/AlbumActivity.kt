package com.demoalbum.album

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.demoalbum.*
import com.demoalbum.databinding.ActivityAlbumBinding
import com.demoalbum.network.ApiService
import com.demoalbum.network.NetworkConnectionInterceptor
import com.demoalbum.photos.PhotosActivity

class AlbumActivity : AppCompatActivity(), AlbumItemClickListener {

    private lateinit var progressDialog: CustomProgressDialog
    private var viewModel: AlbumViewModel? = null
    private lateinit var binding: ActivityAlbumBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_album)
        val networkConnectionInterceptor = NetworkConnectionInterceptor(this)
        val apiService = ApiService(networkConnectionInterceptor)
        val viewModelFactory = MyViewModelFactory(apiService)
        viewModel = ViewModelProvider(this, viewModelFactory)[AlbumViewModel::class.java]

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

        binding.toolBar.ivMenu.visibility = View.GONE

        viewModel?.getListOfAlbums()

        viewModel?.albumList?.observe(this) { list ->
            binding.rvList.apply {
                adapter = list?.let { GalleryAdapter(it, this@AlbumActivity) }
                layoutManager = LinearLayoutManager(
                    context,
                    LinearLayoutManager.VERTICAL,
                    false
                )
                addItemDecoration(
                    DividerItemDecoration(
                        context,
                        DividerItemDecoration.VERTICAL
                    )
                )
            }
        }

    }

    override fun onAlbumClick(albumData: AlbumData) {
        val intent = Intent(this@AlbumActivity, PhotosActivity::class.java)
        intent.putExtra("id", albumData.userId)
        intent.putExtra("title", albumData.title)
        startActivity(intent)
    }
}