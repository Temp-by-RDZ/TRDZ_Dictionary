package com.trdz.dictionary.model

import io.reactivex.rxjava3.core.Completable

interface InternalData {
	fun saveWords(words: List<DataWord>, search: String): Completable
}