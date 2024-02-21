package com.hocheol.movieapp

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels

open class BaseDialogFragment : DialogFragment() {
    protected val themeViewModel: ThemeViewModel by activityViewModels()
}