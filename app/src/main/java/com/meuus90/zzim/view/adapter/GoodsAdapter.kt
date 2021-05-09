package com.meuus90.zzim.view.adapter

import android.content.Context
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.meuus90.zzim.R
import com.meuus90.zzim.common.BigDecimalExt.formatNumberString
import com.meuus90.zzim.common.ViewExt.gone
import com.meuus90.zzim.common.ViewExt.show
import com.meuus90.zzim.databinding.ItemBannerBinding
import com.meuus90.zzim.databinding.ItemProductBinding
import com.meuus90.zzim.model.data.GoodsDataModel
import com.meuus90.zzim.model.data.response.Goods
import com.meuus90.zzim.view.activity.BaseActivity


class GoodsAdapter(
    private val context: Context,
    val doOnClick: (goods: Goods, isFavorite: Boolean) -> Unit
) : PagingDataAdapter<GoodsDataModel, RecyclerView.ViewHolder>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<GoodsDataModel>() {
                override fun areItemsTheSame(
                    oldItem: GoodsDataModel,
                    newItem: GoodsDataModel
                ): Boolean {
                    return if (oldItem is GoodsDataModel.Item && newItem is GoodsDataModel.Item) {
                        oldItem.goods.id == newItem.goods.id
                    } else if (oldItem is GoodsDataModel.Header && newItem is GoodsDataModel.Header) {
                        oldItem.banners == newItem.banners
                    } else {
                        oldItem == newItem
                    }
                }

                override fun areContentsTheSame(
                    oldItem: GoodsDataModel,
                    newItem: GoodsDataModel
                ): Boolean {
                    return if (oldItem is GoodsDataModel.Item && newItem is GoodsDataModel.Item) {
                        oldItem.goods.name == newItem.goods.name
                    } else if (oldItem is GoodsDataModel.Header && newItem is GoodsDataModel.Header) {
                        oldItem.banners == newItem.banners
                    } else {
                        oldItem == newItem
                    }
                }
            }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val contextThemeWrapper =
            ContextThemeWrapper(parent.context.applicationContext, R.style.AppTheme)

        return when (viewType) {
            GoodsDataModel.DataType.HEADER.ordinal -> {
                val binding =
                    ItemBannerBinding.inflate(
                        LayoutInflater.from(contextThemeWrapper),
                        parent,
                        false
                    )
                BannerHolder(binding, this)
            }
            else -> {
                val binding =
                    ItemProductBinding.inflate(
                        LayoutInflater.from(contextThemeWrapper),
                        parent,
                        false
                    )
                GoodsItemHolder(binding, this)
            }
        }
    }

    @ExperimentalStdlibApi
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BannerHolder -> holder.bind(getItem(position) as GoodsDataModel.Header)
            is GoodsItemHolder -> holder.bind(getItem(position) as GoodsDataModel.Item, position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position)?.type?.ordinal ?: GoodsDataModel.DataType.ITEM.ordinal
    }

    var favoritesSet = hashSetOf<Int>()
    fun updateFavorite(favorites: List<Goods>) {
        favoritesSet.clear()
        favoritesSet.addAll(favorites.map { it.id })
        notifyDataSetChanged()
    }

    var bannerAdapter: BannerViewPagerAdapter? = null

    class BannerHolder(
        private val binding: ItemBannerBinding,
        private val adapter: GoodsAdapter
    ) : RecyclerView.ViewHolder(binding.root) {
        @ExperimentalStdlibApi
        fun bind(item: GoodsDataModel.Header) {
            val banners = item.banners
            binding.viewPager.let {
                if (adapter.bannerAdapter == null)
                    adapter.bannerAdapter =
                        BannerViewPagerAdapter.getInstance(adapter.context as BaseActivity<*>)

                it.adapter = adapter.bannerAdapter
                adapter.bannerAdapter?.let { it.setItems(banners) }

                it.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                    override fun onPageScrolled(
                        position: Int,
                        positionOffset: Float,
                        positionOffsetPixels: Int
                    ) {
                    }

                    override fun onPageSelected(position: Int) {
                        binding.tvPosition.text =
                            adapter.context.getString(
                                R.string.product_banner_count,
                                position + 1,
                                banners.size
                            )
                    }

                    override fun onPageScrollStateChanged(state: Int) {
                    }
                })
            }
            binding.tvPosition.text =
                adapter.context.getString(
                    R.string.product_banner_count,
                    1,
                    banners.size
                )
        }
    }

    class GoodsItemHolder(
        private val binding: ItemProductBinding,
        private val adapter: GoodsAdapter
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
        fun bind(item: GoodsDataModel.Item, position: Int) {
            (adapter.context as BaseActivity<*>).glideRequestManager
                .asBitmap().clone()
                .load(item.goods.image)
                .centerCrop()
                .placeholder(placeholderResList[position % 6])
                .into(binding.ivProductImage)

            if (item.goods.isSale()) binding.tvSale.show() else binding.tvSale.gone()
            binding.tvSale.text =
                adapter.context.getString(
                    R.string.product_percent,
                    item.goods.getSale().formatNumberString()
                )

            binding.tvPrice.text = item.goods.actual_price.formatNumberString()
//                tvName.text = item.goods.name
            binding.tvName.text = item.goods.id.toString()

            if (item.goods.is_new) binding.tvNew.show() else binding.tvNew.gone()
            binding.tvBuying.text =
                adapter.context.getString(
                    R.string.product_buying,
                    item.goods.sell_count.formatNumberString()
                )

            val isFavotire = adapter.favoritesSet.contains(item.goods.id)
            binding.cbFavorite.isChecked = isFavotire

            binding.container.setOnClickListener {
                adapter.doOnClick(item.goods, isFavotire)
            }
        }
    }
}