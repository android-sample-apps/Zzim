package com.meuus90.zzim.model.data.response

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.math.RoundingMode

@Entity(tableName = "GoodsDoc")
data class Goods(
    @field:PrimaryKey
    @field:ColumnInfo(name = "id") val id: String,
    @field:ColumnInfo(name = "name") val name: String,
    @field:ColumnInfo(name = "image") val image: String,
    @field:ColumnInfo(name = "is_new") val is_new: Boolean,
    @field:ColumnInfo(name = "sell_count") val sell_count: BigDecimal,
    @field:ColumnInfo(name = "actual_price") val actual_price: BigDecimal,
    @field:ColumnInfo(name = "price") val price: BigDecimal
) {
    fun getSale() =
        (price - actual_price).multiply(100.toBigDecimal()).divide(price, 0, RoundingMode.HALF_DOWN)

    fun isSale() = price == actual_price
}