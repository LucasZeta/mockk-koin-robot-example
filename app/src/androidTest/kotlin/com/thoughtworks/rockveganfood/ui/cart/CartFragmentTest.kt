package com.thoughtworks.rockveganfood.ui.cart

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CartFragmentTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun whenMakingOrder_requestShouldContainItems() {
        setupCartFragment {
            mockSelectedItems()
            mockOrder()
        } launch {
            order()
        } check {
            orderRequestContainsItems()
        }
    }

    @Test
    fun whenUpdatingValues_userShouldGoToPreviousScreen() {
        setupCartFragment {
            mockSelectedItems()
        } launch {
            updateItems()
        } check {
            wentToPreviousScreen()
        }
    }
}