package com.hocheol.imageextraction.mvvm.bindingadapter

import android.graphics.Color
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import com.hocheol.imageextraction.mvvm.model.Image

@BindingAdapter("image")
fun ImageView.setImage(image: Image?) {
    image ?: return

    setBackgroundColor(Color.parseColor(image.color))

    load(image.url) {
        crossfade(300)
    }
}