package com.example.intelliguess

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

// Creates a factory that passes the dao as parameter
class IntelliGuessViewModelFactory(
    private val dao: SubjCollectionDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(IntelliGuessViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return IntelliGuessViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}