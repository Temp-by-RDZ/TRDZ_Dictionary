package com.trdz.dictionary.model.source_server.data_word.dto


import com.google.gson.annotations.SerializedName

data class WordsDTOItem(
	@SerializedName("id")
	val id: Int,
	@SerializedName("meanings")
	val meanings: List<Meaning>,
	@SerializedName("text")
	val text: String,
)