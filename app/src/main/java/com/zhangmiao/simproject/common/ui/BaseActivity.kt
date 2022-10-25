package com.zhangmiao.simproject.common.ui

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.zhangmiao.simproject.R

/**
 * 基础 activity,包含展示 loading 与 兜底展示
 */
open class BaseActivity : AppCompatActivity() {

    val TAG: String? = this::class.simpleName

    /**
     * 标题栏
     */
    private var iv_appBarBack: ImageView? = null
    private var tv_appBarTitle: TextView? = null

    /**
     * loading
     */
    private var pb_loading: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
    }

    protected open fun initView() {
        iv_appBarBack = findViewById(R.id.layout_app_bar_back_iv)
        tv_appBarTitle = findViewById(R.id.layout_app_bar_title_tv)
        pb_loading = findViewById(R.id.layout_loading_loading_pb)
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

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    /**
     * 设置标题栏
     * @param title 标题
     * @param showBack 是否显示返回按钮
     */
    fun setAppBar(title: String = "", showBack: Boolean = false) {
        if (showBack) {
            iv_appBarBack?.visibility = View.VISIBLE
        } else {
            iv_appBarBack?.visibility = View.GONE
        }

        tv_appBarTitle?.text = title
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