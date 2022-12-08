package com.trdz.dictionary.view.segment_favor

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class WindowFavorTouch(private val itemTouchHelperAdapter: WindowNoteOnTouch): ItemTouchHelper.Callback() {

	override fun getMovementFlags(
		recyclerView: RecyclerView,
		viewHolder: RecyclerView.ViewHolder,
	): Int {
		val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
		val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
		return makeMovementFlags(dragFlags, swipeFlags)
	}

	override fun onMove(
		recyclerView: RecyclerView,
		source: RecyclerView.ViewHolder,
		target: RecyclerView.ViewHolder,
	): Boolean {
		itemTouchHelperAdapter.onItemMove(source.adapterPosition, target.adapterPosition)
		return true
	}

	override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
		itemTouchHelperAdapter.onItemDismiss(viewHolder.adapterPosition)
	}

	override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
		if (viewHolder is WindowFavorListRecycle.NoteLine) {
			(viewHolder as WindowFavorListRecycle.NoteLine).onItemSelected()
		}
		super.onSelectedChanged(viewHolder, actionState)
	}

	override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
		if (viewHolder is WindowFavorListRecycle.NoteLine)
			(viewHolder as WindowFavorListRecycle.NoteLine).onItemClear()
		super.clearView(recyclerView, viewHolder)
	}

}