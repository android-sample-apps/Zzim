package com.meuus90.zzim.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.meuus90.zzim.R
import com.meuus90.zzim.common.constant.AppConfig
import com.meuus90.zzim.common.firebase.DynamicLinkManager
import com.meuus90.zzim.databinding.ActivityMainBinding
import com.meuus90.zzim.model.data.BaseData
import com.meuus90.zzim.model.data.response.Goods
import com.meuus90.zzim.view.Caller
import com.meuus90.zzim.view.fragment.BaseFragment
import com.meuus90.zzim.view.fragment.FavoriteFragment
import com.meuus90.zzim.view.fragment.HomeFragment
import com.meuus90.zzim.viewmodel.FavoriteViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
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
        if (intent.action == Intent.ACTION_VIEW) {
            try {
                intent.data?.getQueryParameter(AppConfig.keyFavorites)?.let { json ->
                    val type = object : TypeToken<List<Goods>>() {}.type
                    BaseData.gson().fromJson<List<Goods>>(json, type)?.let {
                        replaceFavorites(it)
                    }
                }
            } catch (e: Exception) {
                Timber.e(e)
            }
        }

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
            DynamicLinkManager.makeDynamicLink(Gson().toJson(list)) { link ->
                Caller.shareFavorites(this@MainActivity, link, list)
            }
        }
    }

    private fun replaceFavorites(list: List<Goods>) {
        lifecycleScope.launch(Dispatchers.IO) {
            favoriteViewModel.updateFavorites(list)
        }
    }
}