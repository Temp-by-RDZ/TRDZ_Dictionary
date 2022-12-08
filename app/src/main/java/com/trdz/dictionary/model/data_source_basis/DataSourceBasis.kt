package com.trdz.dictionary.model.data_source_basis

import com.trdz.dictionary.base_utility.TYPE_TITLE
import com.trdz.dictionary.model.*

class DataSourceBasis: ADataSource {

	private val basisData = listOf(
		DataWord(name = "Не найдено", subName = "Этого слова нет в ScyEng базе", id = 0, iconUrl = "",
			visual = VisualState(type = TYPE_TITLE,  group = 1)),
	)

	override fun loadWords(target: String): RequestResults = RequestResults.SuccessWords(ServersResult(0, basisData))

}
