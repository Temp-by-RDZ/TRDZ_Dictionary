package com.trdz.dictionary.model

interface Repository {
	fun setSource(index: Int)
	fun checkLast(): String

	//Data Word
	suspend fun initWordList(target: String): com.trdz.dictionary.model.data.RequestResults
	suspend fun analyze(data: List<com.trdz.dictionary.model.data.DataWord>): List<com.trdz.dictionary.model.data.DataWord>
	fun update(currentData: List<com.trdz.dictionary.model.data.DataWord>, target: String)

	//Data Favor
	suspend fun initFavorList(): List<com.trdz.dictionary.model.data.DataLine>
	fun update(list: List<com.trdz.dictionary.model.data.DataLine>)
	fun addFavorite(data: com.trdz.dictionary.model.data.DataLine)
	fun removeFavorite(data: com.trdz.dictionary.model.data.DataLine)

	//Data Search
	suspend fun initSearchList(): List<com.trdz.dictionary.model.data.DataLine>
	fun update(target: String)
}