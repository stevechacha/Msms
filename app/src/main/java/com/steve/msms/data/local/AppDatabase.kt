package com.steve.msms.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.steve.msms.utils.DATABASE_NAME


@Database(
    entities = [Tag::class],
    version = 3
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun taggedSmsDao(): TaggedSmsDao

}