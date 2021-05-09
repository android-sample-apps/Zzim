package com.meuus90.zzim.view.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.meuus90.zzim.R
import com.meuus90.zzim.common.firebase.DynamicLinkManager
import com.meuus90.zzim.databinding.ActivityMainBinding
import com.meuus90.zzim.model.data.response.Goods
import com.meuus90.zzim.view.Caller
import com.meuus90.zzim.view.fragment.BaseFragment
import com.meuus90.zzim.view.fragment.FavoriteFragment
import com.meuus90.zzim.view.fragment.HomeFragment
import com.meuus90.zzim.viewmodel.FavoriteViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    private val fragments: List<BaseFragment<*>> = listOf(
        HomeFragment.newInstance(),
        FavoriteFragment.newInstance()
    )

    @Inject
    internal lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewPager.let {
            it.adapter = object : FragmentStateAdapter(this) {
                override fun getItemCount(): Int {
                    return fragments.size
                }

                override fun createFragment(position: Int): Fragment {
                    return fragments[position]
                }
            }
            it.isUserInputEnabled = false
        }

        with(binding) {
            bottomNavigation.setOnNavigationItemSelectedListener {
                tvToolbar.text = it.title
                viewPager.currentItem = when (it.itemId) {
                    R.id.nav_home -> 0
                    else -> 1
                }
                appbarLayout.setExpanded(true)
                true
            }

            bottomNavigation.selectedItemId = R.id.nav_home
            setSupportActionBar(toolbar)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_share, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_download -> {
                shareList()
                true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    private fun shareList() {
        lifecycleScope.launch(Dispatchers.IO) {
            val list = favoriteViewModel.getFavoriteList()
            if (list.isEmpty()) {
                this.launch(Dispatchers.Main) {
                    createDialog(getString(R.string.error_no_favorite_list))
                }
            } else {
                makeLink(list)
            }
        }
    }

    private fun makeLink(list: List<Goods>) {
        lifecycleScope.launch(Dispatchers.Main) {
            DynamicLinkManager.makeDynamicLink { link ->
                Caller.shareFavorites(this@MainActivity, link, list)
            }
        }
    }
}