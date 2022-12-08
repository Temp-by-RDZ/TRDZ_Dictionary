package com.trdz.dictionary.model.data_source_room.database

import androidx.room.*

@Dao
interface WordDao {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	abstract fun saveWord(words: List<EntityWord>)

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	abstract fun saveFavor(favor: List<EntityFavor>)

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	abstract fun addFavor(favor: EntityFavor)

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	abstract fun addSearch(search: EntitySearch)

	@Transaction
	@Query("SELECT * FROM word WHERE search = :search")
	abstract fun getWords(search: String): List<EntityWord>

	@Transaction
	@Query("SELECT * FROM favor")
	abstract fun getFavor(): List<EntityFavor>

	@Transaction
	@Query("SELECT * FROM search")
	abstract fun getSearch(): List<EntitySearch>

	@Delete
	abstract fun deleteFavor(favor: EntityFavor)

	@Delete
	abstract fun delete(wordsDBObject: EntityWord)

	@Query("DELETE FROM favor")
	abstract fun clearFavor()

	@Query("DELETE FROM search")
	abstract fun clearHistory()
}