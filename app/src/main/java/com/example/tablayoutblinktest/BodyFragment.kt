package com.example.tablayoutblinktest

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.tablayoutblinktest.databinding.FragmentBodyBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.FragmentScoped

const val POSITION = "position"

@AndroidEntryPoint
@FragmentScoped
class BodyFragment : BaseFragment<FragmentBodyBinding>(R.layout.fragment_body) {
    companion object {
        fun newInstance(position: Int) = BodyFragment().apply {
            arguments = Bundle().apply {
                putInt(POSITION, position)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind {
            lifecycleOwner = viewLifecycleOwner
        }

        bindView()
    }

    private fun bindView() {
        bind {
            textView.text = "테스트 ${arguments?.getInt(POSITION) ?: -1}"
        }
    }
}
