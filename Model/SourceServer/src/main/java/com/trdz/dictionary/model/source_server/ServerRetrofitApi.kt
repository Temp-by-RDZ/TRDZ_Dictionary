package com.trdz.dictionary.model.source_server

import com.trdz.dictionary.model.source_server.data_word.dto.WordsDTO
import com.trdz.dictionary.utility.PACKAGE_LIST
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ServerRetrofitApi {
	@GET(PACKAGE_LIST)
	fun getResponse(
		@Query("search") search: String,
	): Call<WordsDTO>
}


