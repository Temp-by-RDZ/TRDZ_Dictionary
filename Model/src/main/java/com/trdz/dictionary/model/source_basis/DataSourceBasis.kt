package com.trdz.dictionary.model.source_basis

import com.trdz.dictionary.model.data.*
import com.trdz.dictionary.utility.TYPE_TITLE

class DataSourceBasis: ADataSource {

	private val basisData = listOf<DataWord>(
		DataWord(name = "Не найдено", subName = "Этого слова нет в ScyEng базе", id = 0, iconUrl = "",
			visual = VisualState(type = TYPE_TITLE, group = 1)),
	)

	override fun loadWords(target: String): RequestResults = RequestResults.SuccessWords(ServersResult(0, basisData))

}
