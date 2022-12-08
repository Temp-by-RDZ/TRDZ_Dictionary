package com.trdz.dictionary.model.data_source_room.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search")
data class EntitySearch(
	@PrimaryKey()
	@ColumnInfo(name = PRIMARY_KEY)
	val id: Int,
	val search: String,
) {
	companion object {
		const val PRIMARY_KEY = "id"
	}
}
