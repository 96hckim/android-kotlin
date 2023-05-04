package com.hocheol.webtoon

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import com.hocheol.webtoon.databinding.FragmentWebviewBinding

class WebViewFragment(
    private val position: Int,
    private val webViewUrl: String,
) : Fragment() {
    private lateinit var binding: FragmentWebviewBinding

    var listener: OnTabLayoutNameChanged? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentWebviewBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.webView.webViewClient = WebtoonWebViewClient(binding.progressBar) { url ->
            getSharedPreferences()?.edit {
                putString("tab${position}_url", url)
            }
        }
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.loadUrl(webViewUrl)

        binding.backToLastButton.setOnClickListener {
            val sharedPreferences = getSharedPreferences()
            val url = sharedPreferences?.getString("tab${position}_url", "")
            if (url.isNullOrEmpty()) {
                Toast.makeText(context, "마지막 저장 시점이 없습니다.", Toast.LENGTH_SHORT).show()
            } else {
                binding.webView.loadUrl(url)
            }
        }

        binding.changeTabNameButton.setOnClickListener {
            val context = context
            val dialog = AlertDialog.Builder(context)
            val editText = EditText(context)

            dialog.setView(editText)
            dialog.setPositiveButton("저장") { _, _ ->
                getSharedPreferences()?.edit {
                    val tabName = editText.text.toString()
                    putString("tab${position}_name", tabName)
                    listener?.nameChanged(position, tabName)
                }
            }
            dialog.setNegativeButton("취소") { dialog, _ ->
                dialog.cancel()
            }
            dialog.show()
        }
    }

    private fun getSharedPreferences(): SharedPreferences? {
        return activity?.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    fun canGoBack(): Boolean {
        return binding.webView.canGoBack()
    }

    fun goBack() {
        binding.webView.goBack()
    }

    companion object {
        const val SHARED_PREFERENCES_NAME = "WEB_HISTORY"
    }
}

interface OnTabLayoutNameChanged {
    fun nameChanged(position: Int, name: String)
}