package com.majiangfang.zo.library.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.majiangfang.zo.library.app.demo.ZoLogDemoActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var tvLog = findViewById<TextView>(R.id.tv_zolog)
        tvLog.setOnClickListener {
            startActivity(Intent(this, ZoLogDemoActivity::class.java))
        }
    }
}