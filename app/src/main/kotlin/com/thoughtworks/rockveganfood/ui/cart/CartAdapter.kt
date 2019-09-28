package com.thoughtworks.rockveganfood.ui.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.thoughtworks.rockveganfood.R
import com.thoughtworks.rockveganfood.ui.MenuItem

class CartAdapter : RecyclerView.Adapter<CartViewHolder>() {

    private var items = listOf<MenuItem>()

    fun load(menuItems: List<MenuItem>) {
        items = menuItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.viewholder_cart, parent, false)

        return CartViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(items[position])
    }
}

class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val title by lazy { view.findViewById<TextView>(R.id.title) }

    fun bind(menuItem: MenuItem) {
        val (dish, count) = menuItem

        title.text = itemView.context.getString(R.string.cart_item_title, count, dish.title)
    }
}
