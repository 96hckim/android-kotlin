package com.hocheol.weatherapp

import com.google.gson.annotations.SerializedName

enum class Category(
    val description: String,
    val unit: String,
    val compressionBits: Int
) {
    @SerializedName("POP")
    POP("강수확률", "%", 8),

    @SerializedName("PTY")
    PTY("강수형태", "코드값", 4),

    @SerializedName("PCP")
    PCP("1시간 강수량", "범주 (1 mm)", 8),

    @SerializedName("REH")
    REH("습도", "%", 8),

    @SerializedName("SNO")
    SNO("1시간 신적설", "범주 (1 cm)", 8),

    @SerializedName("SKY")
    SKY("하늘상태", "코드값", 4),

    @SerializedName("TMP")
    TMP("1시간 기온", "℃", 10),

    @SerializedName("TMN")
    TMN("일 최저기온", "℃", 10),

    @SerializedName("TMX")
    TMX("일 최고기온", "℃", 10),

    @SerializedName("UUU")
    UUU("풍속(동서성분)", "m/s", 12),

    @SerializedName("VVV")
    VVV("풍속(남북성분)", "m/s", 12),

    @SerializedName("WAV")
    WAV("파고", "M", 8),

    @SerializedName("VEC")
    VEC("풍향", "deg", 10),

    @SerializedName("WSD")
    WSD("풍속", "m/s", 10)
}