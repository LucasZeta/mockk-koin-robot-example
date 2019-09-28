package com.thoughtworks.rockveganfood.ui.menu

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MenuFragmentTest {

    @Test
    fun atStart_userCannotGoToCart() {
        setupMenuFragment {
            mockMenuWithFewItems()
        } launch {
        } check {
            userCannotGoToCart()
        }
    }

    @Test
    fun whenAddingOneDish_userCanGoToCart() {
        setupMenuFragment {
            mockMenuWithFewItems()
        } launch {
            addItem("Calabresa")
        } check {
            userCanGoToCart()
        }
    }

    @Test
    fun whenRemovingTheOnlySelectedDish_userCannotGoToCart() {
        setupMenuFragment {
            mockMenuWithFewItems()
        } launch {
            addItem("Tradicional")
            removeItem("Tradicional")
        } check {
            userCannotGoToCart()
        }
    }

    @Test
    fun whenRemovingDish_havingAnotherSelected_userCanGoToCart() {
        setupMenuFragment {
            mockMenuWithFewItems()
        } launch {
            addItem("Calabresa")
            addItem("Lombo")
            removeItem("Calabresa")
        } check {
            userCanGoToCart()
        }
    }

    @Test
    fun whenAddToCart_userShouldBeSentToCartScreen() {
        setupMenuFragment {
            mockMenuWithFewItems()
        } launch {
            addItem("Lombo")
            addToCart()
        } check {
            wentToCartScreen()
        }
    }

    @Test
    fun whenMenuListIsExtensive_userShouldStillBeAbleToSeeCartButton() {
        setupMenuFragment {
            mockMenuWithSeveralItems()
        } launch {
        } check {
            userCanSeeAddButton()
        }
    }
}