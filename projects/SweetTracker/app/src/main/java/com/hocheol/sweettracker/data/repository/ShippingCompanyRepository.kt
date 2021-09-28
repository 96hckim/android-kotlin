package com.hocheol.sweettracker.data.repository

import com.hocheol.sweettracker.data.entity.ShippingCompany

interface ShippingCompanyRepository {

    suspend fun getShippingCompanies(): List<ShippingCompany>

//    suspend fun getRecommendShippingCompany(invoice: String): ShippingCompany?

}
