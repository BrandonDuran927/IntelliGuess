package com.example.intelliguess

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
            _collections.value = dao.getAllSubjCollections()
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
            _collections.value = dao.getAllSubjCollections() // Update the collections after deletion
        }
    }
    fun retrieveCurrentSubj(): SubjCollectionEnt? {
         if (_selectedSubj.value == null) {
             return if (_collections.value != null) {
                 _collections.value?.first()
             } else {
                 null
             }
         }
        return _selectedSubj.value
        // Add ternary operator to retrieve the first element
    }

    fun setCurrentSubject(subj: SubjCollectionEnt) {
        _selectedSubj.value = subj
    }
//

//
//    fun addMap(obj: SubjCollection, description: String, title: String) {
//        val currentSubj = _collections.value?.find { it.subject == obj.subject }
//        val updatedMapPair = currentSubj?.mapPair?.toMutableMap()?.apply {
//            put(description, title) //This
//        }
//        val updatedSubj = updatedMapPair?.let { currentSubj.copy(mapPair = it) }
//        val updatedList = _collections.value?.toMutableList()?.apply {
//            val indexToUpdate = indexOfFirst { it.subject == currentSubj?.subject }
//            if (indexToUpdate != -1) {
//                if (updatedSubj != null) {
//                    this[indexToUpdate] = updatedSubj
//                }
//            }
//        } ?: emptyList()
//        _collections.value = updatedList
//        _selectedSubj.value = updatedSubj!!
//    }
//
//    fun modifyMap(obj: SubjCollection, key: String) {
//        val updatedMap = obj.mapPair.toMutableMap().apply {
//            remove(key)
//        }
//        val updatedObj = obj.copy(mapPair = updatedMap)
//        _collections.value = _collections.value?.toMutableList()?.apply {
//            val indexToUpdate = indexOfFirst { it.subject == obj.subject }
//            this[indexToUpdate] = updatedObj
//        }
//        _selectedSubj.value = updatedObj
//    }
//
//    fun modifyMap(obj: SubjCollection, key: String, wins: MutableState<Int>, oldSubj: SubjCollection) {
//        val updatedMap = obj.mapPair.toMutableMap().apply {
//            remove(key)
//        }
//        val updatedObj = obj.copy(mapPair = updatedMap)
//        _collections.value = _collections.value?.toMutableList()?.apply {
//            val indexToUpdate = indexOfFirst { it.subject == obj.subject }
//            if (indexToUpdate != -1) {
//                this[indexToUpdate] = updatedObj
//            }
//        }
//        _selectedSubj.value = updatedObj
//        wins.value += 1 // if wins count is equal to the size of map pair, then stop incrementing
//
//        if (updatedObj.mapPair.isEmpty()) {
//            _selectedSubj.value = oldSubj
//        }
//    }
//
//    fun getItemRandomly(num: Int, subj: SubjCollection): MutableMap.MutableEntry<String, String>? {
//        val entriesList = subj.mapPair.entries.toList()
//        return if (num in entriesList.indices) {
//            entriesList[num]
//        } else {
//            null
//        }
//    }
//
//    fun getItemRandomly(): Int? {
//        val size = selectedSubj.value?.mapPair?.size ?: 0
//        return if (size > 0) {
//            (0 until size).random()
//        } else {
//            null
//        }
//    }
//
//    fun resetMap(oldObj: SubjCollection) {
//        val updatedList = _collections.value?.toMutableList()?.apply {
//            val indexToUpdate = indexOfFirst { it.subject == oldObj.subject }
//            if (indexToUpdate != -1) {
//                this[indexToUpdate] = oldObj
//            }
//        }
//        _collections.value = updatedList!!
//        _selectedSubj.value = oldObj
//    }
//
//    fun startEditing(subj: SubjCollection) {
//        collections.value?.let { list ->
//            val updatedCollections = list.map { collection ->
//                if (collection == subj) {
//                    collection.copy(isEditing = true)
//                } else {
//                    collection.copy(isEditing = false)
//                }
//            }
//            _collections.value = updatedCollections
//        }
//    }
//
//    fun editSubjDesc(subj: SubjCollection, editedDesc: String, currentTitle: String) {
//        val currentSubj = _collections.value?.find { it.subject == subj.subject }
//        val editedMapPair = currentSubj?.mapPair?.toMutableMap()?.apply {
//            put(editedDesc.trim(), currentTitle.trim())
//        }
//        val updatedSubj = editedMapPair?.let { currentSubj.copy(mapPair = it) }
//        val updatedList = _collections.value?.toMutableList()?.apply {
//            val indexToUpdate = indexOfFirst { it.subject == currentSubj?.subject }
//            if (indexToUpdate != -1) {
//                if (updatedSubj != null) {
//                    this[indexToUpdate] = updatedSubj
//                }
//            }
//        }
//        _collections.value = updatedList!!
//        _selectedSubj.value = updatedSubj!!
//    }
}