package com.hocheol.todo.viewmodel.todo

import com.hocheol.todo.data.entity.ToDoEntity
import com.hocheol.todo.domain.todo.InsertToDoListUseCase
import com.hocheol.todo.presentation.list.ListViewModel
import com.hocheol.todo.viewmodel.ViewModelTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.koin.core.inject

/**
 * [ListViewModel] 을 테스트하기 위한 Unit Test Class
 *
 * 1. initData()
 * 2. test viewModel fetch
 * 3. test Item Update
 * 4. test Item Delete All
 */
@ExperimentalCoroutinesApi
internal class ListViewModelTest : ViewModelTest() {

    private val viewModel: ListViewModel by inject()

    private val insertToDoListUseCase: InsertToDoListUseCase by inject()

    private val mockList = (0 until 10).map {
        ToDoEntity(
            id = it.toLong(),
            title = "title $it",
            description = "description $it",
            hasCompleted = false
        )
    }

    /**
     * 필요한 UseCase
     * 1. InsertToDoListUseCase
     * 2. GetToDoItemUseCase
     */

    @Before
    fun init() {
        initData()
    }

    private fun initData() = runBlockingTest {
        insertToDoListUseCase(mockList)
    }

    // Test : 입력된 데이터를 불러와서 검증한다.
    @Test
    fun `test viewModel fetch`(): Unit = runBlockingTest {
        val testObservable = viewModel.toDoListLiveData.test()
        viewModel.fetchData()
        testObservable.assertValueSequence(
            listOf(
                mockList
            )
        )
    }

}