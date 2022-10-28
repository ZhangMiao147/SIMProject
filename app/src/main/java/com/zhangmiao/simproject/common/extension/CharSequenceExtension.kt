package com.zhangmiao.simproject.common.extension

import android.widget.Toast
import com.zhangmiao.simproject.SIMApplication

fun CharSequence.showToast(){
    Toast.makeText(SIMApplication.context,this,Toast.LENGTH_SHORT).show()
}