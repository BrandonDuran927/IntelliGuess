package com.example.intelliguess

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.intelliguess.data.SubjCollectionEnt

// Serves as Data Access Object that interacts with database
@Dao
interface SubjCollectionDao {
    //Update and insert the newly modified subject
    @Upsert
    suspend fun upsertSubjCollection(subjCollectionEnt: SubjCollectionEnt)

    //Update the newly modified collection
    @Update
    suspend fun updateSubjCollection(collections: List<SubjCollectionEnt>)

    // Delete the specific subject
    @Delete
    suspend fun deleteSubjCollection(subjCollectionEnt: SubjCollectionEnt)

    // Retrieve all the subject from the table/database
    @Query("SELECT * FROM SubjCollectionEnt")
    suspend fun getAllSubjCollections(): List<SubjCollectionEnt>
}