package com.thoughtworks.rockveganfood.ui.menu

import androidx.recyclerview.widget.DiffUtil
import com.thoughtworks.rockveganfood.ui.MenuItem

class MenuItemDiffCallback(
    private val newList: List<MenuItem>,
    private val oldList: List<MenuItem>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) = true

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        val sameCount = oldItem.second == newItem.second
        val sameDish = oldItem.first.uid == newItem.first.uid &&
            oldItem.first.title == newItem.first.title

        return sameCount && sameDish
    }
}
