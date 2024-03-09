package com.hocheol.presentation.viewmodel.basket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hocheol.domain.model.BasketProduct
import com.hocheol.domain.model.Product
import com.hocheol.domain.usecase.BasketUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BasketViewModel @Inject constructor(
    private val basketUseCase: BasketUseCase
) : ViewModel() {

    val basketProducts = basketUseCase.getBasketProducts()

    private val _eventFlow = MutableSharedFlow<BasketEvent>()
    val eventFlow: SharedFlow<BasketEvent> = _eventFlow

    fun dispatch(action: BasketAction) {
        when (action) {
            is BasketAction.RemoveProduct -> removeBasketProduct(action.product)
            is BasketAction.CheckoutBasket -> checkoutBasket(action.products)
        }
    }

    private fun removeBasketProduct(product: Product) {
        viewModelScope.launch {
            basketUseCase.removeBasketProducts(product)
        }
    }

    private fun checkoutBasket(products: List<BasketProduct>) {
        viewModelScope.launch {
            basketUseCase.checkoutBasket(products)
            _eventFlow.emit(BasketEvent.ShowSnackbar)
        }
    }
}

sealed class BasketEvent {

    data object ShowSnackbar : BasketEvent()
}

sealed class BasketAction {

    data class RemoveProduct(
        val product: Product
    ) : BasketAction()

    data class CheckoutBasket(
        val products: List<BasketProduct>
    ) : BasketAction()
}