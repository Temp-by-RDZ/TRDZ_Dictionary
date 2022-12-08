package com.trdz.dictionary.model

interface Repository {
	fun setSource(index: Int)
	//Data Word
	suspend fun initWordList(target: String): RequestResults
	fun update(currentData: List<DataWord>,target: String)
	//Data Favor
	suspend fun initFavorList(): List<DataLine>
	fun addFavorite(data: DataLine)
	fun removeFavorite(data: DataLine)
	//Data Search
}