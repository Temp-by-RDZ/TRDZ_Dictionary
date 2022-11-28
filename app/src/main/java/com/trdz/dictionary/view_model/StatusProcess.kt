package com.trdz.dictionary.view_model

import com.trdz.dictionary.model.DataWord

sealed class StatusProcess {
	//Базовые команды
	object Loading : StatusProcess()
	data class Success(val data: ModelResult) : StatusProcess()
	data class Error(val code: Int, val error: Throwable) : StatusProcess()
	//Специфические команды
	data class Change(val data: List<DataWord>, val position: Int, val count: Int) : StatusProcess()
}

data class ModelResult(
	val data: List<DataWord>,
	val loadState: Boolean = false,
)