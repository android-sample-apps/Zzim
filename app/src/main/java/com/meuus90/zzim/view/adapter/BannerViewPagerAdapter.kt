package com.meuus90.zzim.view.adapter

import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.meuus90.zzim.R
import com.meuus90.zzim.databinding.ItemBannerImageBinding
import com.meuus90.zzim.model.data.response.Banner
import com.meuus90.zzim.view.activity.BaseActivity


class BannerViewPagerAdapter(private val activity: BaseActivity<*>) :
    PagerAdapter() {
    private val banners = arrayListOf<Banner>()

    companion object {
        fun getInstance(activity: BaseActivity<*>) = BannerViewPagerAdapter(activity)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == (`object` as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): View {
        val contextThemeWrapper =
            ContextThemeWrapper(container.context.applicationContext, R.style.AppTheme)

        val binding = ItemBannerImageBinding.inflate(
            LayoutInflater.from(contextThemeWrapper),
            container,
            false
        )

        activity.glideRequestManager
            .asBitmap().clone()
            .load(banners[position].image)
            .centerCrop()
            .dontAnimate()
            .into(binding.container)

        container.addView(binding.root)
        return binding.root
    }

    override fun getCount() = banners.size

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        (container as ViewPager).removeView(`object` as ImageView)
    }

    fun setItems(url: List<Banner>) {
        banners.clear()
        banners.addAll(url)
        notifyDataSetChanged()
    }
}