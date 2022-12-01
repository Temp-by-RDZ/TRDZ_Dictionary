package com.trdz.dictionary.model.data_source_room.database

import androidx.room.*

@Dao
interface WordDao {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	abstract fun saveWord(words: List<WordEntity>)

	@Transaction
	@Query("SELECT * FROM word WHERE search = :search")
	abstract fun getWords(search: String): List<WordEntity>

	@Delete
	abstract fun delete(wordsDBObject: WordEntity)
}