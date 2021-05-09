package com.meuus90.zzim.view

import android.content.Context
import android.content.Intent
import com.meuus90.zzim.R
import com.meuus90.zzim.common.util.BigDecimalExt.formatNumberString
import com.meuus90.zzim.model.data.response.Goods

object Caller {
    internal fun shareFavorites(context: Context, link: String, goods: List<Goods>) {
        val sb = StringBuilder()
            .appendLine(link)
            .appendLine()

        goods.forEach {
            sb.append(it.name)
                .append(" ")
                .appendLine(it.price.formatNumberString())
                .appendLine()
        }

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.app_name))
            putExtra(Intent.EXTRA_TEXT, sb.toString())
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        context.startActivity(Intent.createChooser(intent, context.getString(R.string.app_name)))
    }
}