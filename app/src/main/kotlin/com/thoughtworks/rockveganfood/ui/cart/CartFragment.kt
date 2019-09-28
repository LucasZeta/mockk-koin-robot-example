package com.thoughtworks.rockveganfood.ui.cart

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.thoughtworks.rockveganfood.R
import com.thoughtworks.rockveganfood.ui.BaseFragment
import com.thoughtworks.rockveganfood.ui.OrderViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CartFragment : BaseFragment(R.layout.fragment_cart) {

    private val viewModel: OrderViewModel by sharedViewModel()

    private val makeOrderButton by lazy { requireView().findViewById<Button>(R.id.make_order) }
    private val updateOrderButton by lazy { requireView().findViewById<Button>(R.id.update_order) }
    private val selectedDishes by lazy {
        requireView().findViewById<RecyclerView>(R.id.selected_dishes).apply {
            adapter = CartAdapter()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setClickListeners()
        updateUi()
        observeViewState()
    }

    private fun observeViewState() {
        viewModel.message.observe(viewLifecycleOwner, Observer { it?.let { messageRes ->
            Toast.makeText(requireContext(), getString(messageRes), Toast.LENGTH_LONG).show()
            viewModel.invalidateMessage()
        }})
    }

    private fun setClickListeners() {
        updateOrderButton.setOnClickListener {
            findNavController().popBackStack()
        }

        makeOrderButton.setOnClickListener {
            viewModel.makeOrder()
        }
    }

    private fun updateUi() {
        (selectedDishes.adapter as CartAdapter).load(viewModel.fetchOrder())
    }
}
