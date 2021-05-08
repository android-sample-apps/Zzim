package com.meuus90.zzim.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.internal.ViewUtils.dpToPx
import com.meuus90.zzim.common.AppConfig
import com.meuus90.zzim.common.GridSpacingItemDecoration
import com.meuus90.zzim.databinding.FragmentHomeBinding
import com.meuus90.zzim.view.BaseFragment
import com.meuus90.zzim.view.adapter.GoodsAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    companion object {
        fun newInstance() = HomeFragment().apply {
            arguments = Bundle(1).apply {
                putString(FRAGMENT_TAG, HomeFragment::class.java.name)
            }
        }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    internal lateinit var adapter: GoodsAdapter

    @SuppressLint("RestrictedApi")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = GoodsAdapter(baseActivity) { goods ->
        }

        adapter.setHasStableIds(true)
        with(binding) {
            recyclerView.adapter = adapter
            recyclerView.setItemViewCacheSize(AppConfig.recyclerViewCacheSize)
            recyclerView.setHasFixedSize(false)
            recyclerView.isVerticalScrollBarEnabled = false
            recyclerView.addItemDecoration(
                GridSpacingItemDecoration(
                    2,
                    dpToPx(context, 12).toInt(),
                    true
                )
            )

            val layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
            layoutManager.isItemPrefetchEnabled = true
            recyclerView.layoutManager = layoutManager

            swipeLayout.setOnRefreshListener {
                getNextList()
            }
        }

        lifecycleScope.launchWhenCreated {
            @OptIn(ExperimentalCoroutinesApi::class)
            adapter.loadStateFlow
                .collectLatest { loadStates ->
                    binding.swipeLayout.isRefreshing =
                        loadStates.refresh is LoadState.Loading && adapter.itemCount < 1
                }
        }

        initialize()
    }

    fun initialize(){

    }

    fun getNextList(){

    }
}