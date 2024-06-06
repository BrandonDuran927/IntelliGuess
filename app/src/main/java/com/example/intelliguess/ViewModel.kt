package com.example.intelliguess

import androidx.compose.runtime.MutableState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.intelliguess.data.SubjCollection

class IntelliGuessViewModel : ViewModel() {
    private val _collections = MutableLiveData<List<SubjCollection>>()
    val collections: LiveData<List<SubjCollection>>
        get() = _collections

    private val _selectedSubj = MutableLiveData<SubjCollection>()
    val selectedSubj: LiveData<SubjCollection>
        get() = _selectedSubj

    fun add(addedSubj: String) {
        val newPair = mutableMapOf<String, String>()
        val newCard = SubjCollection(addedSubj, newPair)
        val currentList = _collections.value ?: emptyList()
        _collections.value = currentList.plus(newCard)
        _selectedSubj.value = newCard
    }

    fun removeSubject(subj: SubjCollection) {
        _collections.value = _collections.value?.toMutableList()?.apply {
            remove(subj)
        }
    }

    fun setSelectedSubj(subj: SubjCollection) {
        _selectedSubj.value = subj
    }

    fun addMap(obj: SubjCollection, description: String, title: String) {
        val currentSubj = _collections.value?.find { it.subject == obj.subject }
        val updatedMapPair = currentSubj?.mapPair?.toMutableMap()?.apply {
            put(description, title) //This
        }
        val updatedSubj = updatedMapPair?.let { currentSubj.copy(mapPair = it) }
        val updatedList = _collections.value?.toMutableList()?.apply {
            val indexToUpdate = indexOfFirst { it.subject == currentSubj?.subject }
            if (indexToUpdate != -1) {
                if (updatedSubj != null) {
                    this[indexToUpdate] = updatedSubj
                }
            }
        } ?: emptyList()
        _collections.value = updatedList
        _selectedSubj.value = updatedSubj!!
    }

    fun modifyMap(obj: SubjCollection, key: String) {
        val updatedMap = obj.mapPair.toMutableMap().apply {
            remove(key)
        }
        val updatedObj = obj.copy(mapPair = updatedMap)
        _collections.value = _collections.value?.toMutableList()?.apply {
            val indexToUpdate = indexOfFirst { it.subject == obj.subject }
            this[indexToUpdate] = updatedObj
        }
        _selectedSubj.value = updatedObj
    }

    fun modifyMap(obj: SubjCollection, key: String, wins: MutableState<Int>, oldSubj: SubjCollection) {
        val updatedMap = obj.mapPair.toMutableMap().apply {
            remove(key)
        }
        val updatedObj = obj.copy(mapPair = updatedMap)
        _collections.value = _collections.value?.toMutableList()?.apply {
            val indexToUpdate = indexOfFirst { it.subject == obj.subject }
            if (indexToUpdate != -1) {
                this[indexToUpdate] = updatedObj
            }
        }
        _selectedSubj.value = updatedObj
        wins.value += 1 // if wins count is equal to the size of map pair, then stop incrementing

        if (updatedObj.mapPair.isEmpty()) {
            _selectedSubj.value = oldSubj
        }
    }

    fun getItemRandomly(num: Int, subj: SubjCollection): MutableMap.MutableEntry<String, String>? {
        val entriesList = subj.mapPair.entries.toList()
        return if (num in entriesList.indices) {
            entriesList[num]
        } else {
            null
        }
    }

    fun getItemRandomly(): Int? {
        val size = selectedSubj.value?.mapPair?.size ?: 0
        return if (size > 0) {
            (0 until size).random()
        } else {
            null
        }
    }

    fun resetMap(oldObj: SubjCollection) {
        val updatedList = _collections.value?.toMutableList()?.apply {
            val indexToUpdate = indexOfFirst { it.subject == oldObj.subject }
            if (indexToUpdate != -1) {
                this[indexToUpdate] = oldObj
            }
        }
        _collections.value = updatedList!!
        _selectedSubj.value = oldObj
    }

    fun startEditing(subj: SubjCollection) {
        collections.value?.let { list ->
            val updatedCollections = list.map { collection ->
                if (collection == subj) {
                    collection.copy(isEditing = true)
                } else {
                    collection.copy(isEditing = false)
                }
            }
            _collections.value = updatedCollections
        }
    }

    fun editSubjDesc(subj: SubjCollection, editedDesc: String, currentTitle: String) {
        val currentSubj = _collections.value?.find { it.subject == subj.subject }
        val editedMapPair = currentSubj?.mapPair?.toMutableMap()?.apply {
            put(editedDesc.trim(), currentTitle.trim())
        }
        val updatedSubj = editedMapPair?.let { currentSubj.copy(mapPair = it) }
        val updatedList = _collections.value?.toMutableList()?.apply {
            val indexToUpdate = indexOfFirst { it.subject == currentSubj?.subject }
            if (indexToUpdate != -1) {
                if (updatedSubj != null) {
                    this[indexToUpdate] = updatedSubj
                }
            }
        }
        _collections.value = updatedList!!
        _selectedSubj.value = updatedSubj!!
    }
}