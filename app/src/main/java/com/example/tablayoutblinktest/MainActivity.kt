package com.example.tablayoutblinktest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.tablayoutblinktest.databinding.ActivityMainBinding
import com.example.tablayoutblinktest.model.TabStatus
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val tabNames = listOf("테스트1", "테스트2", "테스트3")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind {
            lifecycleOwner = this@MainActivity
        }

        initViewPager2()
    }

    private fun initViewPager2() {
        bind {
            viewPager.adapter = CustomAdapter()

            with(tabLayout){
                addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
                    override fun onTabSelected(tab: TabLayout.Tab?) {
                        (tab?.customView as? CustomTabView)?.bind {
                            status = status.copy(
                                status = true
                            )
                        }
                    }

                    override fun onTabUnselected(tab: TabLayout.Tab?) {
                        (tab?.customView as? CustomTabView)?.bind {
                            status = status.copy(
                                status = false
                            )
                        }
                    }

                    override fun onTabReselected(tab: TabLayout.Tab?) {}
                })
            }
        }

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.customView = CustomTabView(this).apply {
                status = TabStatus("테스트 $position", position == 0)
            }
        }.attach()


    }

    inner class CustomAdapter : FragmentStateAdapter(this) {
        override fun getItemCount(): Int = tabNames.size

        override fun createFragment(position: Int): Fragment {
            return BodyFragment.newInstance(position)
        }
    }
}