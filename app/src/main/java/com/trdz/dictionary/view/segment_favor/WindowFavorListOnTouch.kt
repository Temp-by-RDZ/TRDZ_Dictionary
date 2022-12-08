package com.trdz.dictionary.view.segment_favor

interface WindowNoteOnTouch {
	fun onItemMove(fromPosition: Int, toPosition: Int)
	fun onItemDismiss(position: Int)
}

interface WindowNoteOnTouchHelp {
	fun onItemSelected()
	fun onItemClear()
}