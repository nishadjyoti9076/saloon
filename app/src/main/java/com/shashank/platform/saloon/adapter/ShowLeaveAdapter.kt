package com.shashank.platform.saloon.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import cn.pedant.SweetAlert.SweetAlertDialog
import com.shashank.platform.saloon.R
import com.shashank.platform.saloon.application.MySaloonApplication.Companion.mContext
import com.shashank.platform.saloon.constant.GlobalAccess
import com.shashank.platform.saloon.databinding.LayoutForLeaveListBinding
import com.shashank.platform.saloon.interfaces.RemoveLeavData
import com.shashank.platform.saloon.model.AssignLeads
import com.shashank.platform.saloon.model.LeaveDataByUserIdItem
import com.shashank.platform.saloon.ui.ApplyLeaveActivity
import com.shashank.platform.saloon.viewmodel.LeaveDataByUserModel

class ShowLeaveAdapter(
    private val context: Context,
    private val userList: List<LeaveDataByUserIdItem>,
    private val mViewModel: LeaveDataByUserModel
) :
    RecyclerView.Adapter<ShowLeaveAdapter.UserViewHolder>() {

    private lateinit var mDialog: SweetAlertDialog//declare this dialog globally

    private val mutableUserList: MutableList<LeaveDataByUserIdItem> = userList.toMutableList()
    private var removeLeavData: RemoveLeavData? = null


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShowLeaveAdapter.UserViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding: LayoutForLeaveListBinding =
            DataBindingUtil.inflate(inflater, R.layout.layout_for_leave_list, parent, false)
        return UserViewHolder(binding)
    }

    inner class UserViewHolder(val binding: LayoutForLeaveListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: AssignLeads) {
            binding.user = user
            // Execute pending bindings to immediately update UI
            binding.executePendingBindings()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ShowLeaveAdapter.UserViewHolder, position: Int) {

        val item: LeaveDataByUserIdItem = mutableUserList[position]
        holder.binding.txtSubject.text = item.subject
        holder.binding.txtTodate.text = item.todate
        holder.binding.txtFromdate.text = item.fromDate
        holder.binding.txtReason.text = item.reason
        mViewModel.errorObserver.observe(context as LifecycleOwner)
        {
            Toast.makeText(mContext, "Delete Successfully", Toast.LENGTH_LONG).show()
            mDialog.dismissWithAnimation()
        }

        if (item.status == 1) {
            holder.binding.llEdit.visibility = View.GONE
            holder.binding.llStatus.visibility = View.VISIBLE
            holder.binding.txtStatus.text = "Approved"
            holder.binding.txtStatus.setTextColor(Color.parseColor("#036907"))
        } else {
            holder.binding.llEdit.visibility = View.VISIBLE
            holder.binding.llStatus.visibility = View.VISIBLE
            holder.binding.txtStatus.text = "Pending"
            holder.binding.txtStatus.setTextColor(Color.parseColor("#FFF41D0D"))
        }
        holder.binding.btnEdit.setOnClickListener {
            val intent = Intent(mContext, ApplyLeaveActivity::class.java)
            intent.putExtra("edit", "Edit")
            intent.putExtra("ItemObject", item as java.io.Serializable)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            mContext?.startActivity(intent)
        }

        holder.binding.btnDelete.setOnClickListener {
            mDialog = SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE)
            mDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
            mDialog.titleText = "Loading ..."
            mDialog.setCancelable(false)
            mDialog.show()
           mViewModel.deleteLeave(item.id)
            remove(position)
        }
    }

    override fun getItemCount(): Int {
        return mutableUserList.size
    }

    fun remove(position: Int){
            mutableUserList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, mutableUserList.size)

    }
}