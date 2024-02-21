package com.hocheol.movieapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.hocheol.movieapp.features.common.viewmodel.ThemeViewModel

open class BaseFragment : Fragment() {
    protected val themeViewModel: ThemeViewModel by activityViewModels()
}