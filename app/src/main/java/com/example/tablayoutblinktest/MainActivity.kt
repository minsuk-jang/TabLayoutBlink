package com.example.tablayoutblinktest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.tablayoutblinktest.databinding.ActivityMainBinding
import com.example.tablayoutblinktest.model.TabStatus
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

const val TAG = "jms8732"

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val tabNames = listOf("테스트1", "테스트2", "테스트3")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind {
            lifecycleOwner = this@MainActivity
            viewPager.adapter = CustomAdapter()
            viewPager1.adapter = CustomAdapter()
        }

        initTabLayout()
        initTabLayout1()
    }

    private fun initTabLayout() {
        bind {
            with(tabLayout) {
                addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
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

    private fun initTabLayout1() {
        bind {
            with(tabLayout1) {
                tabNames.forEachIndexed { index, s ->
                    addTab(newTab().apply {
                        customView = CustomTabView(this@MainActivity).apply {
                            status = TabStatus(s, index == 0)
                        }
                    })
                }

                addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                    override fun onTabSelected(tab: TabLayout.Tab?) {
                        //Log.e(TAG, "onTabSelected: ${tab?.position}")
                        (tab?.customView as? CustomTabView)?.bind {
                            status = status.copy(
                                status = true
                            )
                        }
                    }

                    override fun onTabUnselected(tab: TabLayout.Tab?) {
                        //Log.e(TAG, "onTabUnselected: ${tab?.position}")
                        (tab?.customView as? CustomTabView)?.bind {
                            status = status.copy(
                                status = false
                            )
                        }
                    }

                    override fun onTabReselected(tab: TabLayout.Tab?) {
                    }
                })
            }
        }

        binding.viewPager1.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                Log.e(TAG, "[onPageScrolled]: $positionOffset position: $position", )
                binding.tabLayout1.setScrollPosition(position, positionOffset, true, true)
                if (positionOffset == 0.0f) {
                val tab = binding.tabLayout1.getTabAt(position)
                binding.tabLayout1.selectTab(tab)
            }
            }

            override fun onPageSelected(position: Int) {
                Log.e(TAG, "[onPageSelected]: $position")
                /*val tab = binding.tabLayout1.getTabAt(position)
                binding.tabLayout1.selectTab(tab, true)
                binding.viewPager1.setCurrentItem(tab?.position ?: 0, true)*/
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    inner class CustomAdapter : FragmentStateAdapter(this) {
        override fun getItemCount(): Int = tabNames.size

        override fun createFragment(position: Int): Fragment {
            return BodyFragment.newInstance(position)
        }
    }
}