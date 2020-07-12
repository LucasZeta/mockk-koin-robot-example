package com.thoughtworks.rockveganfood.ui.menu

import androidx.fragment.app.testing.FragmentScenario
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasSibling
import androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.thoughtworks.rockveganfood.R
import com.thoughtworks.rockveganfood.data.model.Dish
import com.thoughtworks.rockveganfood.data.repository.OrderRepository
import com.thoughtworks.rockveganfood.ui.OrderViewModel
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import org.koin.core.KoinComponent
import org.koin.core.context.loadKoinModules
import org.koin.core.inject
import org.koin.dsl.module

fun setupMenuFragment(block: MenuFragmentSetupRobot.() -> Unit) =
    MenuFragmentSetupRobot().apply(block)

class MenuFragmentSetupRobot : KoinComponent {

    private val orderRepository: OrderRepository = mockk()
    private val viewModel = OrderViewModel(orderRepository)
    private val navController: NavController = mockk(relaxed = true)

    init {
        loadKoinModules(module(override = true) {
            single { orderRepository }
            single { navController }
            single { viewModel }
        })
    }

    fun mockMenuWithFewItems() {
        coEvery { orderRepository.fetchMenu() } returns stubFewMenuItems()
    }

    fun mockMenuWithSeveralItems() {
        coEvery { orderRepository.fetchMenu() } returns stubSeveralMenuItems()
    }

    infix fun launch(block: MenuFragmentActionRobot.() -> Unit): MenuFragmentActionRobot {
        FragmentScenario.launchInContainer(
            MenuFragment::class.java,
            null,
            R.style.AppTheme,
            null
        ).onFragment {
            Navigation.setViewNavController(it.requireView(), navController)
        }

        return MenuFragmentActionRobot().apply(block)
    }
}

class MenuFragmentActionRobot {

    infix fun check(block: MenuFragmentResultRobot.() -> Unit) =
        MenuFragmentResultRobot().apply(block)

    fun addItem(dishTitle: String) {
        onView(allOf(
            withId(R.id.plus_button),
            hasSibling(withText(dishTitle))
        )).perform(click())
    }

    fun removeItem(dishTitle: String) {
        onView(allOf(
            withId(R.id.minus_button),
            hasSibling(withText(dishTitle))
        )).perform(click())
    }

    fun addToCart() {
        onView(withId(R.id.add_to_cart)).perform(click())
    }
}

class MenuFragmentResultRobot : KoinComponent {

    private val navController: NavController by inject()

    fun userCannotGoToCart() {
        onView(withId(R.id.add_to_cart)).check(matches(not(isEnabled())))
    }

    fun userCanGoToCart() {
        onView(withId(R.id.add_to_cart)).check(matches(isEnabled()))
    }

    fun wentToCartScreen() {
        verify { navController.navigate(MenuFragmentDirections.addToCart()) }
    }

    fun userCanSeeAddButton() {
        onView(withId(R.id.add_to_cart)).check(matches(isCompletelyDisplayed()))
    }
}

private fun stubFewMenuItems() = listOf(
    Dish("tradicional", "catupop, tomate em rodelas, molho de tomate, orégano e azeitonas (contém gluten)."),
    Dish("calabresa", "linguiça vegetal, catupop, cebola caramelizada, molho de tomate, orégano e azeitonas (contém gluten)."),
    Dish("lombo", "presunto vegetal,catupop, cebola caramelizada, molho de tomate, orégano e azeitonas (contém gluten).")
)

private fun stubSeveralMenuItems() = listOf(
    Dish("tradicional", "catupop, tomate em rodelas, molho de tomate, orégano e azeitonas (contém gluten)."),
    Dish("rúcula com chèvre", "molho de tomate, chèvre nomoo®*, rúcula fresca*, tomate seco, azeitona preta e orégano (pode conter traços de glúten). *ingredientes que não vão ao forno, por isso a pizza ficará naturalmente fria/menos quente."),
    Dish("quatro queijos", "tomate em rodelas, queijo mussarela de castanha, catupop, vegapop, parmesão de amendoim, molho de tomate, orégano e azeitonas (contém gluten)."),
    Dish("portuguesa", "presunto vegetal, ervilha, cebola caramelizada, vegapop, molho de tomate, orégano e azeitonas (contém gluten)."),
    Dish("pepperoni", "temperoni, catupop, molho de tomate, orégano e azeitonas (contém gluten)."),
    Dish("mix de cogumelos", "shimeji, paris, catupop, pimenta biquinho, azeitona preta, cebolinha, gergelim, molho de tomate, orégano e azeitonas (contém gluten)."),
    Dish("marguerita com queijo de castanhas", "mussarela de castanha, tomate em rodelas, manjericão, molho de tomate, orégano e azeitonas (pode conter glúten)."),
    Dish("lombo", "presunto vegetal,catupop, cebola caramelizada, molho de tomate, orégano e azeitonas (contém gluten)."),
    Dish("jardineira", "catupop, brócolis, palmito, rodelas de tomate, alho frito, molho de tomate, orégano e azeitonas (contém gluten)."),
    Dish("jaca crazy", "jaca (jaca verde, cebola, tomate, molho de tomate, pimentão, gordura de palma, especiarias e sal)., molho de tomate, milho, ervilha, catupop,orégano e azeitonas (contém gluten)."),
    Dish("frango com catupiry", "frango, catupop, milho, molho de tomate, orégano e azeitonas (contém gluten)."),
    Dish("di napoli", "catupop, abobrinha fresca fatiada, tomate seco, alho frito, lemon pepper, molho de tomate, orégano e azeitonas (contém gluten)."),
    Dish("diferentona", "linguiça vegetal, catupop, ervilha, milho, palmito, molho de tomate, orégano e azeitonas (contém gluten)."),
    Dish("caponata", "berinjela , alho, pimentões, catupop, uva passa preta, manjericão, cebolinha,nozes, melado, vinagre de maçã, molho de tomate, orégano e azeitonas (contém gluten)."),
    Dish("calabresa", "linguiça vegetal, catupop, cebola caramelizada, molho de tomate, orégano e azeitonas (contém gluten).")
)