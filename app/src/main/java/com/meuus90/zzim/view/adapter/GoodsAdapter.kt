package com.meuus90.zzim.view.adapter

import android.content.Context
import android.graphics.Color
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.PrecomputedTextCompat
import androidx.core.widget.TextViewCompat
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.meuus90.zzim.R
import com.meuus90.zzim.common.BigDecimalExt.formatNumberString
import com.meuus90.zzim.databinding.ItemProductBinding
import com.meuus90.zzim.model.data.Goods
import com.meuus90.zzim.view.BaseActivity
import com.meuus90.zzim.view.scheduler.UIJobScheduler

class GoodsAdapter(
    private val context: Context,
    val doOnClick: (goods: Goods) -> Unit
) : PagingDataAdapter<Goods, BaseViewHolder<Goods>>(DIFF_CALLBACK) {
    companion object {
        private val PAYLOAD_TITLE = Any()

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Goods>() {
            override fun areItemsTheSame(oldItem: Goods, newItem: Goods): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Goods, newItem: Goods): Boolean =
                oldItem == newItem

            override fun getChangePayload(oldItem: Goods, newItem: Goods): Any? =
                if (sameExceptTitle(oldItem, newItem)) PAYLOAD_TITLE else null
        }

        private fun sameExceptTitle(oldItem: Goods, newItem: Goods) =
            oldItem.copy(id = newItem.id) == newItem
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
            val coroutineScope = (adapter.context as BaseActivity<*>).lifecycleScope

            with(binding) {
                UIJobScheduler.submitJob(coroutineScope) {
                    adapter.context.glideRequestManager
                        .asBitmap().clone()
                        .load(item.image)
                        .placeholder(placeholderResList[position % 6])
                        .dontAnimate()
                        .into(ivProductImage)
                }

                if (item.isSale()) tvSale.visibility = View.VISIBLE
                else tvSale.visibility = View.GONE
                UIJobScheduler.submitJob(coroutineScope) {
                    tvSale.setTextFuture(
                        PrecomputedTextCompat.getTextFuture(
                            item.getSale().formatNumberString(),
                            TextViewCompat.getTextMetricsParams(tvSale),
                            null
                        )
                    )
                }

                UIJobScheduler.submitJob(coroutineScope) {
                    tvPrice.setTextFuture(
                        PrecomputedTextCompat.getTextFuture(
                            item.actual_price.formatNumberString(),
                            TextViewCompat.getTextMetricsParams(tvPrice),
                            null
                        )
                    )
                }

                UIJobScheduler.submitJob(coroutineScope) {
                    tvName.setTextFuture(
                        PrecomputedTextCompat.getTextFuture(
                            item.name,
                            TextViewCompat.getTextMetricsParams(tvName),
                            null
                        )
                    )
                }

                if (item.is_new) tvNew.visibility = View.VISIBLE
                else tvNew.visibility = View.GONE

                UIJobScheduler.submitJob(coroutineScope) {
                    tvBuying.setTextFuture(
                        PrecomputedTextCompat.getTextFuture(
                            context.getString(
                                R.string.product_buying,
                                item.sell_count.formatNumberString()
                            ),
                            TextViewCompat.getTextMetricsParams(tvBuying),
                            null
                        )
                    )
                }

                holder.view.setOnClickListener {
                    adapter.doOnClick(item)
                }
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