package com.hocheol.domain.usecase

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.hocheol.domain.model.Category
import com.hocheol.domain.model.Product
import com.hocheol.domain.model.SearchFilter
import com.hocheol.domain.model.SearchKeyword
import com.hocheol.domain.repository.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@Suppress("NonAsciiCharacters")
@OptIn(ExperimentalCoroutinesApi::class)
class SearchUseCaseTest {

    private lateinit var searchUseCase: SearchUseCase

    @Mock
    private lateinit var searchRepository: SearchRepository

    @Mock
    private lateinit var topProduct: Product

    @Mock
    private lateinit var dressProduct: Product

    @Mock
    private lateinit var pantsProduct: Product

    private lateinit var searchResponse: List<Product>

    private lateinit var autoCloseable: AutoCloseable

    private val searchKeyword = SearchKeyword("1")

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        autoCloseable = MockitoAnnotations.openMocks(this)

        `when`(topProduct.category).thenReturn(Category.Top)
        `when`(dressProduct.category).thenReturn(Category.Dress)
        `when`(pantsProduct.category).thenReturn(Category.Pants)

        `when`(topProduct.productName).thenReturn("상의1")
        `when`(dressProduct.productName).thenReturn("드레스1")
        `when`(pantsProduct.productName).thenReturn("바지1")

        searchResponse = listOf(topProduct, dressProduct, pantsProduct)

        searchUseCase = SearchUseCase(searchRepository)
    }

    @After
    fun close() {
        Dispatchers.resetMain()
        autoCloseable.close()
    }

    @Test
    fun `검색 호출 테스트`() {
        searchUseCase.getSearchKeywords()

        verify(searchRepository).getSearchKeywords()
    }

    @Test
    fun `상의 필터 검색 테스트`() = runTest {
        `when`(searchRepository.search(searchKeyword)).thenReturn(flow { emit(searchResponse) })

        searchUseCase.search(
            searchKeyword = searchKeyword,
            filters = listOf(SearchFilter.CategoryFilter(listOf(), Category.Top))
        ).test {
            assertThat(awaitItem()).isEqualTo(listOf(topProduct))
            awaitComplete()
        }
    }

    @Test
    fun `드레스 필터 검색 테스트`() = runTest {
        `when`(searchRepository.search(searchKeyword)).thenReturn(flow { emit(searchResponse) })

        searchUseCase.search(
            searchKeyword = searchKeyword,
            filters = listOf(SearchFilter.CategoryFilter(listOf(), Category.Dress))
        ).test {
            assertThat(awaitItem()).isEqualTo(listOf(dressProduct))
            awaitComplete()
        }
    }

    @Test
    fun `검색어 테스트`() = runTest {
        val dressSearchKeyword = SearchKeyword("상의")

        `when`(searchRepository.search(dressSearchKeyword)).thenReturn(flow { emit(searchResponse) })

        searchUseCase.search(
            searchKeyword = dressSearchKeyword,
            filters = listOf()
        ).test {
            assertThat(awaitItem()).isEqualTo(listOf(topProduct))
            awaitComplete()
        }
    }
}