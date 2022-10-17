package com.example.tablayoutblinktest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tablayoutblinktest.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind {
            lifecycleOwner = this@MainActivity
        }

    }
}