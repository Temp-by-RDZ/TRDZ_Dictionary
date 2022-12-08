package com.trdz.dictionary.model

import android.util.Log
import com.trdz.dictionary.base_utility.IN_BASIS
import com.trdz.dictionary.base_utility.IN_SERVER
import com.trdz.dictionary.base_utility.IN_STORAGE

class RepositoryExecutor(
	private val internalStorage: InternalData,
	private val dataBasis: ADataSource,
	private val dataServer: ADataSource,
	private val dataInternal: ADataSource,
): Repository {

	private var lastSearch: String = ""
	private lateinit var dataSource: ADataSource

	override fun setSource(index: Int) {
		when (index) {
			IN_BASIS -> dataSource = dataBasis
			IN_STORAGE -> dataSource = dataInternal
			IN_SERVER -> dataSource = dataServer
		}
	}

	override fun checkLast() = lastSearch


	//region Word Data update

	override suspend fun initWordList(target: String): RequestResults {
		lastSearch = target
		return dataSource.loadWords(target)
	}

	override fun update(currentData: List<DataWord>, target: String) {
		Log.d("@@@", "Rep - Words Saving...")
		internalStorage.saveWords(currentData, target)
		update(target)
	}

	override suspend fun analyze(data: List<DataWord>): List<DataWord> {
		val favors = initFavorList()
		data.forEachIndexed { index, elem ->
			favors.forEach { favor ->
				if (favor.name == elem.name) {
					data[index].visual.expand = true
				}
			}
		}
		return data
	}

	//endregion

	//region Favor Data update

	override suspend fun initFavorList(): List<DataLine> {
		return internalStorage.loadFavor()
	}

	override fun update(list: List<DataLine>) {
		Log.d("@@@", "Rep - Favor Saving...")
		internalStorage.saveFavor(list)
	}

	override fun addFavorite(data: DataLine) {
		Log.d("@@@", "Rep - Adding favorite...")
		internalStorage.addFavorite(data)
	}

	override fun removeFavorite(data: DataLine) {
		Log.d("@@@", "Rep - Remove favorite...")
		internalStorage.removeFavorite(data)
	}

	//endregion

	//region History Data update

	override suspend fun initSearchList(): List<DataLine> {
		return internalStorage.loadHistory()
	}

	override fun update(target: String) {
		Log.d("@@@", "Rep - Search Saving...")
		internalStorage.saveHistory(DataLine(0, target))
	}

	//endregion

}
