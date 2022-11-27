package com.trdz.dictionary.model.data_source_room

import com.trdz.dictionary.base_utility.TYPE_TITLE
import com.trdz.dictionary.model.DataWord
import com.trdz.dictionary.model.data_source_room.database.WordEntity

object ResponseMapper {

	fun toStorage(data: DataWord, search: String): WordEntity {
		return with(data) {
			WordEntity(
				index = 0,
				id = id.toLong(),
				name = name,
				subName = subName,
				iconUrl = iconUrl,
				type = type,
				group = group,
				search = search
			)
		}
	}

	fun fromStorage(entity: WordEntity): DataWord {
		var state = 1
		if (entity.type != TYPE_TITLE) {state =2}
		return with(entity) {
			DataWord(
				id = id.toInt(),
				name = name,
				subName = subName,
				iconUrl = iconUrl,
				type = type,
				group = group,
				state = state
			)
		}
	}

}
