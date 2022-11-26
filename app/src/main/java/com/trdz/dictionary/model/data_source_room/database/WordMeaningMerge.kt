package com.trdz.dictionary.model.data_source_room.database

import androidx.room.Embedded
import androidx.room.Relation

data class WordMeaningMerge (
	@Embedded
	val user: WordEntity,
	@Relation(
		parentColumn = WordEntity.PRIMARY_KEY,
		entityColumn = MeaningEntity.FOREIGN_WORD_KEY
	)
	val repos: List<MeaningEntity>)

