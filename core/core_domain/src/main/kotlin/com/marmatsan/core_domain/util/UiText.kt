package com.marmatsan.core_domain.util

import android.content.Context

sealed interface UiText {
    data class DynamicString(val text: String) : UiText
    data class StringResource(val resId: Int) : UiText

    fun asString(context: Context): String {
        return when (this) {
            is DynamicString -> text
            is StringResource -> context.getString(resId)
        }
    }
}