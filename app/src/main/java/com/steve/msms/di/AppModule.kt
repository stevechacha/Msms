package com.steve.msms.di

import android.content.Context
import androidx.room.Room
import com.steve.msms.data.local.AppDatabase
import com.steve.msms.data.local.TaggedSmsDao
import com.steve.msms.services.exportcsv.ExportCsvService
import com.steve.msms.utils.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideExportCSV(@ApplicationContext context: Context): ExportCsvService {
        return ExportCsvService(appContext = context)
    }
}