package com.shashank.platform.saloon.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import cn.pedant.SweetAlert.SweetAlertDialog
import com.shashank.platform.saloon.R
import com.shashank.platform.saloon.application.MySaloonApplication
import com.shashank.platform.saloon.databinding.LayoutForProductAllocationBinding
import com.shashank.platform.saloon.model.*
import com.shashank.platform.saloon.ui.MainActivity
import com.shashank.platform.saloon.viewmodel.ProductAllocationViewModel

class ProductAllocationAdapter(
    private val context: Context,
    private val getProductAllocationList: List<GetProductAllocationDataItem>,
    private val mViewModel: ProductAllocationViewModel
) : RecyclerView.Adapter<ProductAllocationAdapter.UserViewHolder>() {

    private val mutableProductAllocationList: MutableList<GetProductAllocationDataItem> = getProductAllocationList.toMutableList()
    private lateinit var mDialog: SweetAlertDialog//declare this dialog globally

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ProductAllocationAdapter.UserViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding: LayoutForProductAllocationBinding =
            DataBindingUtil.inflate(inflater, R.layout.layout_for_product_allocation, parent, false)
        return UserViewHolder(binding)
    }

    inner class UserViewHolder(val binding: LayoutForProductAllocationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: AssignLeads) {
            // Execute pending  bindingsto immediately update UI
            binding.executePendingBindings()
        }
    }

    override fun onBindViewHolder(holder: ProductAllocationAdapter.UserViewHolder, position: Int) {

        val item: GetProductAllocationDataItem = getProductAllocationList[position]

        holder.binding.txtCategory.text = item.categoryName
        holder.binding.txtAvailablequantity.text = item.allocatedQuantity.toString()
        holder.binding.txtProductname.text = item.productName

        /*  if (item.status == 1 || item.status == null) {
              holder.binding.llEdit.visibility = View.GONE
              holder.binding.llStatus.visibility = View.VISIBLE
              holder.binding.txtStatus.text = "Pending"
              holder.binding.txtStatus.setTextColor(Color.parseColor("#FFF41D0D"))
              //holder.binding.txtStatus.setTextColor(Color.parseColor("#036907"))
          } else {
              holder.binding.llEdit.visibility = View.VISIBLE
              holder.binding.llStatus.visibility = View.VISIBLE
              holder.binding.txtStatus.text = "Approved"
          }*/

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

        mViewModel.updateproductResponse.observe(context as LifecycleOwner) {
            Toast.makeText(MySaloonApplication.mContext, "Update Successfully", Toast.LENGTH_LONG)
                .show()
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
            mDialog.dismissWithAnimation()

        }

        mViewModel.errorObserver.observe(context as LifecycleOwner) {
            Toast.makeText(MySaloonApplication.mContext, "Error on server side", Toast.LENGTH_LONG)
                .show()
            mDialog.dismissWithAnimation()
        }
        mViewModel.productDeleteResponse.observe(context as LifecycleOwner) {
            Toast.makeText(MySaloonApplication.mContext, "Delete Successfully", Toast.LENGTH_LONG)
                .show()
            mDialog.dismissWithAnimation()

        }

        holder.binding.btnEdit.setOnClickListener {
            showUpdateDialog(holder, item)
        }

        holder.binding.btnDelete.setOnClickListener {
            mDialog = SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE)
            mDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
            mDialog.titleText = "Loading ..."
            mDialog.setCancelable(false)
            mDialog.show()
            mViewModel.deleteProductAllocationData(mDialog,item.id)
            remove(position)
        }
    }

    override fun getItemCount(): Int {
        return mutableProductAllocationList.size
    }

    private fun showUpdateDialog(
        holder: UserViewHolder, productItem: GetProductAllocationDataItem
    ) {
        val dialogBuilder = AlertDialog.Builder(holder.itemView.context)

        val input = EditText(holder.itemView.context)
        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT
        )
        input.layoutParams = lp
        //  input.setText(movie.name)

        dialogBuilder.setView(input)

        dialogBuilder.setTitle("Update Quantity")
        dialogBuilder.setPositiveButton("Update") { dialog, whichButton ->
            // updateMovie(Movie(movie.id, input.text.toString()))

            if (input.text.toString().isNotEmpty() && input.text.toString().isNotBlank()) {
                /*  if (productItem.allocatedQuantity.toString() < input.text.toString()) {
                      Toast.makeText(context, "Quantity is Greater", Toast.LENGTH_LONG).show()
                  } else {
                      mDialog = SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE)
                      mDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
                      mDialog.titleText = "Loading ..."
                      mDialog.setCancelable(false)
                      mDialog.show()*/
                mDialog = SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE)
                mDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
                mDialog.titleText = "Loading ..."
                mDialog.setCancelable(false)
                mDialog.show()
                val updateProductAllocationRequest = UpdateProductAllocation2(
                    input.text.toString().toInt(),
                    productItem.id,
                    productItem.productId,
                    productItem.userId
                )
                mViewModel.updateProductAllocationRequestData(updateProductAllocationRequest)
                // }
            } else {
                Toast.makeText(context, "Enter Quantity", Toast.LENGTH_LONG).show()
                // input.requestFocus()
            }


        }
        dialogBuilder.setNegativeButton("Cancel") { dialog, whichButton ->
            dialog.cancel()
        }
        val b = dialogBuilder.create()
        b.show()
    }


    fun remove(position: Int){
        if (position >=  0 && position < mutableProductAllocationList.size) {
            mutableProductAllocationList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, mutableProductAllocationList.size)
        }
    }
}