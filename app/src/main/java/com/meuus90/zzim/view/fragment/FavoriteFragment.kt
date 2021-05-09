package com.meuus90.zzim.view.fragment

import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.internal.ViewUtils
import com.meuus90.zzim.databinding.FragmentFavoriteBinding
import com.meuus90.zzim.model.data.response.Goods
import com.meuus90.zzim.view.adapter.FavoritesAdapter
import com.meuus90.zzim.viewmodel.FavoriteViewModel
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>() {
    companion object {
        fun newInstance() = FavoriteFragment().apply {
            arguments = Bundle(1).apply {
                putString(FRAGMENT_TAG, FavoriteFragment::class.java.name)
            }
        }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentFavoriteBinding
        get() = FragmentFavoriteBinding::inflate

    @Inject
    internal lateinit var favoriteViewModel: FavoriteViewModel

    internal lateinit var adapter: FavoritesAdapter

    @SuppressLint("RestrictedApi")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = FavoritesAdapter(baseActivity) { goods ->
            removeFavorite(goods)
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
                        val spanIndex =
                            (view.layoutParams as GridLayoutManager.LayoutParams).spanIndex
                        if (spanIndex == 0) {
                            outRect.left = spacing * 2
                            outRect.right = spacing
                        } else {
                            outRect.left = spacing
                            outRect.right = spacing * 2
                        }

                        if (position > 1) outRect.top = spacing
                        else outRect.top = spacing * 2

                        outRect.bottom = spacing
                    }
                }
            )

            val layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
            layoutManager.isItemPrefetchEnabled = true
            recyclerView.layoutManager = layoutManager
        }

        lifecycleScope.launchWhenCreated {
            favoriteViewModel.goods
                .collectLatest {
                    adapter.submitData(it)
                }
        }
    }

    private fun removeFavorite(goods: Goods) {
        favoriteViewModel.removeFavorite(goods)
    }
}