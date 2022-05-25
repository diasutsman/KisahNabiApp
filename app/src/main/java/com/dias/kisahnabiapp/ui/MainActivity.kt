package com.dias.kisahnabiapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dias.kisahnabiapp.data.KisahResponse
import com.dias.kisahnabiapp.databinding.ActivityMainBinding
import com.dias.kisahnabiapp.ui.detail.DetailActivity
import com.dias.kisahnabiapp.utils.OnItemClickCallback

class MainActivity : AppCompatActivity() {

    // best practice binding
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding as ActivityMainBinding

    private var _viewModel: MainViewModel? = null
    private val viewModel get() = _viewModel as MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        _viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.loadDataForView()

        viewModel.kisahResponse.observe(this) { showData(it) }

        viewModel.isLoading.observe(this) { showLoading(it) }

        viewModel.isError.observe(this) { showError(it) }

    }

    private fun showLoading(isLoading: Boolean?) {
        isLoading?.let {
            binding.progressMain.visibility = if (it) View.VISIBLE else View.INVISIBLE
            binding.recyclerMain.visibility = if (it) View.INVISIBLE else View.VISIBLE
        }
    }

    private fun showError(error: Throwable?) {
        binding.progressMain.visibility = View.INVISIBLE
        binding.recyclerMain.visibility = View.INVISIBLE
        Toast.makeText(this, error?.message, Toast.LENGTH_SHORT).show()
    }

    private fun showData(data: List<KisahResponse>?) {
        val mAdapter = NabiAdapter()
        mAdapter.setData(data)
        mAdapter.setOnItemClickCallback(object : OnItemClickCallback {
            override fun onItemClicked(data: KisahResponse) {
                startActivity(
                    Intent(this@MainActivity, DetailActivity::class.java)
                        .putExtra(DetailActivity.DATA_RESPONSE, data)
                )
            }

        })
        binding.recyclerMain.adapter = mAdapter
    }
}