package com.trdz.dictionary.model.data_source_room.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "words")
data class WordEntity(
	@PrimaryKey
	@ColumnInfo(name = PRIMARY_KEY)
	val id: Long,
	val name: String,
	val subName: String,
	val group: Int,
	val iconUrl: String,
) {

	companion object {
		const val PRIMARY_KEY = "id"
	}
}
