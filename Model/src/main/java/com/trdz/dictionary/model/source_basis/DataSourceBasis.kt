package com.trdz.dictionary.model.source_basis

import com.trdz.dictionary.model.data.RequestResults
import com.trdz.dictionary.model.data.ServersResult
import com.trdz.dictionary.utility.TYPE_TITLE

class DataSourceBasis: com.trdz.dictionary.model.data.ADataSource {

	private val basisData = listOf(
		com.trdz.dictionary.model.data.DataWord(name = "Не найдено", subName = "Этого слова нет в ScyEng базе", id = 0, iconUrl = "",
			visual = com.trdz.dictionary.model.data.VisualState(type = TYPE_TITLE, group = 1)),
	)

	override fun loadWords(target: String): RequestResults = RequestResults.SuccessWords(ServersResult(0, basisData))

}
