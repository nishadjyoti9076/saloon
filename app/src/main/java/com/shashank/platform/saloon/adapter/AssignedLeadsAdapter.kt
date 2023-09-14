package com.shashank.platform.saloon.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.location.Location
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import cn.pedant.SweetAlert.SweetAlertDialog
import com.shashank.platform.saloon.R
import com.shashank.platform.saloon.application.MySaloonApplication.Companion.sharedPreferenceClass
import com.shashank.platform.saloon.constant.ApiConst.ID
import com.shashank.platform.saloon.databinding.AssingedLeadsLayoutBinding
import com.shashank.platform.saloon.model.ServiceLeadRequest
import com.shashank.platform.saloon.model.UserLeadsResponseByUserId
import com.shashank.platform.saloon.model.UserLeadsResponseByUserIdItem
import com.shashank.platform.saloon.ui.DrawMapActivity
import com.shashank.platform.saloon.viewmodel.UserLeadsViewModel

class AssignedLeadsAdapter(
    private val context: Context,
    private val userList: List<UserLeadsResponseByUserIdItem>,
    private val mViewModel: UserLeadsViewModel,
    private val lastLocation: Location?,
    private val addresses: String
) : RecyclerView.Adapter<AssignedLeadsAdapter.UserViewHolder>() {
    var binding: AssingedLeadsLayoutBinding? = null
    var status: Int? = null
    private lateinit var mDialog: SweetAlertDialog//declare this dialog globally

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(inflater, R.layout.assinged_leads_layout, parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        //holder.bind(userList[position])

        mViewModel.saveLeadDataResponse.observe(context as LifecycleOwner)
        {
            mDialog.dismiss()
            Toast.makeText(context, "Data Save Successfully", Toast.LENGTH_LONG).show()
        }
        val item: UserLeadsResponseByUserIdItem = userList.get(position)
        holder.binding?.txtServicetype?.text = item.serviceType
        holder.binding?.txtAddress?.text = item.address
        holder.binding?.txtUsername?.text = item.userName
        holder.binding?.txtStartdata?.text = item.serviceStartTime
        holder.binding?.txtServiceduration?.text = item.duration
        binding?.btnAction?.setOnClickListener {
            openDialog(item.id)
        }
       /* binding?.txtViewMap?.setOnClickListener {
            val intent = Intent(context, DrawMapActivity::class.java)
            context.startActivity(intent)
        }*/

        binding?.btnViewMap?.setOnClickListener{
            val intent = Intent(context, DrawMapActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    inner class UserViewHolder(val binding: AssingedLeadsLayoutBinding?) :
        RecyclerView.ViewHolder(binding!!.root) {
        //lateinit var item: UserLeadsResponseByUserId
        fun bind(user: UserLeadsResponseByUserId) {
            // this.item = item
            // Execute pending bindings to immediately update UI

            binding?.executePendingBindings()
        }
    }

    private fun openDialog(leadId: Int) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Take Action")

        val inflater = LayoutInflater.from(context)
        val dialogView = inflater.inflate(R.layout.dialog_spinner, null)

        val spinner = dialogView.findViewById<Spinner>(R.id.spinner)
        val items = arrayOf(
            "--Select--", "Start Location", "Start service", "Stop Service", "Stop Location"
        )
        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, items)
        spinner.adapter = adapter

        val selectedPosition = items.indexOf(spinner.selectedItem)
        spinner.setSelection(selectedPosition)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (spinner.selectedItem.toString().equals("Start Location")) {
                    status = 1
                } else if (spinner.selectedItem.toString().equals("Start service")) {
                    status = 2
                } else if (spinner.selectedItem.toString().equals("Stop Location")) {
                    status = 3
                } else if (spinner.selectedItem.toString().equals("Stop Service")) {
                    status = 4
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        builder.setView(dialogView)
        builder.setPositiveButton("OK") { dialog, which ->
            // Handle the selected item
            val selectedItem = spinner.selectedItem as String
            // TODO: Do something with the selected item
            saveLeadData(leadId, status!!)
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancel") { dialog, which ->
            dialog.dismiss()
        }
        builder.show()

    }

    private fun saveLeadData(leadId: Int, status: Int) {
        mDialog = SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE)
        mDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
        mDialog.setTitleText("Loading ...")
        mDialog.setCancelable(false)
        mDialog.show()
        val saveDataRequst = ServiceLeadRequest(
            addresses,
            "",
            false,
            lastLocation!!.latitude,
            leadId,
            lastLocation.longitude,
            "",
            status,
            sharedPreferenceClass.getString(ID).toInt()
        )
        mViewModel.saveLeadData(saveDataRequst)
    }
}