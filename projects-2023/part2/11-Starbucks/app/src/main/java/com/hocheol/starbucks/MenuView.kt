package com.hocheol.starbucks

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.hocheol.starbucks.databinding.ItemMenuBinding

class MenuView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private lateinit var binding: ItemMenuBinding
    private var title: String? = null
    private var imageUrl: String? = null

    init {
        attrs?.let {
            initAttr(it)
        }
        initView()
    }

    private fun initAttr(attrs: AttributeSet) {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.MenuView,
            0, 0
        ).apply {
            title = getString(R.styleable.MenuView_title)
            imageUrl = getString(R.styleable.MenuView_imageUrl)
        }
    }

    private fun initView() {
        val view = LayoutInflater.from(context).inflate(R.layout.item_menu, this)
        binding = ItemMenuBinding.bind(view)

        title?.let {
            setTitle(it)
        }
        imageUrl?.let {
            setImageUrl(it)
        }
    }

    fun setTitle(title: String) {
        this.title = title
        binding.nameTextView.text = title
    }

    fun setImageUrl(imageUrl: String) {
        this.imageUrl = imageUrl
        Glide.with(binding.menuImageView)
            .load(imageUrl)
            .circleCrop()
            .into(binding.menuImageView)
    }
}