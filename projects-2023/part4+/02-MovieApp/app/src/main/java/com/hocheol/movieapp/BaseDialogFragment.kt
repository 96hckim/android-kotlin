package com.hocheol.movieapp

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.hocheol.movieapp.features.common.viewmodel.ThemeViewModel

open class BaseDialogFragment : DialogFragment() {
    protected val themeViewModel: ThemeViewModel by activityViewModels()
}