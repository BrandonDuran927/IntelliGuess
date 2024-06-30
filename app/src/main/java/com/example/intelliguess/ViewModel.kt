package com.example.intelliguess

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.intelliguess.data.SubjCollectionEnt
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class IntelliGuessViewModel(
    private val dao: SubjCollectionDao
) : ViewModel() {
    var isInHomeScreen = MutableLiveData<Boolean>()

    // Responsible for updating the collections continuously using the LiveData
    private val _collections = MutableLiveData<List<SubjCollectionEnt>>()
    val collections: LiveData<List<SubjCollectionEnt>>
        get() = _collections

    // Responsible for updating the current subject continuously using the LiveData
    private val _selectedSubj = MutableLiveData<SubjCollectionEnt?>()
    val selectedSubj: LiveData<SubjCollectionEnt?>
        get() = _selectedSubj

    // Stores the previous selected subject
    private val _oldSubj = MutableLiveData<SubjCollectionEnt>()


    // Initializer of this class
    init {
        viewModelScope.launch {
            delay(1000L)
            // Ensures the LiveData _collections contains the latest data
            _collections.value = dao.getAllSubjCollections()
            // Ensuring the UI reflects the latest state
            if(_collections.value!!.isNotEmpty()) {
                _selectedSubj.value = _collections.value?.first()
            }
        }
    }

    // Add a new object/entity
    fun add(addedSubj: String) {
        viewModelScope.launch {
            val newPair = mutableMapOf<String, String>()
            val newCard = SubjCollectionEnt(addedSubj, newPair)
            dao.upsertSubjCollection(newCard)
            _collections.value = dao.getAllSubjCollections() // Fetch updated data
            _selectedSubj.value = _collections.value?.last()
        }
    }

    // Removes an object/entity
    fun remove(subj: SubjCollectionEnt) {
        viewModelScope.launch {
            dao.deleteSubjCollection(subj) // Delete the specific subject from the table
            _collections.value = dao.getAllSubjCollections() // Update the collections after deletion
            Log.d("Remove", "size: ${_collections.value!!.size}, subject: ${_selectedSubj.value}")
            if (_collections.value!!.isNotEmpty()) {
                _selectedSubj.value = _collections.value!!.last()
            } else {
                _selectedSubj.value = null
            }
        }
    }



    // Responsible for adding a dictionary or editing the existing dictionary
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

    // Responsible for removing a key in the current subj and when the size of the map is 0, the
    // oldSubj will passed its value to the _selectedSubj
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
                    _oldSubj.value = oldSubj
                }
            }
        }
    }

    // Start editing the object/entity
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

    // Delete a key from map directly without retrieving
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

    // Generate a random entry in the map
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

    // Generate a random number between 0 to the size of the map of _selectedSubj
    fun getItemRandomly(): Int? {
        val size = _selectedSubj.value?.mapPair?.size ?: 0

        return if (size > 0) {
            (0 until size).random()
        } else {
            null
        }
    }

    // Reset the map to _oldSubj
    fun resetMap(subj: SubjCollectionEnt) {
        viewModelScope.launch {
            dao.upsertSubjCollection(subj) // Assuming subj has the original mapPair
            _collections.value = dao.getAllSubjCollections()
            _selectedSubj.value = subj
            _oldSubj.value = subj
        }
    }

    // Reset the map without passing an object/entity
    fun resetMap() {
        viewModelScope.launch {
            try {
                if (_collections.value?.isNotEmpty() == true) {
                    Log.d("IntelliGuessViewModel", "Selected Subject value: ${_selectedSubj.value}")
                    _oldSubj.value?.let { dao.upsertSubjCollection(it) }
                    _collections.value = dao.getAllSubjCollections()
                    _selectedSubj.value = _oldSubj.value
                }
            } catch (e: Exception) {
                Log.e("IntelliGuessViewModel", "Error resetting map because ${_oldSubj.value}", e)
            }
        }
    }

    // Set the value of subj to _selectedSubj
    fun setCurrentSubject(subj: SubjCollectionEnt) {
        viewModelScope.launch {
            _collections.value = dao.getAllSubjCollections()
            _selectedSubj.value = subj
        }
    }

    // Set the oldSubj value using the subj parameter
    fun setOldSubjectValue(subj: SubjCollectionEnt) {
        viewModelScope.launch {
            _oldSubj.value = subj
        }
    }

    // Restore the old value of _selectedSubj ensuring it retrieves the previous deleted pair/s
    fun changeSubject(subj: SubjCollectionEnt, oldSubj: SubjCollectionEnt) {
        viewModelScope.launch {
            dao.upsertSubjCollection(oldSubj)
            _selectedSubj.value = subj
            _collections.value = dao.getAllSubjCollections()
        }
    }
}

