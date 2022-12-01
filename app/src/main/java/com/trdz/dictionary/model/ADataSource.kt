package com.trdz.dictionary.model

interface ADataSource {
	fun loadWords(target: String): RequestResults
}