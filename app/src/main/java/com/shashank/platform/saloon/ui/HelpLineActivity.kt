package com.shashank.platform.saloon.ui

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.shashank.platform.saloon.R
import com.shashank.platform.saloon.databinding.ActivityHelpLineBinding
import java.io.IOException
import java.util.*

class HelpLineActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHelpLineBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_help_line)

        supportActionBar?.title = "HelpLine"
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_help_line)

        binding.txtCall.setOnClickListener {
            phoneCall(binding.txtCall.text.toString())
        }
        binding.txtCall1.setOnClickListener {
            phoneCall(binding.txtCall1.text.toString())
        }
    }

    private fun phoneCall(selectedNumber: String) {
        val dialIntent = Intent(Intent.ACTION_DIAL)
        dialIntent.data = Uri.parse("tel:$selectedNumber")
        startActivity(dialIntent)
    }


}