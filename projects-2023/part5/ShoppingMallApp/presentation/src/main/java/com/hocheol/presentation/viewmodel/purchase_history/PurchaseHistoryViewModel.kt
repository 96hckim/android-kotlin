package com.hocheol.presentation.viewmodel.purchase_history

import androidx.lifecycle.ViewModel
import com.hocheol.domain.usecase.PurchaseHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PurchaseHistoryViewModel @Inject constructor(
    purchaseHistoryUseCase: PurchaseHistoryUseCase
) : ViewModel() {

    val purchaseHistory = purchaseHistoryUseCase.getPurchaseHistory()
}