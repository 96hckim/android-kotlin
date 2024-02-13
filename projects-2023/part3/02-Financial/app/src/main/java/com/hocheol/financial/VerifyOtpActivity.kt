package com.hocheol.financial

import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.hocheol.financial.databinding.ActivityVerifyOtpBinding
import com.hocheol.financial.util.ViewUtil.setOnEditorActionListener
import com.hocheol.financial.util.ViewUtil.showKeyboardDelay

class VerifyOtpActivity : AppCompatActivity(), AuthOtpReceiver.OtpReceiveListener {

    private lateinit var binding: ActivityVerifyOtpBinding

    private var smsReceiver: AuthOtpReceiver? = null

    private var timer: CountDownTimer? = object : CountDownTimer((3 * 60 * 1000), 1000L) {
        override fun onTick(millisUntilFinished: Long) {
            val min = (millisUntilFinished / 1000) / 60
            val sec = (millisUntilFinished / 1000) % 60
            binding.timerTextView.text = String.format("%02d:%02d", min, sec)
        }

        override fun onFinish() {
            binding.timerTextView.text = ""
            Toast.makeText(this@VerifyOtpActivity, "입력 제한 시간을 초과하였습니다.\n다시 시도해주세요.", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        binding.otpCodeEdit.showKeyboardDelay()
        startSmsReceiver()
    }

    override fun onDestroy() {
        clearTimer()
        stopSmsReceiver()
        super.onDestroy()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerifyOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.view = this
        initView()
    }

    private fun initView() {
        startTimer()
        with(binding) {
            otpCodeEdit.doAfterTextChanged {
                if (otpCodeEdit.length() >= 6) {
                    stopTimer()
                    Toast.makeText(this@VerifyOtpActivity, "인증번호 입력이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            otpCodeEdit.setOnEditorActionListener(EditorInfo.IME_ACTION_DONE) {

            }
        }
    }

    private fun startTimer() {
        timer?.start()
    }

    private fun stopTimer() {
        timer?.cancel()
    }

    private fun clearTimer() {
        stopTimer()
        timer = null
    }

    override fun onOtpReceived(otp: String) {
        binding.otpCodeEdit.setText(otp)
    }

    private fun startSmsReceiver() {
        SmsRetriever.getClient(this).startSmsRetriever().also { task ->
            task.addOnSuccessListener {
                val receiver = smsReceiver ?: AuthOtpReceiver().apply {
                    setOtpListener(this@VerifyOtpActivity)
                }.also {
                    smsReceiver = it
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    registerReceiver(receiver, receiver.doFilter(), RECEIVER_EXPORTED)
                } else {
                    registerReceiver(receiver, receiver.doFilter())
                }
            }

            task.addOnFailureListener {
                stopSmsReceiver()
            }
        }
    }

    private fun stopSmsReceiver() {
        if (smsReceiver != null) {
            unregisterReceiver(smsReceiver)
            smsReceiver = null
        }
    }
}