package com.dias.kisahnabiapp.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dias.kisahnabiapp.R
import com.dias.kisahnabiapp.data.KisahResponse
import com.dias.kisahnabiapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding as ActivityDetailBinding

    companion object {
        const val DATA_RESPONSE = "data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val kisah = intent.getParcelableExtra<KisahResponse>(DATA_RESPONSE) as KisahResponse

        supportActionBar?.title = getString(R.string.title_detail)

        binding.apply {
            detailNama.text = kisah.name
            detailDesk.text = kisah.description
            detailTahun.text = kisah.thnKelahiran
            detailTempat.text = kisah.tmp
            detailUsia.text = kisah.usia
            Glide.with(this@DetailActivity).load(kisah.imageUrl).into(detailImage)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}