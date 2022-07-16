package com.steve.msms.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TaggedSmsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTag(tag: Tag): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTagSms(tag: Tag)

    // used to update existing transaction
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTagSms(tag: Tag)

    // get all saved transaction list
    @Query("SELECT * FROM tags ")
    fun getAllTags(): Flow<List<Tag>>


    @Query("SELECT * FROM tags WHERE tag  LIKE '%' || :tag || '%'")
    suspend fun getTags(tag: String): List<Tag>

    @Query("SELECT * FROM tags WHERE id = :id")

    suspend fun getTagsWithId(id: String): Tag?

}