package com.trdz.dictionary.view.segment_favor

import androidx.recyclerview.widget.DiffUtil
import com.trdz.dictionary.model.DataLine

class DiffUtilCallback(
	private val oldList: List<DataLine>,
	private val newList: List<DataLine>,
): DiffUtil.Callback() {
	override fun getOldListSize() = oldList.size

	override fun getNewListSize() = newList.size


	override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
		return oldList[oldItemPosition].id == newList[newItemPosition].id
	}

	override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
		return (oldList[oldItemPosition].name == newList[newItemPosition].name)
	}

	override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any {
		val oldItem = oldList[oldItemPosition]
		val newItem = newList[newItemPosition]
		return Change(oldItem, newItem)
	}
}