package com.trdz.dictionary.model.source_room.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [EntityWord::class, EntitySearch::class, EntityFavor::class], version = 1)
abstract class DataBase: RoomDatabase() {
	abstract fun userDao(): WordDao
}