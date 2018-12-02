package com.jericho2code.app_finance_manager

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class LauncherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}