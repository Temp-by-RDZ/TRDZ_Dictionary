package com.trdz.dictionary.view.segment_users

import com.trdz.dictionary.model.DataWord

interface WindowWordListOnClick {
	fun onItemClickSpecial(data: DataWord, position: Int)
}
