package com.meuus90.zzim.view.adapter

import androidx.recyclerview.widget.DiffUtil
import com.meuus90.zzim.model.data.response.Goods


class GoodsDiffCallback(
    private val oldGoodsList: List<Goods>,
    private val newGoodsList: List<Goods>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldGoodsList.size
    }

    override fun getNewListSize(): Int {
        return newGoodsList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldGoodsList[oldItemPosition].id == newGoodsList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEmployee: Goods = oldGoodsList[oldItemPosition]
        val newEmployee: Goods = newGoodsList[newItemPosition]
        return oldEmployee.name.equals(newEmployee.name)
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        // Implement method if you're going to use ItemAnimator
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}