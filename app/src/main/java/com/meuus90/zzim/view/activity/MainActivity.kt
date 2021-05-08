package com.meuus90.zzim.view.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.meuus90.zzim.R
import com.meuus90.zzim.databinding.ActivityMainBinding
import com.meuus90.zzim.view.BaseActivity
import com.meuus90.zzim.view.BaseFragment
import com.meuus90.zzim.view.fragment.FavoriteFragment
import com.meuus90.zzim.view.fragment.HomeFragment

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    private val fragments: List<BaseFragment<*>> = listOf(
        HomeFragment.newInstance(),
        FavoriteFragment.newInstance()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.fragmentContainer.let {
            it.adapter = object : FragmentStateAdapter(this) {
                override fun getItemCount(): Int {
                    return fragments.size
                }

                override fun createFragment(position: Int): Fragment {
                    return fragments[position]
                }
            }
        }

        binding.bottomNavigation.selectedItemId = 0
    }
}