package com.meuus90.zzim.view.fragment

import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.internal.ViewUtils
import com.meuus90.zzim.databinding.FragmentHomeBinding
import com.meuus90.zzim.model.data.request.Query
import com.meuus90.zzim.model.data.response.Goods
import com.meuus90.zzim.view.adapter.GoodsAdapter
import com.meuus90.zzim.viewmodel.FavoriteViewModel
import com.meuus90.zzim.viewmodel.GoodsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
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

    @Inject
    internal lateinit var favoriteViewModel: FavoriteViewModel

    internal lateinit var adapter: GoodsAdapter

    @ExperimentalCoroutinesApi
    @SuppressLint("RestrictedApi")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = GoodsAdapter(baseActivity) { goods, isFavorite ->
            if (isFavorite)
                removeFavorite(goods)
            else
                addFavorite(goods)
        }
        with(binding) {
            recyclerView.adapter = adapter
            recyclerView.addItemDecoration(
                object : RecyclerView.ItemDecoration() {
                    val spacing = ViewUtils.dpToPx(context, 6).toInt()

                    override fun getItemOffsets(
                        outRect: Rect,
                        view: View,
                        parent: RecyclerView,
                        state: RecyclerView.State
                    ) {
                        val position = parent.getChildAdapterPosition(view)
                        if (position > 0) {
                            val spanIndex =
                                (view.layoutParams as GridLayoutManager.LayoutParams).spanIndex
                            if (spanIndex == 0) {
                                outRect.left = spacing * 2
                                outRect.right = spacing
                            } else {
                                outRect.left = spacing
                                outRect.right = spacing * 2
                            }

                            outRect.top = spacing
                        }
                        outRect.bottom = spacing
                    }
                }
            )

            (recyclerView.layoutManager as GridLayoutManager).spanSizeLookup =
                object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int) = if (position == 0) 2 else 1
                }

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
            favoriteViewModel.goodsFlow
                .collectLatest {
                    adapter.updateFavorite(it)
                }
        }

        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow
                .distinctUntilChangedBy { it.append }
                .collectLatest {
                    val state = it.append
                    //todo check error state
                }
        }

        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow
                .collectLatest { loadStates ->
                    binding.swipeLayout.isRefreshing =
                        loadStates.refresh is LoadState.Loading/* && adapter.itemCount < 1*/
                }
        }

        initialize()
    }

    private fun initialize() {
        goodsViewModel.pullTrigger(Query())
    }

    private fun addFavorite(goods: Goods) {
        favoriteViewModel.addFavorite(goods)
    }

    private fun removeFavorite(goods: Goods) {
        favoriteViewModel.removeFavorite(goods)
    }
}