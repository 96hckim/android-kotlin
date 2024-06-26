package com.hocheol.presentation.main.writing.toolbar

import androidx.annotation.DrawableRes
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import com.hocheol.presentation.R
import com.mohamedrejeb.richeditor.model.RichTextState

enum class WritingToolbarType(
    @DrawableRes val resId: Int
) {
    BOLD(R.drawable.bold),
    ITALIC(R.drawable.italic),
    UNDERLINE(R.drawable.underline),
    STRIKE_THROUGH(R.drawable.strike_through);

    fun toSpanStyle(): SpanStyle {
        return when (this) {
            BOLD -> SpanStyle(fontWeight = FontWeight.Bold)
            ITALIC -> SpanStyle(fontStyle = FontStyle.Italic)
            UNDERLINE -> SpanStyle(textDecoration = TextDecoration.Underline)
            STRIKE_THROUGH -> SpanStyle(textDecoration = TextDecoration.LineThrough)
        }
    }
}

fun RichTextState.hasSpanStyle(toolbarType: WritingToolbarType): Boolean {
    return when (toolbarType) {
        WritingToolbarType.BOLD -> currentSpanStyle.fontWeight == FontWeight.Bold
        WritingToolbarType.ITALIC -> currentSpanStyle.fontStyle == FontStyle.Italic
        WritingToolbarType.UNDERLINE -> currentSpanStyle.textDecoration?.contains(TextDecoration.Underline) == true
        WritingToolbarType.STRIKE_THROUGH -> currentSpanStyle.textDecoration?.contains(TextDecoration.LineThrough) == true
    }
}