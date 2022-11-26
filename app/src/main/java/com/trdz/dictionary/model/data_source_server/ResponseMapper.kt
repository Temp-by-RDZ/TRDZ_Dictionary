package com.trdz.dictionary.model.data_source_server

import com.trdz.dictionary.base_utility.TYPE_CARD
import com.trdz.dictionary.base_utility.TYPE_TITLE
import com.trdz.dictionary.model.DataWord
import com.trdz.dictionary.model.data_source_server.data_word.dto.Meaning
import com.trdz.dictionary.model.data_source_server.data_word.dto.WordsDTOItem

object ResponseMapper {
	fun mapToEntity(dto: WordsDTOItem, index: Int): DataWord {
		val trn = (dto.meanings.lastOrNull()?.transcription ?: "")
		return with(dto) {
			DataWord(
				id = id,
				name = text,
				subName = trn,
				iconUrl = "",
				type = TYPE_TITLE,
				group = index,
				state = 1
			)
		}
	}

	fun mapToEntity(dto: Meaning, index: Int): DataWord {
		return with(dto) {
			DataWord(
				id = id,
				name = translation.text,
				subName = translation.note ?: "",
				iconUrl = imageUrl,
				type = TYPE_CARD,
				group = index,
				state = 2
			)
		}
	}
}
