package com.shashank.platform.saloon.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.shashank.platform.saloon.BR
import com.shashank.platform.saloon.R
import com.shashank.platform.saloon.adapter.AssignedLeadsAdapter
import com.shashank.platform.saloon.base.BaseActivity
import com.shashank.platform.saloon.databinding.ActivityAssignedLeadsBinding
import com.shashank.platform.saloon.model.AssignLeads
import com.shashank.platform.saloon.model.UserLeadsResponseByUserIdItem
import com.shashank.platform.saloon.viewmodel.UserLeadsViewModel
import java.util.*


class AssignedLeadsActivity : BaseActivity<ActivityAssignedLeadsBinding, UserLeadsViewModel>() {

    private lateinit var binding: ActivityAssignedLeadsBinding
    private lateinit var userAdapter: AssignedLeadsAdapter
    private val userList = mutableListOf<AssignLeads>()
    private lateinit var mViewModel: UserLeadsViewModel
    private lateinit var mDialog: SweetAlertDialog//declare this dialog globally
    private val LOCATION_PERMISSION_REQUEST_CODE = 123
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var lastLocation: Location? = null
    var addresses: String? = null

    override fun getBindingVariable(): Int {
        return BR.userLeadsViewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_assigned_leads
    }

    override fun getViewModel(): UserLeadsViewModel {
        mViewModel = ViewModelProvider(this)[UserLeadsViewModel::class.java]
        return mViewModel
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = getViewDataBinding()
        binding.userLeadsViewModel = mViewModel
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = "Assigned Leads"
        // Initialize the Places SDK

        window.statusBarColor = ContextCompat.getColor(
            applicationContext, R.color.colorAccent
        )
        supportActionBar?.setBackgroundDrawable(
            ContextCompat.getDrawable(
                this, R.drawable.actionbar_color
            )
        )

        addresses = intent.getStringExtra("address")
        // setupRecyclerView()

        populateUserList()

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    lastLocation = location
                    Log.d("locationLattitue", "locationLattitue: $location")
                } else {
                    // Handle case where last known location is not available
                }
            }


        mViewModel.allApplicationObserver.observe(this) {
            if (it.isNullOrEmpty()) {
                mDialog.dismiss()
                binding.emptyImage.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE
                Log.d("setupRecyclerView", "setupRecyclerView: $it")
            } else {
                mDialog.dismiss()
                binding.emptyImage.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
                Log.d("setupRecyclerView", "setupRecyclerView: $it")
                setupRecyclerView(it)
            }
        }
    }

    private fun setupRecyclerView(userLeadsResponseByUserIdItem: List<UserLeadsResponseByUserIdItem>) {
        userAdapter =
            AssignedLeadsAdapter(
                this,
                userLeadsResponseByUserIdItem,
                mViewModel,
                lastLocation,
                addresses!!
            )
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = userAdapter
    }

    private fun populateUserList() {
        // Populate your user list here, for example:
        mDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        mDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
        mDialog.setTitleText("Loading ...")
        mDialog.setCancelable(false)
        mDialog.show()
        mViewModel.getLeadsDatabyUserId()
        // Notify the adapter that the data set has changed
    }


    private fun showSnackbar(
        mainTextStringId: String, actionStringId: String, listener: View.OnClickListener
    ) {
        Toast.makeText(this@AssignedLeadsActivity, mainTextStringId, Toast.LENGTH_LONG).show()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_leave -> {
                val intent = Intent(this, ApplyLeaveActivity::class.java)
                startActivity(intent)
                return true
            }
            android.R.id.home -> {
                val intent = Intent(this@AssignedLeadsActivity, MainActivity::class.java)
                startActivity(intent)
            }

        }
        return super.onOptionsItemSelected(item)
    }


}