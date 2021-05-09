package com.meuus90.zzim.model.data

import com.meuus90.zzim.model.data.response.Banner
import com.meuus90.zzim.model.data.response.Goods


sealed class GoodsDataModel(val type: DataType) {
    data class Item(val goods: Goods) : GoodsDataModel(DataType.ITEM)
    data class Header(val banners: List<Banner>) : GoodsDataModel(DataType.HEADER)

    enum class DataType { HEADER, ITEM }
}