package com.trdz.dictionary.model

import com.trdz.dictionary.model.data.DataLine
import com.trdz.dictionary.model.data.DataWord
import com.trdz.dictionary.model.data.RequestResults

interface Repository {
	fun setSource(index: Int)
	fun checkLast(): String

	//Data Word
	suspend fun initWordList(target: String): RequestResults
	suspend fun analyze(data: List<DataWord>): List<DataWord>
	fun update(currentData: List<DataWord>, target: String)

	//Data Favor
	suspend fun initFavorList(): List<DataLine>
	fun update(list: List<DataLine>)
	fun addFavorite(data: DataLine)
	fun removeFavorite(data: DataLine)

	//Data Search
	suspend fun initSearchList(): List<DataLine>
	fun update(target: String)
}