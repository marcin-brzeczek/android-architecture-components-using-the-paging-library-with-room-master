package com.chikeandroid.pagingtutsplus.adapter

import android.support.v7.util.DiffUtil
import com.chikeandroid.pagingtutsplus.data.TableItem

class ItemDiffCallback(private val oldItems: List<TableItem>, private val newItems: List<TableItem>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldItems.size
    }

    override fun getNewListSize(): Int {
        return newItems.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems.get(oldItemPosition).id == newItems.get(newItemPosition).id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems.get(oldItemPosition) == (newItems.get(newItemPosition))
    }
}

