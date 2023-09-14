package com.shashank.platform.saloon.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<T : ViewDataBinding, V : BaseViewModel<T>> : Fragment() {

    //  private var mBaseActivity:BaseActivity<>

    private var mBaseActivity: BaseActivity<*, *>? = null
    private var mRootVew: View? = null
    private var mViewDataBinding: T? = null
    private var mViewModel: V? = null


    abstract fun getBindingVariable(): Int


    abstract fun getLayoutId(): Int

    abstract fun getViewModel(): V?

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity<*, *>) {
            val activity = context as BaseActivity<*, *>?
            this.mBaseActivity = activity
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        mRootVew = mViewDataBinding?.root
        return mRootVew
    }

    override fun onDetach() {
        mBaseActivity = null
        super.onDetach()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewDataBinding?.setVariable(getBindingVariable(), mViewModel)
        mViewDataBinding?.executePendingBindings()
    }

    interface Callback {


    }

}