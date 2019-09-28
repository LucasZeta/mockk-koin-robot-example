package com.thoughtworks.rockveganfood.ui

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thoughtworks.rockveganfood.R
import com.thoughtworks.rockveganfood.data.model.Dish
import com.thoughtworks.rockveganfood.data.repository.OrderRepository
import com.thoughtworks.rockveganfood.ui.ext.PATTERN_LOCAL_DATE_TIME
import com.thoughtworks.rockveganfood.ui.ext.PT_BR_LOCALE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

typealias MenuItem = Pair<Dish, Int>

class OrderViewModel(private val repository: OrderRepository) : ViewModel() {

    private val _menu = MutableLiveData<MutableMap<Dish, Int>>()
    private val _message = MutableLiveData<@StringRes Int>()
    private val _isOrderValid = MutableLiveData<Boolean>()

    val menu: LiveData<MutableMap<Dish, Int>>
        get() {
            if (_menu.value == null) {
                fetchMenu()
            }

            return _menu
        }

    val isOrderValid: LiveData<Boolean>
        get() = _isOrderValid

    val message: LiveData<Int>
        get() = _message

    fun updateOrder(dish: Dish, count: Int) {
        _menu.value?.let { order ->
            if (order.containsKey(dish)) {
                order[dish] = count

                val selectedItemsCount = order.count { it.value > 0 }
                _isOrderValid.postValue(selectedItemsCount > 0)

                _menu.postValue(order)
            }
        }
    }

    fun fetchOrder(): List<MenuItem>{
        return extractSelectedItems().toList()
    }

    fun makeOrder() {
        _menu.value?.let {
            viewModelScope.launch {
                withContext(Dispatchers.Default) {
                    val now = LocalDateTime.now().format(
                        DateTimeFormatter.ofPattern(PATTERN_LOCAL_DATE_TIME, PT_BR_LOCALE)
                    )

                    repository.makeOrder(extractSelectedItems(), now)
                }

                _message.postValue(R.string.order_success_message)
            }
        }
    }

    private fun fetchMenu() {
        viewModelScope.launch {
            val menuItems = withContext(Dispatchers.Default) {
                repository.fetchMenu()
            }

            _menu.postValue(
                mutableMapOf<Dish, Int>().apply {
                    menuItems.map { this.put(it, 0) }
                }
            )
        }
    }

    private fun extractSelectedItems(): Map<Dish, Int> {
        return _menu.value?.filter { it.value > 0 } ?: mapOf()
    }

    fun invalidateMessage() {
        _message.value = null
    }
}
