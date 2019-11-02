package com.zhangnew.tdi2.hook

import com.zhangnew.tdi2.Global
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage

class ModuleEnabledHooker : IXposedHookLoadPackage {
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam?) {
        if (lpparam!!.packageName == Global.MY_PACKAGE_NAME){
            XposedHelpers.findAndHookMethod(Global.MY_PACKAGE_NAME + ".Util", lpparam.classLoader, "getModuleVersion", object : XC_MethodHook() {
                override fun afterHookedMethod(param: MethodHookParam?) {
                    param!!.result = 1
                }
            })
        }
    }
}