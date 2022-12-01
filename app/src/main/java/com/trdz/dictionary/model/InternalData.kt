package com.trdz.dictionary.model

interface InternalData {
	fun saveWords(words: List<DataWord>, search: String)
}