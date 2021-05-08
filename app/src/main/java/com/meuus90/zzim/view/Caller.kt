package com.meuus90.zzim.view

import android.content.Context
import android.content.Intent
import com.meuus90.zzim.view.activity.MainActivity

object Caller {
    internal fun openMainActivity(context: Context) {
        val intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent)
    }
}