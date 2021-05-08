package com.meuus90.zzim.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.meuus90.zzim.databinding.FragmentHomeBinding
import com.meuus90.zzim.view.BaseFragment
import com.meuus90.zzim.view.adapter.GoodsAdapter

class FavoriteFragment : BaseFragment<FragmentHomeBinding>() {
    companion object {
        fun newInstance() = FavoriteFragment().apply {
            arguments = Bundle(1).apply {
                putString(FRAGMENT_TAG, FavoriteFragment::class.java.name)
            }
        }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    internal lateinit var adapter: GoodsAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = GoodsAdapter(baseActivity) { goods ->
        }

        adapter.setHasStableIds(true)
        with(binding) {
            recyclerView.adapter = adapter
            swipeLayout

        }

    }

}