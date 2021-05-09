package com.meuus90.zzim.view.adapter

import android.content.Context
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.meuus90.zzim.R
import com.meuus90.zzim.common.util.BigDecimalExt.formatNumberString
import com.meuus90.zzim.common.util.ViewExt.gone
import com.meuus90.zzim.common.util.ViewExt.show
import com.meuus90.zzim.databinding.ItemProductBinding
import com.meuus90.zzim.model.data.response.Goods
import com.meuus90.zzim.view.activity.BaseActivity

class FavoritesAdapter(
    private val context: Context,
    val doOnClick: (goods: Goods) -> Unit
) : PagingDataAdapter<Goods, RecyclerView.ViewHolder>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<Goods>() {
                override fun areItemsTheSame(
                    oldItem: Goods,
                    newItem: Goods
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: Goods,
                    newItem: Goods
                ): Boolean {
                    return oldItem.name == newItem.name
                }
            }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val contextThemeWrapper =
            ContextThemeWrapper(parent.context.applicationContext, R.style.AppTheme)
        val binding =
            ItemProductBinding.inflate(
                LayoutInflater.from(contextThemeWrapper),
                parent,
                false
            )
        return GoodsItemHolder(binding, this)
    }

    @ExperimentalStdlibApi
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is GoodsItemHolder) holder.bind(getItem(position) as Goods, position)
    }

    class GoodsItemHolder(
        private val binding: ItemProductBinding,
        private val adapter: FavoritesAdapter
    ) : RecyclerView.ViewHolder(binding.root) {
        val placeholderResList = listOf(
            R.drawable.placeholder0,
            R.drawable.placeholder1,
            R.drawable.placeholder2,
            R.drawable.placeholder3,
            R.drawable.placeholder4,
            R.drawable.placeholder5
        )

        @ExperimentalStdlibApi
        fun bind(item: Goods, position: Int) {
            (adapter.context as BaseActivity<*>).glideRequestManager
                .asBitmap().clone()
                .load(item.image)
                .centerCrop()
                .placeholder(placeholderResList[position % 6])
                .into(binding.ivProductImage)

            if (item.isSale()) binding.tvSale.show() else binding.tvSale.gone()
            binding.tvSale.text =
                adapter.context.getString(
                    R.string.product_percent,
                    item.getSale().formatNumberString()
                )

            binding.tvPrice.text = item.actual_price.formatNumberString()
            binding.tvName.text = item.name

            if (item.is_new) binding.tvNew.show() else binding.tvNew.gone()
            binding.tvBuying.text =
                adapter.context.getString(
                    R.string.product_buying,
                    item.sell_count.formatNumberString()
                )

            binding.cbFavorite.isChecked = true

            binding.container.setOnClickListener {
                adapter.doOnClick(item)
            }
        }
    }
}