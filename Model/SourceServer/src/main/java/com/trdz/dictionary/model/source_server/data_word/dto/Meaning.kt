package com.trdz.dictionary.model.source_server.data_word.dto


import com.google.gson.annotations.SerializedName

data class Meaning(
	@SerializedName("id")
	val id: Int,
	@SerializedName("imageUrl")
	val imageUrl: String,
	@SerializedName("partOfSpeechCode")
	val partOfSpeechCode: String,
	@SerializedName("previewUrl")
	val previewUrl: String,
	@SerializedName("soundUrl")
	val soundUrl: String,
	@SerializedName("transcription")
	val transcription: String,
	@SerializedName("translation")
	val translation: Translation,
)