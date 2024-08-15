package com.example.intelliguess.data

import android.content.Context
import androidx.room.Room
import com.example.intelliguess.SubjCollectionDao
import com.example.intelliguess.SubjCollectionDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideLocalDatabase(@ApplicationContext context: Context) : SubjCollectionDatabase =
        Room.databaseBuilder(
            context,
            SubjCollectionDatabase::class.java,
            "SubjCollectionDatabase"
        ).build()

    @Singleton
    @Provides
    fun provideDao(database : SubjCollectionDatabase) : SubjCollectionDao = database.dao

}