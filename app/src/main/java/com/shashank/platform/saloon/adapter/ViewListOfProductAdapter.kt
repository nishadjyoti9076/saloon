package com.shashank.platform.saloon.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.shashank.platform.saloon.R
import com.shashank.platform.saloon.application.MySaloonApplication
import com.shashank.platform.saloon.application.MySaloonApplication.Companion.sharedPreferenceClass
import com.shashank.platform.saloon.constant.ApiConst.ID
import com.shashank.platform.saloon.databinding.LayoutForViewProductBinding
import com.shashank.platform.saloon.model.AssignLeads
import com.shashank.platform.saloon.model.ProductDatabyUserIdItem
import com.shashank.platform.saloon.model.UpdateProductAllocationRequest
import com.shashank.platform.saloon.viewmodel.ProductViewModel

class ViewListOfProductAdapter(
    private val context: Context,
    private val userList: List<ProductDatabyUserIdItem>, private val mViewModel: ProductViewModel
) : RecyclerView.Adapter<ViewListOfProductAdapter.UserViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: LayoutForViewProductBinding =
            DataBindingUtil.inflate(inflater, R.layout.layout_for_view_product, parent, false)
        return UserViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {

        mViewModel.updateproductResponse.observe(context as LifecycleOwner)
        {
            Toast.makeText(context, "Quantity Updated", Toast.LENGTH_LONG).show()
            notifyDataSetChanged()
        }
        mViewModel.errorObserver.observe(context as LifecycleOwner)
        {
            Toast.makeText(MySaloonApplication.mContext, "Quantity Not Updated", Toast.LENGTH_LONG)
                .show()
        }
        val item: ProductDatabyUserIdItem = userList.get(position)
        holder.binding.txtCategory.text = item.categoryName
        holder.binding.txtProductname.text = item.productName
        holder.binding.txtExpirydate.text = item.expirydate
        holder.binding.txtProductamount.text = item.totalPrice
        holder.binding.txtSellingquantity.text = item.totalSellingQuantity.toString()
        holder.binding.txtAvailablequantity.text = item.totalAvailableQuantity.toString()
        holder.binding.txtAllocatedQuantity.text = item.allocatedQuantity.toString()
        holder.binding.btnEdit.setOnClickListener {
            showUpdateDialog(holder, item)
        }
    }

    inner class UserViewHolder(val binding: LayoutForViewProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: AssignLeads) {
            binding.user = user
            // Execute pending bindings to immediately update UI
            binding.executePendingBindings()
        }
    }

    private fun showUpdateDialog(holder: UserViewHolder, productItem: ProductDatabyUserIdItem) {
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
                if (productItem.totalAvailableQuantity.toString() < input.text.toString()) {
                    Toast.makeText(context, "Quantity is Greater", Toast.LENGTH_LONG).show()
                } else {
                    val updateProductAllocationRequest = UpdateProductAllocationRequest(
                        input.text.toString().toInt(),
                        sharedPreferenceClass.getString(ID).toInt(),
                        productItem.productId,
                        sharedPreferenceClass.getString(ID).toInt()
                    )
                    mViewModel.updateProductAllocationData(updateProductAllocationRequest)
                }
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

}