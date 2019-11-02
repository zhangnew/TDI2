package com.zhangnew.tdi2.hook

import android.util.Log
import com.zhangnew.tdi2.Global
import com.zhangnew.tdi2.Global.TAG
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage


/**
 * @author https://github.com/zhangnew
 *
 * 注意：该类不要自己写构造方法，否者可能会hook不成功
 * 开发 Xposed 模块完成以后，建议修改 xposed_init 文件，并将起指向这个类,以提升性能
 * 所以这个类需要 implements IXposedHookLoadPackage, 以防修改 xposed_init 文件后忘记
 *
 * 针对 tid2 R1.5.4 开发
 */

class Tdi2Hook : IXposedHookLoadPackage {
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam?) {
        Log.i("TDI2 Hook", "load package ${lpparam!!.packageName}")

        if (lpparam.packageName == Global.TDI2_PACKAGE_NAME){
            Log.i(TAG, "Found package ${Global.TDI2_PACKAGE_NAME}")

            // 修改主页用户名
            XposedHelpers.findAndHookMethod("${Global.TDI2_PACKAGE_NAME}.managers.AuthManager", lpparam.classLoader, "getNickname", object : XC_MethodHook() {
                override fun afterHookedMethod(param: MethodHookParam?) {
                    Log.d(TAG, "managers.AuthManager getNickname()")
                    val name = param!!.result as String
                    param.result = "$name patched"
                }
            })

            // 修改游戏内的金币数量（买塔用的钱）
            XposedHelpers.findAndHookMethod("${Global.TDI2_PACKAGE_NAME}.systems.GameStateSystem", lpparam.classLoader, "addMoney", Float::class.java, Boolean::class.java, object : XC_MethodHook() {
                // 函数输入第一个参数是 float 类型的金币，返回值是 int 类型
                override fun beforeHookedMethod(param: MethodHookParam?) {
                    Log.d(TAG, "before systems.GameStateSystem addMoney()")
                    param?.args?.apply {
                        val money = this[0] as Float
                        val allMoney = XposedHelpers.callMethod(param.thisObject, "getMoney") as Int
                        if(allMoney < 30000) {
                            this[0] = money + 10000.0f
                        }
                        //this[0] = money + 10000.0f
                    }
                }
            })

            // 修改塔的最高等级
            XposedHelpers.findAndHookMethod("${Global.TDI2_PACKAGE_NAME}.Tower", lpparam.classLoader, "getMaxUpgradeLevel", object : XC_MethodHook() {
                override fun afterHookedMethod(param: MethodHookParam?) {
                    Log.d(TAG, "Tower getMaxUpgradeLevel()")
                    param!!.result = 10
                }
            })
            XposedHelpers.findAndHookMethod("${Global.TDI2_PACKAGE_NAME}.Tower", lpparam.classLoader, "getMaxTowerLevel", object : XC_MethodHook() {
                override fun afterHookedMethod(param: MethodHookParam?) {
                    Log.d(TAG, "Tower getMaxTowerLevel()")
                    param!!.result = 100
                }
            })

            XposedHelpers.findAndHookMethod("${Global.TDI2_PACKAGE_NAME}.Enemy", lpparam.classLoader, "getKillExp", object : XC_MethodHook() {
                override fun afterHookedMethod(param: MethodHookParam?) {
                    Log.d(TAG, "Ememy getKillExp()")
                    param!!.result = param.result as Float * 50
                }
            })

            XposedHelpers.findAndHookMethod("${Global.TDI2_PACKAGE_NAME}.Enemy", lpparam.classLoader, "getKillScore", object : XC_MethodHook() {
                override fun afterHookedMethod(param: MethodHookParam?) {
                    Log.d(TAG, "Ememy getKillScore()")
                    param!!.result = param.result as Int * 50
                }
            })

            // 修改敌人收到的伤害 *100
            val TowerType = XposedHelpers.findClass("${Global.TDI2_PACKAGE_NAME}.enums.TowerType", lpparam.classLoader)
            XposedHelpers.findAndHookMethod("${Global.TDI2_PACKAGE_NAME}.Enemy", lpparam.classLoader, "getTowerDamageMultiplier", TowerType, object : XC_MethodHook() {
                override fun afterHookedMethod(param: MethodHookParam?) {
                    Log.d(TAG, "Ememy getTowerDamageMultiplier()")
                    param!!.result = param.result as Float * 100.0f
                }
            })

            // 修改挖矿速度（这里只 hook 了两种矿机）
            val ResourceType = XposedHelpers.findClass("${Global.TDI2_PACKAGE_NAME}.enums.ResourceType", lpparam.classLoader)
            val GameValueProvider = XposedHelpers.findClass("${Global.TDI2_PACKAGE_NAME}.GameValueProvider", lpparam.classLoader)
            XposedHelpers.findAndHookMethod("${Global.TDI2_PACKAGE_NAME}.miners.VectorMiner\$VectorMinerFactory", lpparam.classLoader, "getBaseMiningSpeed", ResourceType, GameValueProvider, object : XC_MethodHook() {
                override fun afterHookedMethod(param: MethodHookParam?) {
                    Log.d(TAG, "miners.VectorMiner\$VectorMinerFactory getBaseMiningSpeed()")
                    param!!.result = param.result as Float * 10
                }
            })
            XposedHelpers.findAndHookMethod("${Global.TDI2_PACKAGE_NAME}.miners.InfiarMiner\$InfiarMinerFactory", lpparam.classLoader, "getBaseMiningSpeed", ResourceType, GameValueProvider, object : XC_MethodHook() {
                override fun afterHookedMethod(param: MethodHookParam?) {
                    Log.d(TAG, "miners.InfiarMiner\$InfiarMinerFactory getBaseMiningSpeed()")
                    param!!.result = param.result as Float * 10
                }
            })
        }
    }
}