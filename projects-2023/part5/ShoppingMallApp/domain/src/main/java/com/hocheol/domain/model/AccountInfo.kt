package com.hocheol.domain.model

data class AccountInfo(
    val tokenId: String,
    val name: String,
    val type: Type,
    val imageUrl: String
) {
    enum class Type {
        GOOGLE, KAKAO
    }
}
