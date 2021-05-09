package com.meuus90.zzim.model.data

import com.meuus90.zzim.model.data.response.Banner
import com.meuus90.zzim.model.data.response.Goods
import com.meuus90.zzim.model.data.response.Home
import com.meuus90.zzim.model.data.response.NextGoods
import com.meuus90.zzim.testkit.DataClassTestTool
import org.junit.Assert
import org.junit.Test
import java.math.BigDecimal

class ResponseTest {
    companion object {
        val mock by lazy { ResponseTest() }
        fun getInstance() = mock
    }
    val mockBanner0 =
        Banner(1, "https://img.a-bly.com/banner/images/banner_image_1615465448476691.jpg")
    val mockBanner1 =
        Banner(1, "https://img.a-bly.com/banner/images/banner_image_1615465448476691.jpg")

    @Test
    fun BannerTest() {
        Assert.assertTrue(
            DataClassTestTool.checkProperties(
                mockBanner0,
                mockBanner1
            )
        )
    }

    val mockGoods0 = Goods(
        1,
        "[선물포장/별자리각인] 집들이 선물 원목 별자리 인센스 스틱 홀더 3type 호두나무 참나무 단풍나무 인테리어소품 감성소품 캠핑용품",
        "https://d20s70j9gw443i.cloudfront.net/t_GOODS_THUMB_WEBP/https://imgb.a-bly.com/data/goods/20210122_1611290798811044s.jpg",
        false,
        BigDecimal(61),
        BigDecimal(18000),
        BigDecimal(1600)
    )
    val mockGoods1 =
        Goods(
            1,
            "[선물포장/별자리각인] 집들이 선물 원목 별자리 인센스 스틱 홀더 3type 호두나무 참나무 단풍나무 인테리어소품 감성소품 캠핑용품",
            "https://d20s70j9gw443i.cloudfront.net/t_GOODS_THUMB_WEBP/https://imgb.a-bly.com/data/goods/20210122_1611290798811044s.jpg",
            false,
            BigDecimal(61),
            BigDecimal(18000),
            BigDecimal(1600)
        )

    @Test
    fun GoodsTest() {
        Assert.assertTrue(
            DataClassTestTool.checkProperties(
                mockGoods0,
                mockGoods1
            )
        )
    }

    val mockHome0 = Home(mutableListOf(mockBanner0), mutableListOf(mockGoods0))
    val mockHome1 = Home(mutableListOf(mockBanner1), mutableListOf(mockGoods1))

    @Test
    fun HomeTest() {
        Assert.assertTrue(
            DataClassTestTool.checkProperties(
                mockHome0,
                mockHome1
            )
        )
    }

    val mockNextGoods0 = NextGoods(mutableListOf(mockGoods0))
    val mockNextGoods1 = NextGoods(mutableListOf(mockGoods1))

    @Test
    fun NextGoodsTest() {
        Assert.assertTrue(
            DataClassTestTool.checkProperties(
                mockNextGoods0,
                mockNextGoods1
            )
        )
    }
}