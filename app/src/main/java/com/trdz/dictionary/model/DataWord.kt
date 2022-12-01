package com.trdz.dictionary.model

import android.os.Parcelable
import com.trdz.dictionary.base_utility.TYPE_TITLE
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataWord(
	val name: String = "Name",
	val subName: String = "Subname",
	val id: Int,
	val iconUrl: String,
	val type: Int = TYPE_TITLE,
	val group: Int = 0,
	var state: Int = 0,
): Parcelable