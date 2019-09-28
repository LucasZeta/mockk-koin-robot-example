package com.thoughtworks.rockveganfood.ui.menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.thoughtworks.rockveganfood.R
import com.thoughtworks.rockveganfood.data.model.Dish
import com.thoughtworks.rockveganfood.ui.MenuItem

class MenuItemAdapter(
    private val onClickListener: (Dish, Int) -> Unit
) : RecyclerView.Adapter<MenuItemViewHolder>() {

    private var items = mutableListOf<MenuItem>()

    fun load(menuItems: Map<Dish, Int>) {
        val newItems = menuItems.toList()

        val result = DiffUtil.calculateDiff(MenuItemDiffCallback(newItems, items))
        result.dispatchUpdatesTo(this)

        items.clear()
        items.addAll(newItems)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.viewholder_menu_item, parent, false)

        return MenuItemViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: MenuItemViewHolder, position: Int) {
        holder.bind(items[position], onClickListener)
    }
}

class MenuItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val title by lazy { view.findViewById<TextView>(R.id.title) }
    private val description by lazy { view.findViewById<TextView>(R.id.description) }
    private val countView by lazy { view.findViewById<TextView>(R.id.amount) }
    private val plusButton by lazy { view.findViewById<ImageButton>(R.id.plus_button) }
    private val minusButton by lazy { view.findViewById<ImageButton>(R.id.minus_button) }

    fun bind(
        menuItem: MenuItem,
        onClickListener: (Dish, Int) -> Unit
    ) {
        val (dish, count) = menuItem

        title.text = dish.title.capitalize()
        description.text = dish.description
        countView.text = count.toString()

        setClickListeners(dish, onClickListener)
    }

    private fun setClickListeners(
        dish: Dish,
        onClickListener: (Dish, Int) -> Unit
    ) {
        plusButton.setOnClickListener {
            val currentCount = "${countView.text}".toInt() + 1

            countView.text = currentCount.toString()
            onClickListener(dish, currentCount)
        }

        minusButton.setOnClickListener {
            val currentCount = "${countView.text}".toInt() - 1

            if (currentCount >= 0) {
                countView.text = currentCount.toString()
                onClickListener(dish, currentCount)
            }
        }
    }
}
