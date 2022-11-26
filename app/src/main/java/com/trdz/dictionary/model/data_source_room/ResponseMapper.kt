package com.trdz.dictionary.model.data_source_room

import com.trdz.dictionary.base_utility.TYPE_TITLE
import com.trdz.dictionary.model.DataWord
import com.trdz.dictionary.model.data_source_room.database.WordEntity

object ResponseMapper {

	fun toStorage(data: DataWord): WordEntity {
		return with(data) {
			WordEntity(
				id = id.toLong(),
				name = name,
				subName = subName,
				iconUrl = iconUrl,
				group = group,
			)
		}
	}

	fun fromStorage(entity: WordEntity): DataWord {
		return with(entity) {
			DataWord(
				id = id.toInt(),
				name = name,
				subName = subName,
				iconUrl = iconUrl,
				type = TYPE_TITLE,
				group = group,
				state = 1
			)
		}
	}

}
