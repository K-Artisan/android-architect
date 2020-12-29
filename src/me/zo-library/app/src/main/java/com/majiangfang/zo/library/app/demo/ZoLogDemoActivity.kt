package com.majiangfang.zo.library.app.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.majiangfang.zo.library.app.R
import com.majiangfang.zo.library.log.*

class ZoLogDemoActivity : AppCompatActivity() {
    var viewLogPrinter: ZoViewLogPrinter? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zo_log_demo)

        findViewById<View>(R.id.btn_log).setOnClickListener {
            printLog()
        }

        //Log显示在界面上
        viewLogPrinter = ZoViewLogPrinter(this);
        viewLogPrinter!!.viewProvider.showFloatingView();
    }

    private fun printLog() {
        ZoLogManager.getInstance().addPrinter(viewLogPrinter)

        //自定义Log配置
        ZoLog.log(object : ZoLogConfig() {
            override fun includeThread(): Boolean {
                return true
            }

            override fun stackTraceDepth(): Int {
                return 3;
            }
        }, ZoLogType.E, "---", "5566")

        ZoLog.a("9900")
    }
}