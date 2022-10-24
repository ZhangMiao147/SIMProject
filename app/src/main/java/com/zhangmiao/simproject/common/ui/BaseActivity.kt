package com.zhangmiao.simproject.common.ui

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

/**
 * 基础 activity,包含展示 loading 与 兜底展示
 */
open class BaseActivity:AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        Log.d(TAG,"onCreate")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG,"onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG,"onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG,"onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG,"onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,"onDestroy")
    }

    /**
     * 设置标题栏
     * @param title 标题
     * @param showBack 是否显示返回按钮
     */
    fun setAppBar(title:String = "",showBack:Boolean = false){

    }


    /**
     * 展示 loading
     */
    fun showLoading(){

    }

    /**
     * 隐藏 loading
     */
    fun hideLoading(){

    }

    /**
     * 展示空数据兜底页
     */
    fun showEmptyView(){

    }

    /**
     * 隐藏空数据兜底页
     */
    fun hideEmptyView(){

    }

    /**
     * 展示失败兜底页
     */
    fun showErrorView(message:String){

    }

    /**
     * 隐藏失败兜底页
     */
    fun hideErrorView(){

    }



    companion object {
        val TAG:String = "BaseActivity"
    }
}