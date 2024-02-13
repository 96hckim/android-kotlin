package com.hocheol.financial

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status

/**
 * 1. 문자 내용이 140바이트를 초과하면 안된다.
 * 2. 문자 맨 앞에 <#> 가 포함이 되어야 한다.
 * 3. 맨 마지막에 앱을 식별하는 11글자 해쉬코드가 존재해야함.
 */
class AuthOtpReceiver : BroadcastReceiver() {

    private var listener: OtpReceiveListener? = null

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null || intent == null) {
            return
        }

        if (intent.action == SmsRetriever.SMS_RETRIEVED_ACTION) {
            intent.extras?.let { bundle ->
                val status = bundle.get(SmsRetriever.EXTRA_STATUS) as Status
                when (status.statusCode) {
                    CommonStatusCodes.SUCCESS -> {
                        val otpSms = bundle.getString(SmsRetriever.EXTRA_SMS_MESSAGE, "")
                        listener?.let {
                            if (otpSms.isNotEmpty()) {
                                val otp = PATTERN.toRegex().find(otpSms)?.destructured?.component1()
                                if (!otp.isNullOrBlank()) {
                                    it.onOtpReceived(otp)
                                }
                            }
                        }
                    }

                    else -> Unit
                }
            }
        }
    }

    fun setOtpListener(otpReceiveListener: OtpReceiveListener) {
        this.listener = otpReceiveListener
    }

    fun doFilter() = IntentFilter().apply {
        addAction(SmsRetriever.SMS_RETRIEVED_ACTION)
    }

    interface OtpReceiveListener {
        fun onOtpReceived(otp: String)
    }

    companion object {
        private const val PATTERN = "^<#>.*\\[Sample].+\\[(\\d{6})].+\$"
    }
}