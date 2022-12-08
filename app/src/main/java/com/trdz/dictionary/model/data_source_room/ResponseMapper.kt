package com.trdz.dictionary.model.data_source_room

import com.trdz.dictionary.base_utility.TYPE_TITLE
import com.trdz.dictionary.model.DataLine
import com.trdz.dictionary.model.DataWord
import com.trdz.dictionary.model.VisualState
import com.trdz.dictionary.model.data_source_room.database.EntityFavor
import com.trdz.dictionary.model.data_source_room.database.EntityWord

object ResponseMapper {

	fun toStorage(data: DataWord, search: String): EntityWord {
		return with(data) {
			EntityWord(
				index = 0,
				id = id.toLong(),
				name = name,
				subName = subName,
				iconUrl = iconUrl,
				type = visual.type,
				group = visual.group,
				search = search
			)
		}
	}

	fun fromStorage(entity: EntityWord): DataWord {
		var state = 1
		if (entity.type != TYPE_TITLE) {
			state = 2
		}
		return with(entity) {
			DataWord(
				id = id.toInt(),
				name = name,
				subName = subName,
				iconUrl = iconUrl,
				visual = VisualState(
					type = type,
					group = group,
					state = state
				)
			)
		}
	}

	fun toStorage(data: DataLine): EntityFavor {
		return with(data) {
			EntityFavor(
				id = id,
				name = name,
			)
		}
	}

	fun fromStorage(entity: EntityFavor): DataLine {
		return with(entity) {
			DataLine(
				id = id,
				name = name,
			)
		}
	}
}
