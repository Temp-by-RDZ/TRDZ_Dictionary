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

	private lateinit var dataSource: ADataSource

	override fun setSource(index: Int) {
		when (index) {
			IN_BASIS -> dataSource = dataBasis
			IN_STORAGE -> dataSource = dataInternal
			IN_SERVER -> dataSource = dataServer
		}
	}

	//region Word Data update

	override suspend fun initWordList(target: String): RequestResults {
		return dataSource.loadWords(target)
	}

	override fun update(currentData: List<DataWord>,target: String) {
		Log.d("@@@", "Rep - User Saving...")
		internalStorage.saveWords(currentData, target)
	}

	//endregion

	//region Favor Data update

	override suspend fun initFavorList(): List<DataLine> {
		return internalStorage.loadFavor()
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

}
