package com.hocheol.presentation.ui.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.hocheol.domain.model.SearchFilter
import com.hocheol.presentation.ui.component.ProductCard
import com.hocheol.presentation.viewmodel.search.SearchViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navHostController: NavHostController,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val searchFilters by viewModel.searchFilters.collectAsState()
    val sheetState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            skipHiddenState = false
        )
    )
    val scope = rememberCoroutineScope()
    var currentFilterType by remember { mutableStateOf<SearchFilter.Type?>(null) }

    BottomSheetScaffold(
        sheetContent = {
            when (currentFilterType) {
                SearchFilter.Type.CATEGORY -> {
                    val categoryFilter = searchFilters.first { it is SearchFilter.CategoryFilter } as SearchFilter.CategoryFilter
                    SearchFilterCategoryContent(filter = categoryFilter) {
                        scope.launch {
                            currentFilterType = null
                            sheetState.bottomSheetState.hide()
                        }
                        viewModel.updateFilter(it)
                    }
                }

                SearchFilter.Type.PRICE -> {
                    val priceFilter = searchFilters.first { it is SearchFilter.PriceFilter } as SearchFilter.PriceFilter
                    SearchFilterPriceContent(filter = priceFilter) {
                        scope.launch {
                            currentFilterType = null
                            sheetState.bottomSheetState.hide()
                        }
                        viewModel.updateFilter(it)
                    }
                }

                null -> Unit
            }
        },
        scaffoldState = sheetState,
        sheetPeekHeight = 0.dp,
        sheetShape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)
    ) {
        SearchContent(
            viewModel = viewModel,
            navHostController = navHostController
        ) {
            scope.launch {
                currentFilterType = it
                sheetState.bottomSheetState.expand()
            }
        }
    }
}

@Composable
fun SearchFilterCategoryContent(
    filter: SearchFilter.CategoryFilter,
    onCompleteFilter: (SearchFilter) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        Text(
            text = "카테고리 필터",
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            modifier = Modifier.padding(10.dp)
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(10.dp)
        ) {
            items(filter.categories.size) { index ->
                val category = filter.categories[index]

                Button(
                    onClick = {
                        filter.selectedCategory = category
                        onCompleteFilter(filter)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (filter.selectedCategory == category) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.secondary
                        }
                    )
                ) {
                    Text(
                        fontSize = 18.sp,
                        text = category.categoryName
                    )
                }
            }
        }
    }
}

@Composable
fun SearchFilterPriceContent(
    filter: SearchFilter.PriceFilter,
    onCompleteFilter: (SearchFilter) -> Unit
) {
    var sliderValues by remember {
        val selectedRange = filter.selectedRange
        if (selectedRange == null) {
            mutableStateOf(filter.priceRange.first..filter.priceRange.second)
        } else {
            mutableStateOf(selectedRange.first..selectedRange.second)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "가격 필터",
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                modifier = Modifier.padding(10.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    filter.selectedRange = sliderValues.start to sliderValues.endInclusive
                    onCompleteFilter(filter)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    fontSize = 18.sp,
                    text = "완료"
                )
            }
        }

        RangeSlider(
            value = sliderValues,
            onValueChange = {
                sliderValues = it
            },
            valueRange = filter.priceRange.first..filter.priceRange.second,
            steps = 9
        )

        Text(text = "최저가: ${sliderValues.start} ~ 최고가: ${sliderValues.endInclusive}")
    }
}

@Composable
fun SearchContent(
    viewModel: SearchViewModel,
    navHostController: NavHostController,
    openFilterDialog: (SearchFilter.Type) -> Unit
) {
    val searchFilters by viewModel.searchFilters.collectAsState()
    val searchResult by viewModel.searchResult.collectAsState()
    val searchKeywords by viewModel.searchKeywords.collectAsState(initial = listOf())
    var keyword by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column {
        SearchBox(
            keyword = keyword,
            onValueChange = { keyword = it },
            searchAction = {
                viewModel.search(keyword)
                keyboardController?.hide()
            }
        )

        if (searchResult.isEmpty()) {
            Text(
                text = "최근 검색어",
                modifier = Modifier.padding(6.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(10.dp)
            ) {
                val reversedSearchKeywords = searchKeywords.reversed()

                items(searchKeywords.size) { index ->
                    val currentKeyword = reversedSearchKeywords[index].keyword
                    Button(
                        onClick = {
                            keyword = currentKeyword
                            viewModel.search(keyword)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Unspecified
                        )
                    ) {
                        Text(
                            text = currentKeyword,
                            fontSize = 18.sp
                        )
                    }
                }
            }
        } else {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Button(
                    onClick = {
                        openFilterDialog(SearchFilter.Type.CATEGORY)
                    }
                ) {
                    val filter = searchFilters.find { it.type == SearchFilter.Type.CATEGORY } as? SearchFilter.CategoryFilter
                    val selectedCategory = filter?.selectedCategory

                    if (selectedCategory == null) {
                        Text("Category")
                    } else {
                        Text(selectedCategory.categoryName)
                    }
                }

                Spacer(modifier = Modifier.width(20.dp))

                Button(
                    onClick = {
                        openFilterDialog(SearchFilter.Type.PRICE)
                    }
                ) {
                    val filter = searchFilters.find { it.type == SearchFilter.Type.PRICE } as? SearchFilter.PriceFilter
                    val selectedRange = filter?.selectedRange

                    if (selectedRange == null) {
                        Text("Price")
                    } else {
                        Text("${selectedRange.first} ~ ${selectedRange.second}")
                    }
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(10.dp)
            ) {
                items(searchResult.size) { index ->
                    ProductCard(
                        navHostController = navHostController,
                        presentationVM = searchResult[index]
                    )
                }
            }
        }
    }
}

@Composable
fun SearchBox(
    keyword: String,
    onValueChange: (String) -> Unit,
    searchAction: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        TextField(
            value = keyword,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            placeholder = { Text(text = "검색어를 입력해주세요.") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "SearchIcon"
                )
            },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                autoCorrect = true,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = { searchAction() }
            ),
            singleLine = true,
            maxLines = 1,
            shape = RoundedCornerShape(size = 8.dp)
        )
    }
}