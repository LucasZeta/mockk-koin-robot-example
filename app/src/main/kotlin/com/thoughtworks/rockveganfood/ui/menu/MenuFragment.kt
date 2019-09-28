package com.thoughtworks.rockveganfood.ui.menu

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.thoughtworks.rockveganfood.R
import com.thoughtworks.rockveganfood.ui.ext.addVerticalDivider
import com.thoughtworks.rockveganfood.ui.BaseFragment
import com.thoughtworks.rockveganfood.ui.OrderViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MenuFragment : BaseFragment(R.layout.fragment_menu) {

    private val viewModel: OrderViewModel by sharedViewModel()

    private val addToCartButton by lazy { requireView().findViewById<Button>(R.id.add_to_cart) }
    private val menuItems by lazy {
        requireView().findViewById<RecyclerView>(R.id.menu_items).apply {
            adapter = MenuItemAdapter(viewModel::updateOrder)
            addVerticalDivider()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setClickListeners()
        observeViewState()
    }

    private fun observeViewState() {
        viewModel.menu.observe(
            viewLifecycleOwner,
            Observer((menuItems.adapter as MenuItemAdapter)::load)
        )

        viewModel.isOrderValid.observe(
            viewLifecycleOwner,
            Observer(addToCartButton::setEnabled)
        )
    }

    private fun setClickListeners() {
        addToCartButton.setOnClickListener {
            findNavController().navigate(MenuFragmentDirections.addToCart())
        }
    }
}