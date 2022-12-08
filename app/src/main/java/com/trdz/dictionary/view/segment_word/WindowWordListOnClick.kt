package com.trdz.dictionary.view.segment_word

import com.trdz.dictionary.model.DataWord

interface WindowWordListOnClick {
	fun onItemClick(data: DataWord, position: Int, type: Boolean)
	fun onItemClickSpecial(data: DataWord, position: Int)
}
