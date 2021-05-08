package com.meuus90.zzim.common

import android.view.View

object ViewExt {
    fun View.show(): View {
        if (visibility != View.VISIBLE) {
            visibility = View.VISIBLE
        }
        return this
    }

    fun View.hide(): View {
        if (visibility != View.INVISIBLE) {
            visibility = View.INVISIBLE
        }
        return this
    }

    fun View.gone(): View {
        if (visibility != View.GONE) {
            visibility = View.GONE
        }
        return this
    }
}