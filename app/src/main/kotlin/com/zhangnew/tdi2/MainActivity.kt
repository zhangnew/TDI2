package com.zhangnew.tdi2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (!Util.isModuleEnabled())
            Toast.makeText(this, "模块没有启用", Toast.LENGTH_LONG).show()
        else
            Toast.makeText(this, "模块启用成功", Toast.LENGTH_LONG).show()
    }
}
