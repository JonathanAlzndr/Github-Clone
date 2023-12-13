package com.jonathan.mygithubsearchuser.ui.helper

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jonathan.mygithubsearchuser.ui.ThemeSetting.SettingPreferences
import com.jonathan.mygithubsearchuser.ui.ThemeSetting.ThemeViewModel

class ViewModelFactorySetting(private val pref: SettingPreferences) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ThemeViewModel::class.java)) {
            return ThemeViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}