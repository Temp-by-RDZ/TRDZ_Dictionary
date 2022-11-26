package com.trdz.dictionary.model.data_source_server.data_word.dto


import com.google.gson.annotations.SerializedName

data class Translation(
	@SerializedName("note")
	val note: String,
	@SerializedName("text")
	val text: String,
)