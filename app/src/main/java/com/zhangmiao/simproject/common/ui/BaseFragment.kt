package com.zhangmiao.simproject.common.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.zhangmiao.simproject.R

open class BaseFragment:Fragment() {

    val TAG: String? = this::class.simpleName

    /**
     * loading
     */
    private var pb_loading: ProgressBar? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "onAttach")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewCreated")
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
    }

    protected open fun initView(view:View) {
        pb_loading = view.findViewById(R.id.layout_loading_loading_pb)
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }


    /**
     * 展示 loading
     */
    fun showLoading() {
        if (pb_loading?.visibility != View.VISIBLE) {
            pb_loading?.visibility = View.VISIBLE
        }
    }

    /**
     * 隐藏 loading
     */
    fun hideLoading() {
        Log.d(TAG, "hideLoading pb_loading:${pb_loading}")
        if (pb_loading?.visibility != View.GONE) {
            pb_loading?.visibility = View.GONE
        }
    }

    /**
     * 展示空数据兜底页
     */
    fun showEmptyView() {

    }

    /**
     * 隐藏空数据兜底页
     */
    fun hideEmptyView() {

    }

    /**
     * 展示失败兜底页
     */
    fun showErrorView(message: String) {

    }

    /**
     * 隐藏失败兜底页
     */
    fun hideErrorView() {

    }
}