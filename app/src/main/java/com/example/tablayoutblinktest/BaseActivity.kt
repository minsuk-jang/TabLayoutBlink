package com.example.tablayoutblinktest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding


abstract class BaseActivity<T : ViewDataBinding>(private val layId: Int) : AppCompatActivity() {
    private var _binding: T? = null
    protected val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = DataBindingUtil.setContentView(this, layId)
        setContentView(binding.root)
    }

    protected fun bind(block: T.() -> Unit) {
        binding.block()
    }
}