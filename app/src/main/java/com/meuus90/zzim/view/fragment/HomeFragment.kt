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
import com.meuus90.zzim.common.ViewExt.show
import com.meuus90.zzim.databinding.FragmentHomeBinding
import com.meuus90.zzim.model.data.request.Query
import com.meuus90.zzim.view.BaseFragment
import com.meuus90.zzim.view.adapter.GoodsAdapter
import com.meuus90.zzim.viewmodel.GoodsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import javax.inject.Inject

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

    @Inject
    internal lateinit var goodsViewModel: GoodsViewModel

    internal lateinit var adapter: GoodsAdapter

    @SuppressLint("RestrictedApi")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = GoodsAdapter(baseActivity) { goods ->
        }

        adapter.setHasStableIds(true)
        with(binding) {
            recyclerView.adapter = adapter
//            recyclerView.setItemViewCacheSize(AppConfig.recyclerViewCacheSize)
//            recyclerView.setHasFixedSize(false)
//            recyclerView.isVerticalScrollBarEnabled = false
//            recyclerView.addItemDecoration(
//                GridSpacingItemDecoration(
//                    2,
//                    dpToPx(context, 12).toInt(),
//                    true
//                )
//            )

            val layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
//            layoutManager.isItemPrefetchEnabled = true
            recyclerView.layoutManager = layoutManager

            swipeLayout.setOnRefreshListener {
                adapter.refresh()
            }
        }


        lifecycleScope.launchWhenCreated {
            goodsViewModel.goods
                .collectLatest {
//                    binding.recyclerView.show()
                    adapter.submitData(it)
                }
        }

        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow
                .distinctUntilChangedBy { it.append }
                .collectLatest {
                    val state = it.append
//                    if (state is LoadState.Error) // todo popup
//                        updateErrorUI(state)
//                    else {
//                        introViewModel.getCollectionList(searchSchema.collection)
//
//                        recyclerView.show()
//                        v_error.gone()
//                    }
                }
        }

        lifecycleScope.launchWhenCreated {
            @OptIn(ExperimentalCoroutinesApi::class)
            adapter.loadStateFlow
                .collectLatest { loadStates ->
                    binding.swipeLayout.isRefreshing =
                        loadStates.refresh is LoadState.Loading/* && adapter.itemCount < 1*/
                }
        }

        initialize()
    }

    private fun initialize(){
        goodsViewModel.pullTrigger(Query())
    }
}