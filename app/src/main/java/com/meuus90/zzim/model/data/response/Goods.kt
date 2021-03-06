package com.meuus90.zzim.model.data.response

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.meuus90.zzim.model.data.BaseData
import kotlinx.android.parcel.Parcelize
import java.math.BigDecimal
import java.math.RoundingMode

@Parcelize
@Entity(tableName = "GoodsDoc")
data class Goods(
    @field:PrimaryKey
    @field:ColumnInfo(name = "id") val id: Int,
    @field:ColumnInfo(name = "name") val name: String,
    @field:ColumnInfo(name = "image") val image: String,
    @field:ColumnInfo(name = "is_new") val is_new: Boolean,
    @field:ColumnInfo(name = "sell_count") val sell_count: BigDecimal,
    @field:ColumnInfo(name = "actual_price") val actual_price: BigDecimal,
    @field:ColumnInfo(name = "price") val price: BigDecimal
) : BaseData(), Parcelable {
    fun getSale() =
        (actual_price - price).multiply(100.toBigDecimal())
            .divide(actual_price, 0, RoundingMode.HALF_DOWN)

    fun isSale() = actual_price != price
}