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
                _selectedSubj.value = _collections.value?.last()
            }
        }

    }

    fun setCurrentSubject(subj: SubjCollectionEnt) {
        _selectedSubj.value = subj
    }

    fun modifyMap(subj: SubjCollectionEnt, description: String, title: String) {
        viewModelScope.launch {
            val currentCollections = _collections.value?.toMutableList() ?: mutableListOf()

            val currentSubj = _collections.value?.find { it.subject == subj.subject }
            currentSubj.let {
                val updatedMapPair = currentSubj?.mapPair?.toMutableMap()?.apply {
                    put(description.trim(), title.trim())
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
    fun startEditing(subj: SubjCollectionEnt) {
        _collections.value?.let { list ->
            val updatedCollections = list.map { obj ->
                if (obj == subj) {
                    obj.copy(isEditing = true)
                } else {
                    obj.copy(isEditing = false)
                }
            }
            _collections.value = updatedCollections
        }
    }


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
//
}