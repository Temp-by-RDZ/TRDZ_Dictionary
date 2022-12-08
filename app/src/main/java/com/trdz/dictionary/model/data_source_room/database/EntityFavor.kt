package com.trdz.dictionary.model.data_source_room.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favor")
data class EntityFavor(
	@PrimaryKey()
	@ColumnInfo(name = PRIMARY_KEY)
	val id: Int,
	val name: String,
) {
	companion object {
		const val PRIMARY_KEY = "id"
	}
}
