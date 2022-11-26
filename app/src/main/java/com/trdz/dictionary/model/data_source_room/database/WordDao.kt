package com.trdz.dictionary.model.data_source_room.database

import androidx.room.*
import io.reactivex.rxjava3.core.Completable

@Dao
interface WordDao {
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	abstract fun insert(word: WordEntity)

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	abstract fun insertAll(words: List<WordEntity>)

	@Query("SELECT * FROM words")
	abstract fun getUsers(): List<WordEntity>

	@Delete
	abstract fun delete(wordDBObject: WordEntity): Completable
}