package com.trdz.dictionary.model

interface InternalData {
	fun saveWords(list: List<DataWord>, search: String)
	fun saveFavor(list: List<DataLine>)
	fun loadFavor(): List<DataLine>
	fun addFavorite(data: DataLine)
	fun removeFavorite(data: DataLine)
	fun saveHistory(search: DataLine)
	fun loadHistory(): List<DataLine>
}