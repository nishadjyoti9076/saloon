package com.shashank.platform.saloon.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import cn.pedant.SweetAlert.SweetAlertDialog
import com.shashank.platform.saloon.BR
import com.shashank.platform.saloon.R
import com.shashank.platform.saloon.adapter.ShowLeaveAdapter
import com.shashank.platform.saloon.base.BaseActivity
import com.shashank.platform.saloon.databinding.ActivityShowLeaveBinding
import com.shashank.platform.saloon.interfaces.RemoveLeavData
import com.shashank.platform.saloon.model.AssignLeads
import com.shashank.platform.saloon.model.LeaveDataByUserIdItem
import com.shashank.platform.saloon.viewmodel.LeaveDataByUserModel


class ShowLeaveActivity : BaseActivity<ActivityShowLeaveBinding, LeaveDataByUserModel>() {

    private lateinit var binding: ActivityShowLeaveBinding
    private lateinit var userAdapter: ShowLeaveAdapter
    private lateinit var mViewModel: LeaveDataByUserModel
    private lateinit var mDialog: SweetAlertDialog//declare this dialog globally

    private val userList = mutableListOf<AssignLeads>()

    override fun getBindingVariable(): Int {
        return BR.leaveDataByUserModel
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_show_leave
    }

    override fun getViewModel(): LeaveDataByUserModel {
        mViewModel = ViewModelProvider(this)[LeaveDataByUserModel::class.java]
        return mViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewDataBinding()
        binding.leaveDataByUserModel = mViewModel
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = "Leave List"
        window.statusBarColor = ContextCompat.getColor(
            applicationContext,
            R.color.colorAccent
        )
        supportActionBar?.setBackgroundDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.actionbar_color
            )
        )
        populateUserList()
        mViewModel.allApplicationObserver.observe(this)
        {
            if (it.isNullOrEmpty()) {
                mDialog.dismiss()
                binding.emptyImage.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE
                Log.d("setupRecyclerView", "setupRecyclerView: $it")
            } else {
                mDialog.dismiss()
                binding.emptyImage.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
                Log.d("setupRecyclerView", "setupRecyclerView: " + it.toString())
                setupRecyclerView(it)
            }

        }
    }

    private fun setupRecyclerView(leaveDataByUserIdItems: List<LeaveDataByUserIdItem>) {
        userAdapter = ShowLeaveAdapter(this@ShowLeaveActivity, leaveDataByUserIdItems, mViewModel)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = userAdapter
        userAdapter.notifyDataSetChanged()
    }

    private fun populateUserList() {
        // Populate your user list here, for example:
        mDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        mDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
        mDialog.titleText = "Loading ..."
        mDialog.setCancelable(false)
        mDialog.show()
        mViewModel.getLeaveDataByUser()
        // Notify the adapter that the data set has changed
        // userAdapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.show_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_leave -> {
                val intent = Intent(this, ApplyLeaveActivity::class.java)
                startActivity(intent)
                return true
            }
            android.R.id.home -> {
                val intent = Intent(this@ShowLeaveActivity, MainActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@ShowLeaveActivity, MainActivity::class.java)
        startActivity(intent)

    }


}