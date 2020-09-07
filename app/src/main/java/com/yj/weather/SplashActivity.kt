package com.yj.weather

import android.Manifest
import com.alibaba.android.arouter.launcher.ARouter
import com.permissionx.guolindev.PermissionX
import com.yj.weather.base.BaseActivity
import com.yj.weather.util.StatusBarUtil
import com.yj.weather.util.showToast

class SplashActivity : BaseActivity() {

    override fun layoutRes() = R.layout.activity_splash

    override fun initDate() {
        PermissionX.init(this)
            .permissions(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE
            )
            .onExplainRequestReason { scope, deniedList ->
                scope.showRequestReasonDialog(deniedList, " 核心功能基于这些权限", "OK", "Cancel")
            }
            .onForwardToSettings { scope, deniedList ->
                scope.showForwardToSettingsDialog(deniedList, "进入设置手动允许权限", "OK", "Cancel")
            }
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {
                    window.decorView.postDelayed(Runnable {
                        ARouter.getInstance().build(Contract.MAIN_ACTIVITY).navigation();
                        finish()
                    }, 2000)
                } else {
                    "您拒绝了如下权限：$deniedList".showToast()
                }
            }
    }

    override fun setStatusBar() {
        StatusBarUtil.setTransparent(this)
    }

}