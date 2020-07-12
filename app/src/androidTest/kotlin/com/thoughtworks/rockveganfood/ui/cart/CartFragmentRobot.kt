package com.thoughtworks.rockveganfood.ui.cart

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.google.common.truth.Truth.assertThat
import com.jraska.livedata.test
import com.thoughtworks.rockveganfood.R
import com.thoughtworks.rockveganfood.data.model.Dish
import com.thoughtworks.rockveganfood.data.repository.OrderRepository
import com.thoughtworks.rockveganfood.ui.OrderViewModel
import io.mockk.CapturingSlot
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.koin.core.KoinComponent
import org.koin.core.context.loadKoinModules
import org.koin.core.inject
import org.koin.dsl.module

fun setupCartFragment(block: CartFragmentSetupRobot.() -> Unit) =
    CartFragmentSetupRobot().apply(block)

class CartFragmentSetupRobot : KoinComponent {

    private val orderRepository: OrderRepository = mockk()
    private val navController: NavController = mockk(relaxed = true)
    private val slotOrder = slot<Map<Dish, Int>>()
    private val viewModel = OrderViewModel(orderRepository)

    init {
        loadKoinModules(
            module(override = true) {
                single { orderRepository }
                single { slotOrder }
                single { viewModel }
                single { navController }
            }
        )
    }

    fun mockSelectedItems() {
        val menuItems = listOf(
            stubDish("Pizza #1"),
            stubDish("Pizza #2"),
            stubDish("Pizza #3")
        )

        coEvery { orderRepository.fetchMenu() } returns menuItems

        viewModel.menu.test().awaitValue()

        menuItems.forEachIndexed { index, dish ->
            viewModel.updateOrder(dish, 3 - index)
        }
    }

    fun mockOrder() {
        coEvery { orderRepository.makeOrder(capture(slotOrder), any()) } just Runs
    }

    infix fun launch(block: CartFragmentActionRobot.() -> Unit): CartFragmentActionRobot {
        launchFragmentInContainer<CartFragment>(
            themeResId = R.style.AppTheme
        ).onFragment {
            Navigation.setViewNavController(it.requireView(), navController)
        }

        return CartFragmentActionRobot().apply(block)
    }
}

class CartFragmentActionRobot {

    infix fun check(block: CartFragmentResultRobot.() -> Unit) =
        CartFragmentResultRobot().apply(block)

    fun order() {
        onView(withId(R.id.make_order)).perform(click())
    }

    fun updateItems() {
        onView(withId(R.id.update_order)).perform(click())
    }
}

class CartFragmentResultRobot : KoinComponent {

    private val slotOrder: CapturingSlot<Map<Dish, Int>> by inject()
    private val orderRepository: OrderRepository by inject()
    private val navController: NavController by inject()

    fun orderRequestContainsItems() {
        coVerify(timeout = 2000) { orderRepository.makeOrder(slotOrder.captured, any()) }

        assertThat(slotOrder.captured).containsExactly(
            stubDish("Pizza #1"), 3,
            stubDish("Pizza #2"), 2,
            stubDish("Pizza #3"), 1
        )
    }

    fun wentToPreviousScreen() {
        verify { navController.popBackStack() }
    }
}

private fun stubDish(title: String, description: String = "") =
    Dish(title, description)
