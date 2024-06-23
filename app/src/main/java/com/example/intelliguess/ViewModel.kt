package com.example.intelliguess

import androidx.compose.runtime.MutableState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch

class IntelliGuessViewModel(
    private val dao: SubjCollectionDao
) : ViewModel() {
    private val _collections = MutableLiveData<List<SubjCollectionEnt>>()
    val collections: LiveData<List<SubjCollectionEnt>>
        get() = _collections

    private val _selectedSubj = MutableLiveData<SubjCollectionEnt>()
    val selectedSubj: LiveData<SubjCollectionEnt>
        get() = _selectedSubj

    init {
        viewModelScope.launch {
            // Ensures the LiveData _collections contains the latest data
            _collections.value = dao.getAllSubjCollections()

            // Ensuring the UI reflects the latest state
            if(_collections.value!!.isNotEmpty()) {
                _selectedSubj.value = _collections.value?.first()
            }
        }
    }

    fun add(addedSubj: String) {
        viewModelScope.launch {
            val newPair = mutableMapOf<String, String>()
            val newCard = SubjCollectionEnt(addedSubj, newPair)
            dao.upsertSubjCollection(newCard)
            _collections.value = dao.getAllSubjCollections() // Fetch updated data
            _selectedSubj.value = _collections.value?.last()
        }
    }

    fun remove(subj: SubjCollectionEnt) {
        viewModelScope.launch {
            dao.deleteSubjCollection(subj)
            _collections.value =
                dao.getAllSubjCollections() // Update the collections after deletion
            if (_collections.value!!.isNotEmpty()) {
                _selectedSubj.value = _collections.value!!.last() ?: null
            }
        }

    }

    fun setCurrentSubject(subj: SubjCollectionEnt) {
        _selectedSubj.value = subj
    }

    fun modifyMap(subj: SubjCollectionEnt, description: String, title: String) {
        viewModelScope.launch {
            val updatedCollections = _collections.value?.map { collection ->
                if (collection.subject == subj.subject) {
                    val updatedMapPair = collection.mapPair.toMutableMap().apply {
                        put(description.trim(), title.trim())
                    }
                    collection.copy(mapPair = updatedMapPair, isEditing = false)
                } else {
                    collection
                }
            }
            if (updatedCollections != null) {
                dao.upsertSubjCollection(updatedCollections.first { it.subject == subj.subject })
            }
            _selectedSubj.value = updatedCollections?.first { it.subject == subj.subject }
            _collections.value = dao.getAllSubjCollections()
        }
    }

    fun modifyMap(subj: SubjCollectionEnt, key: String, oldSubj: SubjCollectionEnt) {
        viewModelScope.launch {
            val currentCollections = _collections.value?.toMutableList() ?: mutableListOf()

            val currentSubj = _collections.value?.find { it.subject == subj.subject }
            currentSubj.let {
                val updatedMapPair = currentSubj?.mapPair?.toMutableMap()?.apply {
                    remove(key)
                }
                val updatedSubj = updatedMapPair?.let { currentSubj.copy(mapPair = it) }

                // Update the local list
                val index = currentCollections.indexOf(subj)
                if (index != -1 && updatedSubj != null) {
                    currentCollections[index] = updatedSubj
                }

                // Update the database
                if (updatedSubj != null) {
                    dao.upsertSubjCollection(updatedSubj)
                }

                // Update the LiveData
                _collections.value = dao.getAllSubjCollections()
                _selectedSubj.value = updatedSubj!!

                if (updatedSubj.mapPair.isEmpty()) {
                    _selectedSubj.value = oldSubj
                }
            }
        }
    }
    fun startEditing(subj: SubjCollectionEnt) {
        viewModelScope.launch {
            _collections.value?.let { list ->
                val updatedCollections = list.map { obj ->
                    if (obj == subj) {
                        obj.copy(isEditing = true)
                    } else {
                        obj.copy(isEditing = false)
                    }
                }
                dao.updateSubjCollection(updatedCollections)
                _collections.value = dao.getAllSubjCollections()
            }
        }
    }

    fun deleteFromMap(subj: SubjCollectionEnt, key: String) {
        viewModelScope.launch {
            val updatedMap = subj.mapPair.toMutableMap().apply {
                remove(key)
            }
            val updatedObj = subj.copy(mapPair = updatedMap)
            dao.upsertSubjCollection(updatedObj)
            _collections.value = dao.getAllSubjCollections()
            _selectedSubj.value = updatedObj
        }
    }
    fun getItemRandomly(num: Int, subj: SubjCollectionEnt): MutableMap.MutableEntry<String, String>? {
        val entriesList = subj.mapPair.entries.toList()
        var tmp = num

        if (tmp < 0) {
            tmp += 1
        }

        return if (tmp in entriesList.indices) {
            entriesList[tmp]
        } else {
            null
        }
    }
    fun getItemRandomly(): Int? {
        val size = _selectedSubj.value?.mapPair?.size ?: 0

        return if (size > 0) {
            (0 until size).random()
        } else {
            null
        }
    }

    fun resetMap(subj: SubjCollectionEnt) {
        viewModelScope.launch {
            dao.upsertSubjCollection(subj) // Assuming subj has the original mapPair
            _collections.value = dao.getAllSubjCollections()
            _selectedSubj.value = subj
        }
    }
}

