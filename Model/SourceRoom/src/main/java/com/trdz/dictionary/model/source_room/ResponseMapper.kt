package com.trdz.dictionary.model.source_room

import com.trdz.dictionary.model.data.DataLine
import com.trdz.dictionary.model.data.DataWord
import com.trdz.dictionary.model.data.VisualState
import com.trdz.dictionary.model.source_room.database.EntityFavor
import com.trdz.dictionary.model.source_room.database.EntitySearch
import com.trdz.dictionary.model.source_room.database.EntityWord
import com.trdz.dictionary.utility.TYPE_TITLE

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

	fun toStorageSearch(data: DataLine): EntitySearch {
		return with(data) {
			EntitySearch(
				id = id,
				search = name,
			)
		}
	}

	fun fromStorage(entity: EntitySearch): DataLine {
		return with(entity) {
			DataLine(
				id = id,
				name = search,
			)
		}
	}
}
