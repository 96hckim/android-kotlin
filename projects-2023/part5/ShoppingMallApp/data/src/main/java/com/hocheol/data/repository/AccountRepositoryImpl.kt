package com.hocheol.data.repository

import com.hocheol.data.datasource.PreferenceDatasource
import com.hocheol.domain.model.AccountInfo
import com.hocheol.domain.repository.AccountRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val preferenceDatasource: PreferenceDatasource
) : AccountRepository {

    private val accountInfoFlow = MutableStateFlow(preferenceDatasource.getAccountInfo())

    override fun getAccountInfo(): StateFlow<AccountInfo?> {
        return accountInfoFlow
    }

    override suspend fun signInGoogle(accountInfo: AccountInfo) {
        preferenceDatasource.putAccountInfo(accountInfo)
        accountInfoFlow.emit(accountInfo)
    }

    override suspend fun signOutGoogle() {
        preferenceDatasource.removeAccountInfo()
        accountInfoFlow.emit(null)
    }
}