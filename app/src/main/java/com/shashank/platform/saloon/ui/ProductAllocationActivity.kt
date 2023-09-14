package com.shashank.platform.saloon.ui

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
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
import com.shashank.platform.saloon.adapter.ProductAllocationAdapter
import com.shashank.platform.saloon.adapter.ShowLeaveAdapter
import com.shashank.platform.saloon.base.BaseActivity
import com.shashank.platform.saloon.databinding.ActivityProductAllocationBinding
import com.shashank.platform.saloon.databinding.ActivityShowLeaveBinding
import com.shashank.platform.saloon.model.GetProductAllocationDataItem
import com.shashank.platform.saloon.viewmodel.LeaveDataByUserModel
import com.shashank.platform.saloon.viewmodel.ProductAllocationViewModel

class ProductAllocationActivity :
    BaseActivity<ActivityProductAllocationBinding, ProductAllocationViewModel>() {


    private lateinit var binding: ActivityProductAllocationBinding
    private lateinit var userAdapter: ProductAllocationAdapter
    private lateinit var mViewModel: ProductAllocationViewModel
    private lateinit var mDialog: SweetAlertDialog//declare this dialog globally

    override fun getBindingVariable(): Int {
        return BR.productAllocationViewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_product_allocation
    }

    override fun getViewModel(): ProductAllocationViewModel {
        mViewModel = ViewModelProvider(this)[ProductAllocationViewModel::class.java]
        return mViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_product_allocation)
        binding = getViewDataBinding()
        binding.productAllocationViewModel = mViewModel
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = "Product Allocation"
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

        getProductAllocationData()

        mViewModel.allApplicationObserver.observe(this)
        {
            mDialog.dismiss()
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

    private fun setupRecyclerView(getProductAllocationDataItem: List<GetProductAllocationDataItem>) {
        userAdapter = ProductAllocationAdapter(
            this@ProductAllocationActivity,
            getProductAllocationDataItem,
            mViewModel
        )
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = userAdapter
        userAdapter.notifyDataSetChanged()
    }

    private fun getProductAllocationData() {
        mDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        mDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
        mDialog.titleText = "Loading ..."
        mDialog.setCancelable(false)
        mDialog.show()
        mViewModel.getProductAllocationData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.request_product -> {
                val intent = Intent(this, ProductRequestActivity::class.java)
                startActivity(intent)
                return true
            }
            android.R.id.home -> {
                val intent = Intent(this@ProductAllocationActivity, MainActivity::class.java)
                startActivity(intent)
            }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@ProductAllocationActivity, MainActivity::class.java)
        startActivity(intent)

    }

}