package com.trdz.dictionary.model

import com.trdz.dictionary.base_utility.TYPE_TITLE

data class DataWord(
	val name: String = "Name",
	val subName: String = "Subname",
	val id: Int,
	val iconUrl: String,
	val visual: VisualState = VisualState(),
)

data class VisualState(
	val type: Int = TYPE_TITLE,
	val group: Int = 0,
	var state: Int = 0,
	var expand: Boolean = false,
)