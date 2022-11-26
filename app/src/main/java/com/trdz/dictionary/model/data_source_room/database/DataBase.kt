package com.trdz.dictionary.model.data_source_room.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [WordEntity::class,MeaningEntity::class], version = 1)
abstract class DataBase: RoomDatabase() {
	abstract fun userDao(): WordDao
}