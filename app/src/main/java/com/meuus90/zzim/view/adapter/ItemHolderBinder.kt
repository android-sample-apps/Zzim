package com.meuus90.zzim.view.adapter

interface ItemHolderBinder<T> {
    fun bindItemHolder(holder: BaseViewHolder<T>, item: T, position: Int)
    fun onItemSelected()
    fun onItemClear()
}