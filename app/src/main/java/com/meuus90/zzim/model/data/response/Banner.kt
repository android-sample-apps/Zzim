package com.meuus90.zzim.model.data.response

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "BannerDoc")
data class Banner(
    @field:PrimaryKey
    @field:ColumnInfo(name = "id") val id: Int,
    @field:ColumnInfo(name = "image") val image: String
)