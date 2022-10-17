package com.example.tablayoutblinktest

import android.content.Context
import android.content.res.Resources
import android.graphics.fonts.FontFamily
import android.graphics.fonts.FontStyle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.widget.TextViewCompat
import com.example.tablayoutblinktest.databinding.ViewCustomTabBinding
import com.example.tablayoutblinktest.model.TabStatus

class CustomTabView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private val binding = ViewCustomTabBinding.inflate(LayoutInflater.from(context))

    var status: TabStatus = TabStatus("", false)
        set(value) {
            field = value

            TextViewCompat.setTextAppearance(
                binding.textViewTab, when (field.status) {
                    true  -> FontStyle.FONT_SLANT_ITALIC
                    false -> FontStyle.FONT_WEIGHT_EXTRA_BOLD
                }
            )

            binding.status = field
            binding.executePendingBindings()
        }

    init {
        layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        addView(binding.root, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
    }

    fun bind(block: CustomTabView.() -> Unit) {
        block(this)
    }
}