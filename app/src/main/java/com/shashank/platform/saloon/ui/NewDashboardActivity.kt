package com.shashank.platform.saloon.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.shashank.platform.saloon.R
import com.shashank.platform.saloon.adapter.ImagePagerAdapter
import com.shashank.platform.saloon.databinding.ActivityNewDashboardBinding
import com.shashank.platform.saloon.databinding.ActivityShowLeaveBinding

class NewDashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewDashboardBinding
    val imageResources = listOf(
        R.drawable.image4,
        R.drawable.image2,
        R.drawable.image3,
        R.drawable.imag5
        // Add more image resource IDs here
    )

    private var currentPage = 0
    private val autoSlideDelay: Long = 2000 // Auto slide every 3 seconds


    private val autoSlideRunnable = Runnable {
        if (currentPage == imageResources.size) {
            currentPage = 0
        }
        binding.viewPager.currentItem = currentPage
        currentPage++
        startAutoSliding()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.statusBarColor = getColor(R.color.colorAccent)
        }
        supportActionBar!!.setTitle("Dashboard")
        supportActionBar!!.setBackgroundDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.actionbar_color
            )
        )
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_new_dashboard)

        val imagePagerAdapter = ImagePagerAdapter(imageResources)
        binding.viewPager.adapter = imagePagerAdapter
        startAutoSliding()

        binding.cdViewleads.setOnClickListener(View.OnClickListener { view: View? ->
            val intent = Intent(
                applicationContext,
                AssignedLeadsActivity::class.java
            )
            startActivity(intent)
        })

        binding.cdViewproduct.setOnClickListener(View.OnClickListener { view: View? ->
            val intent = Intent(
                applicationContext,
                ViewListOfProductActivity::class.java
            )
            startActivity(intent)
        })

        binding.cdLeave.setOnClickListener(View.OnClickListener { view: View? ->
            val intent = Intent(
                applicationContext,
                ShowLeaveActivity::class.java
            )
            startActivity(intent)
        })


    }

    private fun startAutoSliding() {
        Handler().postDelayed(autoSlideRunnable, autoSlideDelay)

        // Schedule the auto-slide runnable
    }

    override fun onPause() {
        super.onPause()
        // Remove any pending callbacks to stop auto-sliding when the activity is paused
        Handler().removeCallbacks(autoSlideRunnable)
    }

    override fun onResume() {
        super.onResume()
        // Restart auto-sliding when the activity resumes
        startAutoSliding()
    }


}
