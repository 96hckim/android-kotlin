package com.hocheol.presentation.model

import com.hocheol.domain.model.Product
import com.hocheol.presentation.delegate.ProductDelegate

class ProductVM(
    model: Product,
    productDelegate: ProductDelegate
) : PresentationVM<Product>(model), ProductDelegate by productDelegate