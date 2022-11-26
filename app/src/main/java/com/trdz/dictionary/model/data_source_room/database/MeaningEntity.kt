package com.trdz.dictionary.model.data_source_room.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity(tableName = "repos")
data class MeaningEntity(
	@PrimaryKey
	@ColumnInfo(name = PRIMARY_KEY)
	val id: Long,
	val name: String,
) {
	companion object {
		const val PRIMARY_KEY = "id"
		const val FOREIGN_WORD_KEY = "userId"
	}
}
