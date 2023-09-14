package com.shashank.platform.saloon.ui

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.shashank.platform.saloon.BR
import com.shashank.platform.saloon.R
import com.shashank.platform.saloon.application.MySaloonApplication.Companion.sharedPreferenceClass
import com.shashank.platform.saloon.base.BaseActivity
import com.shashank.platform.saloon.constant.ApiConst
import com.shashank.platform.saloon.databinding.ActivityApplyLeaveBinding
import com.shashank.platform.saloon.model.LeaveDataByUserIdItem
import com.shashank.platform.saloon.viewmodel.ApplyLeaveViewModel
import java.text.SimpleDateFormat
import java.util.*

class ApplyLeaveActivity : BaseActivity<ActivityApplyLeaveBinding, ApplyLeaveViewModel>() {
    private lateinit var binding1: ActivityApplyLeaveBinding

    private lateinit var mViewModel: ApplyLeaveViewModel
     var leaveid:Int=0

    override fun getBindingVariable(): Int {
        return BR.leaveDataByUserViewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_apply_leave
    }

    override fun getViewModel(): ApplyLeaveViewModel {
        mViewModel = ViewModelProvider(this)[ApplyLeaveViewModel::class.java]
        return mViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_apply_leave)
        binding1 = getViewDataBinding()
        binding1.leaveDataByUserViewModel = mViewModel

        val editResponse = intent.getStringExtra("edit")
        if (editResponse != null) {
            val mItemObject = intent.getSerializableExtra("ItemObject") as LeaveDataByUserIdItem
            if (mItemObject != null) {
                leaveid=mItemObject.id
                binding1.btnApply.text = "U pdate"
                binding1.edSubject.setText(mItemObject.subject)
                binding1.edFromdate.setText(mItemObject.fromDate)
                binding1.edTodate.setText(mItemObject.todate)
                binding1.edReason.setText(mItemObject.reason)
            }
        }
        //   binding = LayoutLeaveBinding.inflate(layoutInflater)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = "Apply Leave"

        mViewModel.allApplicationObserver.observe(this) {
            Toast.makeText(this@ApplyLeaveActivity, "Data Save Successfully", Toast.LENGTH_LONG)
                .show()
            val intent = Intent(this@ApplyLeaveActivity, ShowLeaveActivity::class.java)
            startActivity(intent)
        }

        binding1.btnApply.setOnClickListener {
            if (binding1.edSubject.text.toString()
                    .isEmpty() && binding1.edSubject.text.toString().isBlank()
            ) {
                Toast.makeText(this@ApplyLeaveActivity, "Enter Subject", Toast.LENGTH_LONG).show()
            } else if (binding1.edFromdate.text.toString()
                    .isEmpty() && binding1.edFromdate.text.toString().isBlank()
            ) {
                Toast.makeText(this@ApplyLeaveActivity, "Enter From Date", Toast.LENGTH_LONG).show()

            } else if (binding1.edTodate.text.toString()
                    .isEmpty() && binding1.edTodate.text.toString().isBlank()
            ) {
                Toast.makeText(this@ApplyLeaveActivity, "Enter To Date", Toast.LENGTH_LONG).show()
            } else if (binding1.edReason.text.toString()
                    .isEmpty() && binding1.edReason.text.toString().isBlank()
            ) {
                Toast.makeText(this@ApplyLeaveActivity, "Enter Reason", Toast.LENGTH_LONG).show()

            } else {

                if (binding1.btnApply.text.toString() == "Update") {
                    val leaveDataByUserIdItem = LeaveDataByUserIdItem(
                        binding1.edSubject.text.toString(),
                        binding1.edFromdate.text.toString(),
                        leaveid/*sharedPreferenceClass.getString(ApiConst.ID).toInt()*/,
                        true,
                        binding1.edReason.text.toString(),
                        binding1.edTodate.text.toString(),
                        sharedPreferenceClass.getString(ApiConst.ID).toInt(),
                        0
                    )
                    mViewModel.updateLeave(leaveDataByUserIdItem)
                } else {
                    val leaveDataByUserIdItem = LeaveDataByUserIdItem(
                        binding1.edSubject.text.toString(),
                        binding1.edFromdate.text.toString(),
                        0,
                        true,
                        binding1.edReason.text.toString(),
                        binding1.edTodate.text.toString(),
                        sharedPreferenceClass.getString(ApiConst.ID).toInt(),
                        0
                    )
                    mViewModel.callApplyLeave(leaveDataByUserIdItem)
                }
            }
        }
        binding1.edTodate.setOnClickListener {
            checkToDate(binding1.edFromdate,binding1.edTodate)
        }
        binding1.edFromdate.setOnClickListener {
            openDatePicker(binding1.edFromdate)
        }
    }

    private fun openDatePicker(edTodate: EditText) {
        val calendar = Calendar.getInstance()

        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                // Set the selected date in the EditText
                val selectedDate = Calendar.getInstance()
                selectedDate.set(Calendar.YEAR, year)
                selectedDate.set(Calendar.MONTH, month)
                selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
                val formattedDate = dateFormat.format(selectedDate.time)
                edTodate.setText(formattedDate)
            },

            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePickerDialog.show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@ApplyLeaveActivity, MainActivity::class.java)
        startActivity(intent)
    }

    private fun checkToDate(edTofrom: EditText, edToto: EditText) {
        val calendar = Calendar.getInstance()

        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                // Set the selected date in the "to" EditText
                val selectedDate = Calendar.getInstance()
                selectedDate.set(Calendar.YEAR, year)
                selectedDate.set(Calendar.MONTH, month)
                selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
                val formattedDate = dateFormat.format(selectedDate.time)
                edToto.setText(formattedDate)

                // Check if the "to" date is less than the "from" date
                val fromDateStr = binding1.edFromdate.text.toString()
                val toDateStr = edToto.text.toString()

                if (fromDateStr.isNotEmpty() && toDateStr.isNotEmpty()) {
                    val fromDate = dateFormat.parse(fromDateStr)
                    val toDate = dateFormat.parse(toDateStr)

                    if (fromDate != null && toDate != null && toDate.before(fromDate)) {
                        // Show an error message or handle the invalid date as needed
                        Toast.makeText(this, "To date should not be less than from date", Toast.LENGTH_SHORT).show()
                        edToto.text.clear() // Clear the "to" date if it's invalid
                    }
                }
            },

            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePickerDialog.show()
    }


}



