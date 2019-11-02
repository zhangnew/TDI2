![](https://github.com/zhangnew/TDI2/workflows/Android%20CI/badge.svg)
# 无限塔防 2 Xposed 模块

## 特性
* 修改金币++
* 塔的等级++
* 伤害值++
* 挖矿速度++
* **游戏乐趣--**

## 开发

### 开发测试环境
* Android Studio 3.5.1
* Gradle 3.5.1
* Android 10
* EdXposed SandHook 0.4.6 beta(4476)

开发时可以在开发时把 `xposed_init` 里面的 `com.zhangnew.tdi2.hook.Tdi2Hook` 修改为 `com.zhangnew.tdi2.hotfix.HookLoader`；
这样在安装模块之后只有第一次需要重启，以后开发修改 Hook 代码后，就可以直接杀掉游戏后台，重新进入游戏就可以实现新的 Hook 功能。

## 其他事项
* 感谢 @shuihuadx 开发的[免重启开发 Xposed 模块](https://github.com/shuihuadx/XposedHook)
* 本工程的内容仅能用于学习和研究，请勿用于任何商业用途，否则由此带来的法律责任由操作者自己承担，和本人无关。

## Licence
GPL
