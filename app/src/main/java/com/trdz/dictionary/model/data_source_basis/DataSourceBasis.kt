package com.trdz.dictionary.model.data_source_basis

import com.trdz.dictionary.base_utility.TYPE_CARD
import com.trdz.dictionary.base_utility.TYPE_TITLE
import com.trdz.dictionary.model.ADataSource
import com.trdz.dictionary.model.DataWord
import com.trdz.dictionary.model.ServersResult
import io.reactivex.rxjava3.core.Single

class DataSourceBasis: ADataSource {

	private val basisData = listOf(
		DataWord(name = "Error", subName = "Missing Words", id = 0, type = TYPE_TITLE, iconUrl = "", group = 1),
		DataWord(name = "Ошибка", 		subName = "", id = 0, type = TYPE_CARD, iconUrl = "", group = 2),
		DataWord(name = "Нет", 			subName = "", id = 0, type = TYPE_CARD, iconUrl = "", group = 2),
		DataWord(name = "Возможности", 	subName = "", id = 0, type = TYPE_CARD, iconUrl = "", group = 2),
		DataWord(name = "Подключится", 	subName = "", id = 0, type = TYPE_CARD, iconUrl = "", group = 2),
	)

	override fun loadWords(target: String): Single<ServersResult> = Single.create {
		it.onSuccess(ServersResult(0, basisData))
	}


}
