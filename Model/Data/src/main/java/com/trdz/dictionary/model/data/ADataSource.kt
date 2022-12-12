package com.trdz.dictionary.model.data

interface ADataSource {
	fun loadWords(target: String): RequestResults
}