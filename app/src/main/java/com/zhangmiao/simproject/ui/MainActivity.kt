package com.zhangmiao.simproject.ui

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.zhangmiao.simproject.R


class MainActivity:AppCompatActivity() {

    val TAG = this::class.simpleName
    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        setContentView(R.layout.activity_main)
        val tb_toolbar:Toolbar = findViewById(R.id.activity_main_toolbar_tb)
        val navController = findNavController(R.id.activity_main_navigation)
        setSupportActionBar(tb_toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)//添加默认的返回图标

        supportActionBar?.setHomeButtonEnabled(true) //设置返回键可用
        Log.d(TAG, "onCreate")
        supportActionBar?.setDisplayShowTitleEnabled(false)

        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener(
            object:NavController.OnDestinationChangedListener{
                override fun onDestinationChanged(
                    controller: NavController,
                    destination: NavDestination,
                    arguments: Bundle?
                ) {
                    val label = destination.label
                    ( tb_toolbar[0] as TextView).text = label
                    Log.d(TAG,"onDestinationChanged controller:${controller},destination:${destination},arguments:${arguments}")
                }
            })


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


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.activity_main_navigation)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

}