package com.trdz.dictionary.model.data_source_room

import android.util.Log
import com.trdz.dictionary.MyApp
import com.trdz.dictionary.model.*
import com.trdz.dictionary.model.data_source_room.database.EntityFavor
import com.trdz.dictionary.model.data_source_room.database.WordDao
import com.trdz.dictionary.model.data_source_room.database.EntityWord

class InternalStorage: ADataSource, InternalData {

	private fun getData(): WordDao = MyApp.di.get(WordDao::class)

	//region Data Words

	override fun saveWords(list: List<DataWord>, search: String) {
		try {
			val wordsList: MutableList<EntityWord> = emptyList<EntityWord>().toMutableList()
			list.forEach { elem ->
				wordsList.add(ResponseMapper.toStorage(elem, search))
			}
			getData().saveWord(wordsList.toList())
			Log.d("@@@", "Rep - ...Done")
		}
		catch (e: Exception) {
			Log.d("@@@", "Rep - ...Failed ${e.message}")
		}

	}

	override fun loadWords(target: String): RequestResults {
		return try {
			val response = getData().getWords(target)
			if (response.isEmpty()) RequestResults.Error(404, responseEmpty())
			else RequestResults.SuccessWords(responseFormation(response))
		}
		catch (error: Exception) {
			RequestResults.Error(-1, responseFail(error))
		}
	}

	private fun responseFormation(response: List<EntityWord>): ServersResult {
		val wordList: MutableList<DataWord> = emptyList<DataWord>().toMutableList()
		response.forEach { entity ->
			wordList.add(ResponseMapper.fromStorage(entity)
			)
		}
		return ServersResult(200, dataWord = wordList)
	}

	//endregion

	//region Data Favor

	override fun saveFavor(list: List<DataLine>) {
		try {
			val favorList: MutableList<EntityFavor> = emptyList<EntityFavor>().toMutableList()
			list.forEach { elem ->
				favorList.add(ResponseMapper.toStorage(elem))
			}
			getData().saveFavor(favorList.toList())
			Log.d("@@@", "Rep - ...Done")
		}
		catch (e: Exception) {
			Log.d("@@@", "Rep - ...Failed ${e.message}")
		}
	}

	override fun addFavorite(data: DataLine) {
		try {
			getData().addFavor(ResponseMapper.toStorage(data))
			Log.d("@@@", "Rep - ...Done")
		}
		catch (e: Exception) {
			Log.d("@@@", "Rep - ...Failed ${e.message}")
		}
	}

	override fun removeFavorite(data: DataLine) {
		try {
			//getData().reFavor(favorList.toList())
			Log.d("@@@", "Rep - ...Done")
		}
		catch (e: Exception) {
			Log.d("@@@", "Rep - ...Failed ${e.message}")
		}
	}

	override fun loadFavor(): List<DataLine> {
		return try {
			val response = getData().getFavor()
			if (response.isEmpty()) listOf<DataLine>()
			else responseFormation(response)
		}
		catch (error: Exception) {
			listOf<DataLine>()
		}
	}

	private fun responseFormation(response: List<EntityFavor>): List<DataLine> {
		val favorList: MutableList<DataLine> = emptyList<DataLine>().toMutableList()
		response.forEach { entity ->
			favorList.add(ResponseMapper.fromStorage(entity)
			)
		}
		return favorList.toList()
	}
//endregion

	private fun responseEmpty() = Throwable("Error code: 404, Data lost\n")
	private fun responseFail(error: Exception) = Throwable("Error code: -1, Internal storage unavailable:\n$error")
}