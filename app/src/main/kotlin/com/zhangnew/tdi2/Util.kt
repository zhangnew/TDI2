package com.zhangnew.tdi2

import android.util.Log
/**
 * Created by zhangnew on 2019/10/26.
 */

object Util {
    /**
     * 返回模块版本
     * 注意：该方法被本模块Hook住，返回的值是 BuildConfig.MODULE_VERSION，如果没被Hook则返回-1
     */
    private fun getModuleVersion(): Int {
        Log.d(Global.TAG, "getModuleVersion")
        return -1
    }

    /**
     * 当前模块是否在XposedInstaller中被启用
     */
    fun isModuleEnabled(): Boolean {
        Log.d(Global.TAG, "getModuleVersion=" + getModuleVersion().toString())
        return getModuleVersion() > 0
    }
}
