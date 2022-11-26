package com.trdz.dictionary.model.data_source_room

import com.trdz.dictionary.MyApp
import com.trdz.dictionary.model.*
import com.trdz.dictionary.model.data_source_room.database.WordDao
import com.trdz.dictionary.model.data_source_room.database.WordEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import java.lang.Exception
import kotlin.concurrent.thread

class InternalStorage: ADataSource, InternalData {

	private fun getData(): WordDao {
		return MyApp.di.get(WordDao::class)
	}

	override fun saveUsers(words: List<DataWord>): Completable = Completable.create {
		thread {
			try {
				val wordList: MutableList<WordEntity> = emptyList<WordEntity>().toMutableList()
				words.forEach { user ->
					wordList.add(ResponseMapper.toStorage(user))
				}
				getData().insertAll(wordList.toList())
				it.onComplete()
			}
			catch (e: Exception) {
				it.onError(Throwable(e.message))
			}
		}.start()

	}

	override fun loadWords(target: String): Single<ServersResult> = Single.create {
		try {
			val response = getData().getUsers()
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