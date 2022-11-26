package com.trdz.dictionary.model.data_source_server

import com.trdz.dictionary.base_utility.PACKAGE_LIST
import com.trdz.dictionary.model.data_source_server.data_word.dto.WordsDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ServerRetrofitApi {
	@GET(PACKAGE_LIST)
	fun getResponse(
		@Query("search") search: String,
	): Call<WordsDTO>
}


