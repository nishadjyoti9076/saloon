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
import com.shashank.platform.saloon.adapter.ViewListOfProductAdapter
import com.shashank.platform.saloon.base.BaseActivity
import com.shashank.platform.saloon.databinding.ActivityViewListOfProductBinding
import com.shashank.platform.saloon.model.AssignLeads
import com.shashank.platform.saloon.model.ProductDatabyUserIdItem
import com.shashank.platform.saloon.viewmodel.ProductViewModel


class ViewListOfProductActivity :
    BaseActivity<ActivityViewListOfProductBinding, ProductViewModel>() {


    private lateinit var binding: ActivityViewListOfProductBinding
    private lateinit var userAdapter: ViewListOfProductAdapter
    private lateinit var mViewModel: ProductViewModel
    private val userList = mutableListOf<AssignLeads>()
    private lateinit var mDialog: SweetAlertDialog//declare this dialog globally


    override fun getBindingVariable(): Int {
        return BR.productViewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_view_list_of_product
    }

    override fun getViewModel(): ProductViewModel {
        mViewModel = ViewModelProvider(this)[ProductViewModel::class.java]
        return mViewModel
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewDataBinding()
        binding.productViewModel = mViewModel
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = "View Product"
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
        //setupRecyclerView()
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
        mViewModel.errorObserver.observe(this)
        {

            mDialog.dismiss()
            binding.emptyImage.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE

            Log.d("setupRecyclerView", "setupRecyclerView: " + it.toString())
        }
    }


    private fun setupRecyclerView(productDatabyUserIdItems: List<ProductDatabyUserIdItem>) {
        userAdapter = ViewListOfProductAdapter(
            this@ViewListOfProductActivity,
            productDatabyUserIdItems,
            mViewModel
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
        mViewModel.getProductByUserId()
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
                val intent = Intent(this@ViewListOfProductActivity, MainActivity::class.java)
                startActivity(intent)
            }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@ViewListOfProductActivity, MainActivity::class.java)
        startActivity(intent)

    }

}