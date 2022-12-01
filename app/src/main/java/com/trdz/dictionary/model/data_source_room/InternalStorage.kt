package com.trdz.dictionary.model.data_source_room

import com.trdz.dictionary.MyApp
import com.trdz.dictionary.model.ADataSource
import com.trdz.dictionary.model.DataWord
import com.trdz.dictionary.model.InternalData
import com.trdz.dictionary.model.ServersResult
import com.trdz.dictionary.model.data_source_room.database.WordDao
import com.trdz.dictionary.model.data_source_room.database.WordEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class InternalStorage: ADataSource, InternalData {

	private fun getData(): WordDao {
		return MyApp.di.get(WordDao::class)
	}

	override fun saveWords(words: List<DataWord>, search: String): Completable = Completable.create {
		try {
			val wordsList: MutableList<WordEntity> = emptyList<WordEntity>().toMutableList()
			words.forEach { user ->
				wordsList.add(ResponseMapper.toStorage(user, search))
			}
			getData().saveWord(wordsList.toList())
			it.onComplete()
		}
		catch (e: Exception) {
			it.onError(Throwable(e.message))
		}

	}

	override fun loadWords(target: String): Single<ServersResult> = Single.create {
		try {
			val response = getData().getWords(target)
			if (response.isEmpty()) it.onError(responseEmpty())
			else it.onSuccess(responseFormation(response))
		}
		catch (error: Exception) {
			it.onError(responseFail(error))
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