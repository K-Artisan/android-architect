package com.majiangfang.zo.library.app

import android.app.Application
import com.google.gson.Gson
import com.majiangfang.zo.library.log.*
import java.util.*

class MApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ZoLogManager.init(object : ZoLogConfig() {
            override fun getGlobalTag(): String {
                return "MApplication";
            }

            override fun enable(): Boolean {
                return true;
            }

            override fun includeThread(): Boolean {
                return true
            }

            override fun stackTraceDepth(): Int {
                return 5
            }

            override fun injectJsonParser(): JsonParser? {
                return JsonParser { src -> Gson().toJson(src) }
            }
        }, ZoConsoleLogPrinter())
    }
}