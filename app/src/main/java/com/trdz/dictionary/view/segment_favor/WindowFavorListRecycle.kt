package com.trdz.dictionary.view.segment_favor

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.trdz.dictionary.R
import com.trdz.dictionary.databinding.ElementLineBinding
import com.trdz.dictionary.model.DataLine

class WindowFavorListRecycle(private val clickExecutor: WindowFavorListOnClick): RecyclerView.Adapter<WindowFavorListRecycle.NoteLine?>(), WindowNoteOnTouch {

	private var list: List<DataLine> = emptyList()

	@SuppressLint("NotifyDataSetChanged")
	fun setList(newList: List<DataLine>) {
		this.list = newList
		notifyDataSetChanged()
	}

	fun setChangeInList(newList: List<DataLine>) {
		val result = DiffUtil.calculateDiff(DiffUtilCallback(list, newList))
		result.dispatchUpdatesTo(this)
		this.list = newList
	}

	fun setAddToList(newList: List<DataLine>, position: Int) {
		this.list = newList
		notifyItemChanged(position)
	}

	fun setRemoveFromList(newList: List<DataLine>, position: Int) {
		this.list = newList
		notifyItemRemoved(position)
	}

	fun setMoveInList(newList: List<DataLine>, fromPosition: Int, toPosition: Int) {
		this.list = newList
		notifyItemMoved(fromPosition, toPosition)
	}

	override fun getItemCount(): Int {
		return list.size
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteLine {
		val view = ElementLineBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return NoteLine(view.root)
	}

	override fun onBindViewHolder(
		holder: NoteLine,
		position: Int,
		payloads: MutableList<Any>,
	) {
		if (payloads.isEmpty()) {
			super.onBindViewHolder(holder, position, payloads)
		}
		else {
			val res = createCombinedPayload(payloads as List<Change<DataLine>>)
			if (res.oldData.name != res.newData.name)
				holder.itemView.findViewById<TextView>(R.id.l_name).text = res.newData.name

		}
	}

	override fun onBindViewHolder(holder: NoteLine, position: Int) {
		holder.bind(list[position])
	}

	inner class NoteLine(view: View): RecyclerView.ViewHolder(view), WindowNoteOnTouchHelp {
		fun bind(data: DataLine) {
			(ElementLineBinding.bind(itemView)).apply {
				lName.text = data.name
				root.setOnClickListener { clickExecutor.onItemClick(data, layoutPosition) }
			}
		}

		override fun onItemSelected() {
			itemView.setBackgroundColor(Color.LTGRAY)
		}

		override fun onItemClear() {
			itemView.setBackgroundColor(Color.WHITE)
		}
	}

	override fun onItemMove(fromPosition: Int, toPosition: Int) {
		clickExecutor.onItemMove(fromPosition, toPosition)
	}

	override fun onItemDismiss(position: Int) {
		clickExecutor.onItemRemove(position)
	}

}