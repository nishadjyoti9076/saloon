package com.shashank.platform.saloon.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doBeforeTextChanged
import androidx.lifecycle.ViewModelProvider
import cn.pedant.SweetAlert.SweetAlertDialog
import com.shashank.platform.saloon.BR
import com.shashank.platform.saloon.R
import com.shashank.platform.saloon.application.MySaloonApplication.Companion.sharedPreferenceClass
import com.shashank.platform.saloon.base.BaseActivity
import com.shashank.platform.saloon.constant.ApiConst.ID
import com.shashank.platform.saloon.databinding.ActivityProductRequestBinding
import com.shashank.platform.saloon.model.CategoryListItem
import com.shashank.platform.saloon.model.ProductByCategoryItem
import com.shashank.platform.saloon.model.SaveProductAllocationRequest
import com.shashank.platform.saloon.viewmodel.ProductRequestViewModel
import java.text.SimpleDateFormat
import java.util.*

class ProductRequestActivity :
    BaseActivity<ActivityProductRequestBinding, ProductRequestViewModel>() {

    private lateinit var binding: ActivityProductRequestBinding
    private lateinit var mViewModel: ProductRequestViewModel
    var dataCategoryList: List<CategoryListItem>? = null
    var spinnerCategoryDataList: ArrayList<String>? = null
    var spinnerProductDataList: ArrayList<String>? = null
    var productDataByCategory: List<ProductByCategoryItem>? = null
    var categoryId: Int? = null
    var totalQuantity: String? = null
    var productId: Int? = null
    private lateinit var mDialog: SweetAlertDialog//declare this dialog globally

    override fun getBindingVariable(): Int {
        return BR.productRequestViewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_product_request
    }

    override fun getViewModel(): ProductRequestViewModel {
        mViewModel = ViewModelProvider(this)[ProductRequestViewModel::class.java]
        return mViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //  setContentView(R.layout.activity_product_request)
        binding = getViewDataBinding()
        binding.productRequestViewModel = mViewModel
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = "Product Request"
/*

        mViewModel.mCategory.value = binding.edCategory.text.toString()
        mViewModel.mProductName.value = binding.edProduct.text.toString()
        mViewModel.mQuality.value = binding.edQuanity.text.toString()
        mViewModel.mPrice.value = binding.edPrice.text.toString()
        mViewModel.mAmount.value = binding.edAmount.text.toString()
*/



        getCategoryData()
        mViewModel.getCategoryObserver.observe(this)
        {

            dataCategoryList = it
// Create a list of category names from dataCategoryList
            spinnerCategoryDataList = ArrayList(dataCategoryList?.map { it.name })
// Add the default value at the beginning of the list
            spinnerCategoryDataList!!.add(0, "--Select--")
// Set up the ArrayAdapter for the spinner
            val arrayAdapter = ArrayAdapter(
                this@ProductRequestActivity,
                R.layout.support_simple_spinner_dropdown_item,
                spinnerCategoryDataList!!
            )
// Set the adapter for the spinner
            binding.spinCategory.adapter = arrayAdapter
            mDialog.dismiss()
        }


        mViewModel.getProductByCategoryItem.observe(this)
        {

            productDataByCategory = it
            spinnerProductDataList =
                (productDataByCategory?.map { it.name } as ArrayList<String>?)!!

            //  spinnerProductDataList!!.add(0, "--Select--")

            // spinnerDataList.add(0, "--Select Product--")
            binding.spinProduct.adapter = ArrayAdapter(
                this@ProductRequestActivity,
                R.layout.support_simple_spinner_dropdown_item, spinnerProductDataList!!
            )


        }
        mViewModel.allApplicationObserver.observe(this) {
            Toast.makeText(this@ProductRequestActivity, "Data Save Successfully", Toast.LENGTH_LONG)
                .show()
            val intent = Intent(this@ProductRequestActivity, ViewListOfProductActivity::class.java)
            startActivity(intent)
        }
        binding.spinCategory.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                //  categoryId = spinnerCategoryDataList?.get(position)
                mViewModel.getProductByCategoryId(binding.spinCategory.selectedItemId.toInt())
                binding.llProductdata.visibility = View.VISIBLE

                //stuff here to handle item selection
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.i(" TOUT", "Nothing Selected")
            }
        })


        binding.spinProduct.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                productId = productDataByCategory?.get(position)?.id!!
                totalQuantity =
                    productDataByCategory?.get(position)?.totalAvailableQuantity.toString()
                binding.edPrice.setText(
                    productDataByCategory?.get(position)?.minmumSellingPrice.toString()
                )


                //stuff here to handle item selection
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.i("GTOUTOUT", "Nothing Selected")
            }
        }


        binding.edQuanity.addTextChangedListener {
            val price: Double = (binding.edPrice.text.toString()).toDouble()
            val quantity: Int = Integer.valueOf(binding.edQuanity.text.toString())
            if (binding.edQuanity.text.toString()
                    .isBlank() && binding.edQuanity.text.toString().equals("")
            ) {
                Toast.makeText(this@ProductRequestActivity, "Enter Quantity", Toast.LENGTH_LONG)
                    .show()
            } else {
                val totalPrice = price * quantity
                binding.edTotalamount.setText(totalPrice.toString())
            }
        }

        binding.edQuanity.doBeforeTextChanged { text, start, count, after ->

        }



        binding.btnRequest.setOnClickListener {
            if (binding.spinCategory.selectedItem.toString()
                    .isEmpty() && binding.spinCategory.selectedItem.toString().equals("--select--")
            ) {
                Toast.makeText(this@ProductRequestActivity, "Select Category", Toast.LENGTH_LONG)
                    .show()
            } else if (binding.edQuanity.text.toString()
                    .isEmpty() && binding.edQuanity.text.toString().isBlank()
            ) {
                Toast.makeText(this@ProductRequestActivity, "Enter Quantity", Toast.LENGTH_LONG)
                    .show()
            } else {
                val date = Calendar.getInstance().time
                // val formatter = SimpleDateFormat.getDateTimeInstance() //or use getDateInstance()
                val formatter = SimpleDateFormat("yyyy-MM-dd")
                val formatedDate = formatter.format(date)
                val leaveDataByUserIdItem = SaveProductAllocationRequest(
                    binding.edQuanity.text.toString().toInt(),
                    formatedDate,
                    0,
                    0,
                    productId!!,
                    0,
                    sharedPreferenceClass.getString(ID).toInt()
                )
                mViewModel.callForProductRequest(leaveDataByUserIdItem)
            }


        }
        //  binding.
    }


    fun getCategoryData() {
        mDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        mDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
        mDialog.setTitleText("Loading ...")
        mDialog.setCancelable(false)
        mDialog.show()
        mViewModel.getCategoryList()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                val intent = Intent(this@ProductRequestActivity, MainActivity::class.java)
                startActivity(intent)
            }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@ProductRequestActivity, MainActivity::class.java)
        startActivity(intent)

    }

}
