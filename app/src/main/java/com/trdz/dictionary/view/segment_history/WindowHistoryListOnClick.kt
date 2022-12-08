package com.trdz.dictionary.view.segment_history

import com.trdz.dictionary.model.DataLine

interface WindowHistoryListOnClick {
	fun onItemClick(data: DataLine, position: Int)
}
