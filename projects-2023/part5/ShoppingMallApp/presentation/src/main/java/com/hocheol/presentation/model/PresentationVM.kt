package com.hocheol.presentation.model

import com.hocheol.domain.model.BaseModel

sealed class PresentationVM<T : BaseModel>(val model: T)