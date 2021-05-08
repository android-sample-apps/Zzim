package com.meuus90.zzim.view.adapter

import android.content.Context
import android.graphics.Color
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.meuus90.zzim.R
import com.meuus90.zzim.common.BigDecimalExt.formatNumberString
import com.meuus90.zzim.common.ViewExt.gone
import com.meuus90.zzim.common.ViewExt.show
import com.meuus90.zzim.databinding.ItemProductBinding
import com.meuus90.zzim.model.data.response.Goods
import com.meuus90.zzim.view.BaseActivity

class GoodsAdapter(
    private val context: Context,
    val doOnClick: (goods: Goods) -> Unit
) : PagingDataAdapter<Goods, BaseViewHolder<Goods>>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<Goods>() {
                override fun areItemsTheSame(oldItem: Goods, newItem: Goods): Boolean =
                    oldItem.id == newItem.id

                override fun areContentsTheSame(oldItem: Goods, newItem: Goods): Boolean =
                    oldItem == newItem
            }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<Goods> {
        val contextThemeWrapper =
            ContextThemeWrapper(parent.context.applicationContext, R.style.AppTheme)

        val binding =
            ItemProductBinding.inflate(LayoutInflater.from(contextThemeWrapper), parent, false)

        return GoodsItemHolder(binding, this)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<Goods>, position: Int) {
        val item = getItem(position)

        item?.let {
            if (holder is GoodsItemHolder) holder.bindItemHolder(holder, it, position)
        }
    }

    class GoodsItemHolder(
        private val binding: ItemProductBinding,
        private val adapter: GoodsAdapter
    ) : BaseViewHolder<Goods>(binding.root) {
        @ExperimentalStdlibApi
        override fun bindItemHolder(
            holder: BaseViewHolder<Goods>,
            item: Goods,
            position: Int
        ) {
            val placeholderResList = listOf(
                R.drawable.placeholder0,
                R.drawable.placeholder1,
                R.drawable.placeholder2,
                R.drawable.placeholder3,
                R.drawable.placeholder4,
                R.drawable.placeholder5
            )

            (adapter.context as BaseActivity<*>).glideRequestManager
                .asBitmap().clone()
                .load(item.image)
                .placeholder(placeholderResList[position % 6])
                .dontAnimate()
                .into(binding.ivProductImage)

            if (item.isSale()) binding.tvSale.show() else binding.tvSale.gone()
            binding.tvSale.text =
                context.getString(R.string.product_percent, item.getSale().formatNumberString())

            binding.tvPrice.text = item.actual_price.formatNumberString()
//                tvName.text = item.name
            binding.tvName.text = item.id

            if (item.is_new) binding.tvNew.show() else binding.tvNew.gone()
            binding.tvBuying.text =
                context.getString(R.string.product_buying, item.sell_count.formatNumberString())

            holder.view.setOnClickListener {
                adapter.doOnClick(item)
            }
        }

        override fun onItemSelected() {
            view.setBackgroundColor(Color.LTGRAY)
        }

        override fun onItemClear() {
            view.setBackgroundColor(0)
        }
    }
}