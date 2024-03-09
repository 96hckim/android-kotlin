package com.hocheol.data.repository

import com.google.common.truth.Truth.assertThat
import com.hocheol.data.db.ApplicationDatabase
import com.hocheol.data.db.dao.BasketDao
import com.hocheol.data.db.dao.PurchaseHistoryDao
import com.hocheol.data.db.entity.BasketProductEntity
import com.hocheol.domain.model.BasketProduct
import com.hocheol.domain.model.Category
import com.hocheol.domain.model.Price
import com.hocheol.domain.model.Product
import com.hocheol.domain.model.SalesStatus
import com.hocheol.domain.model.Shop
import com.hocheol.domain.repository.BasketRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import javax.inject.Inject

@Suppress("NonAsciiCharacters")
@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
@Config(application = HiltTestApplication::class)
@RunWith(RobolectricTestRunner::class)
class BasketRepositoryTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var database: ApplicationDatabase

    private lateinit var basketDao: BasketDao
    private lateinit var purchaseHistoryDao: PurchaseHistoryDao
    private lateinit var basketRepository: BasketRepository

    private val price = Price(
        originPrice = 10000,
        finalPrice = 10000,
        salesStatus = SalesStatus.ON_SALE
    )
    private val shop = Shop(
        shopId = "0",
        shopName = "0",
        imageUrl = ""
    )
    private val category = Category.Top

    private val basketProductEntity = BasketProductEntity(
        productId = "",
        productName = "",
        imageUrl = "",
        price = price,
        category = category,
        shop = shop,
        isNew = false,
        isFreeShipping = false,
        isLike = false,
        count = 1
    )

    private val basketProduct = BasketProduct(
        product = Product(
            productId = "",
            productName = "",
            imageUrl = "",
            price = price,
            category = category,
            shop = shop,
            isNew = false,
            isFreeShipping = false,
            isLike = false
        ),
        count = 1
    )

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        hiltRule.inject()

        basketDao = database.basketDao()
        purchaseHistoryDao = database.purchaseHistoryDao()
        basketRepository = BasketRepositoryImpl(basketDao, purchaseHistoryDao)
    }

    @After
    fun close() {
        Dispatchers.resetMain()
        database.close()
    }

    @Test
    fun `결제 테스트`() = runTest {
        basketDao.insert(basketProductEntity)

        basketRepository.checkoutBasket(listOf(basketProduct))

        assertThat(purchaseHistoryDao.get("1")).isNotNull()
        assertThat(basketDao.get(basketProductEntity.productId)).isNull()
    }
}