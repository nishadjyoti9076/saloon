package com.shashank.platform.saloon.ui

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import cn.pedant.SweetAlert.SweetAlertDialog
import com.shashank.platform.saloon.BR
import com.shashank.platform.saloon.R
import com.shashank.platform.saloon.application.MySaloonApplication
import com.shashank.platform.saloon.application.MySaloonApplication.Companion.sharedPreferenceClass
import com.shashank.platform.saloon.base.BaseActivity
import com.shashank.platform.saloon.constant.ApiConst.ID
import com.shashank.platform.saloon.databinding.ActivityViewProfileBinding
import com.shashank.platform.saloon.model.UpdateProfileRequest
import com.shashank.platform.saloon.model.UserProfile
import com.shashank.platform.saloon.retrofit.ApiRepository.getUserProfile
import com.shashank.platform.saloon.viewmodel.EditProfileViewModel
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class ViewProfileActivity : BaseActivity<ActivityViewProfileBinding, EditProfileViewModel>() {


    private lateinit var binding: ActivityViewProfileBinding
    private lateinit var mViewModel: EditProfileViewModel
    private lateinit var mDialog: SweetAlertDialog//declare this dialog globally

    override fun getBindingVariable(): Int {
        return BR.editProfileViewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_view_profile
    }

    override fun getViewModel(): EditProfileViewModel {
        mViewModel = ViewModelProvider(this)[EditProfileViewModel::class.java]
        return mViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_view_profile)
        binding = getViewDataBinding()
        binding.editProfileViewModel = mViewModel
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = "View Profile"
        getUserProfile()

        mViewModel.userProfileData.observe(this)
        {
            binding.firstNameEditText.setText(it.firstName)
            binding.lastNameEditText.setText(it.lastName)
            binding.edMobileno.setText(it.mobileNo)
            binding.dobEditText.setText(it.dob.split("T")[0])
            val imageUrl =
                "https://mysalonss.dreamitsolution.org/AdminContent/ProfilePic/6682fc7d-7c19-4b65-b147-6b93697d529f_user-png.png"
            Log.d("TAG", "imageUrl: $imageUrl")
            Picasso.get()
                .load(imageUrl)
                .into(binding.profilePic)
            mDialog.dismiss()
        }

        mViewModel.errorObserver.observe(this)
        {
            Toast.makeText(this@ViewProfileActivity, "Data Not Found", Toast.LENGTH_LONG)
                .show()
            mDialog.dismiss()
        }

        mViewModel.updateProfileResponse.observe(this)
        {
            Toast.makeText(this@ViewProfileActivity, "Update Successfully", Toast.LENGTH_LONG)
                .show()
            mDialog.dismiss()
            binding.updateButton.visibility = View.GONE
        }

        binding.ivEdit.setOnClickListener {
            binding.updateButton.visibility = View.VISIBLE
            binding.dobEditText.isEnabled = true
            binding.firstNameEditText.isEnabled = true
            binding.lastNameEditText.isEnabled = true
            binding.edMobileno.isEnabled = true
        }

        binding.dobEditText.setOnClickListener {
            openDatePicker(binding.dobEditText)
        }

        binding.updateButton.setOnClickListener {
            val updateProfile = UpdateProfileRequest(
                binding.firstNameEditText.text.toString(),
                sharedPreferenceClass.getString(ID).toInt(),
                binding.lastNameEditText.text.toString(),
                binding.edMobileno.text.toString(),
                binding.dobEditText.text.toString()
            )
            mDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
            mDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
            mDialog.setTitleText("Loading ...")
            mDialog.setCancelable(false)
            mDialog.show()
            mViewModel.updateProfile(updateProfile)
        }

        //https://mysalonss.dreamitsolution.org/AdminContent/D638284623198739062-logo.PNG


    }

    private fun getUserProfile() {
        mDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        mDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
        mDialog.setTitleText("Loading ...")
        mDialog.setCancelable(false)
        mDialog.show()
        mViewModel.getProfileData()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                val intent = Intent(this@ViewProfileActivity, MainActivity::class.java)
                startActivity(intent)
            }

        }
        return super.onOptionsItemSelected(item)
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

}