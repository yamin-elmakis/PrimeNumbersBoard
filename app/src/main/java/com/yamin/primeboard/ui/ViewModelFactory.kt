package com.yamin.primeboard.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yamin.primeboard.repo.NumbersRepository
import com.yamin.primeboard.ui.main.MainViewModel

class ViewModelFactory(
    private val repo: NumbersRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when (modelClass) {

            MainViewModel::class.java -> {
                MainViewModel(
                    repo = repo
                )
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
        } as T
    }
}