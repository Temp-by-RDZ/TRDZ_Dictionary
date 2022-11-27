package com.trdz.dictionary.model.data_source_room.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import com.trdz.dictionary.base_utility.TYPE_TITLE

@Entity(tableName = "word")
data class WordEntity(
	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = PRIMARY_KEY)
	val index: Int,
	val name: String,
	val subName: String,
	val id: Long,
	val iconUrl: String,
	val type: Int,
	val group: Int,
	val search: String
) {
	companion object {
		const val PRIMARY_KEY = "index"
	}
}
