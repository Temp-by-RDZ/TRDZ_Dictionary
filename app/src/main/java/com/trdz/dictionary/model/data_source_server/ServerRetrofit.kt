package com.trdz.dictionary.model.data_source_server

import com.trdz.dictionary.MyApp
import com.trdz.dictionary.model.ADataSource
import com.trdz.dictionary.model.DataWord
import com.trdz.dictionary.model.RequestResults
import com.trdz.dictionary.model.ServersResult
import com.trdz.dictionary.model.data_source_server.data_word.dto.WordsDTO
import retrofit2.Response

class ServerRetrofit: ADataSource {

	override fun loadWords(target: String): RequestResults {
		val retrofit = MyApp.di.get(ServerRetrofitApi::class)
		return try {
			val response = retrofit.getResponse(target).execute()
			RequestResults.Success(responseFormation(response))
		}
		catch (error: Exception) {
			RequestResults.Error(-1,responseFail(error))
		}
	}

	private fun responseFormation(response: Response<WordsDTO>): ServersResult {
		return if (response.isSuccessful) response.body()!!.run {
			val wordList: MutableList<DataWord> = emptyList<DataWord>().toMutableList()
			forEachIndexed { index, user ->
				val group = index * 2
				wordList.add(ResponseMapper.mapToEntity(user, group))
				user.meanings.forEach { meaning ->
					wordList.add(ResponseMapper.mapToEntity(meaning, group + 1))
				}

			}
			ServersResult(response.code(), dataWord = wordList)
		}
		else ServersResult(response.code())
	}

	private fun responseFail(error: Exception) = Throwable("Error code: -1, Internet connection lost or current data available:\n$error")
}
