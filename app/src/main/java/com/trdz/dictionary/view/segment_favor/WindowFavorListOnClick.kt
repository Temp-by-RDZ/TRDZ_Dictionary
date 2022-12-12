package com.trdz.dictionary.view.segment_favor

import com.trdz.dictionary.model.data.DataLine

interface WindowFavorListOnClick {
	fun onItemClick(data: DataLine, position: Int)
	fun onItemMove(fromPosition: Int, toPosition: Int)
	fun onItemRemove(data: DataLine, position: Int)
}
