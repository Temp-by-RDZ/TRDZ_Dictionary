package com.trdz.dictionary.model.data_source_room

import android.util.Log
import com.trdz.dictionary.MyApp
import com.trdz.dictionary.model.*
import com.trdz.dictionary.model.data_source_room.database.WordDao
import com.trdz.dictionary.model.data_source_room.database.WordEntity

class InternalStorage: ADataSource, InternalData {

	private fun getData(): WordDao {
		return MyApp.di.get(WordDao::class)
	}

	override fun saveWords(words: List<DataWord>, search: String) {
		try {
			val wordsList: MutableList<WordEntity> = emptyList<WordEntity>().toMutableList()
			words.forEach { user ->
				wordsList.add(ResponseMapper.toStorage(user, search))
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
			if (response.isEmpty()) RequestResults.Error(404,responseEmpty())
			else RequestResults.Success(responseFormation(response))
		}
		catch (error: Exception) {
			RequestResults.Error(-1,responseFail(error))
		}
	}

	private fun responseFormation(response: List<WordEntity>): ServersResult {
		val wordList: MutableList<DataWord> = emptyList<DataWord>().toMutableList()
		response.forEach { entity ->
			wordList.add(ResponseMapper.fromStorage(entity)
			)
		}
		return ServersResult(200, dataWord = wordList)
	}

	private fun responseEmpty() = Throwable("Error code: 404, Data lost\n")
	private fun responseFail(error: Exception) = Throwable("Error code: -1, Internal storage unavailable:\n$error")
}