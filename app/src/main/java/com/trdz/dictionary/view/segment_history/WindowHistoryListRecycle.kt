package com.trdz.dictionary.view.segment_history

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.trdz.dictionary.databinding.ElementLineBinding
import com.trdz.dictionary.model.data.DataLine

class WindowHistoryListRecycle(private val clickExecutor: WindowHistoryListOnClick): RecyclerView.Adapter<WindowHistoryListRecycle.NoteLine?>() {

	private var list: List<DataLine> = emptyList()

	@SuppressLint("NotifyDataSetChanged")
	fun setList(newList: List<DataLine>) {
		this.list = newList
		notifyDataSetChanged()
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
	}

	override fun onBindViewHolder(holder: NoteLine, position: Int) {
		holder.bind(list[position])
	}

	inner class NoteLine(view: View): RecyclerView.ViewHolder(view) {
		fun bind(data: DataLine) {
			(ElementLineBinding.bind(itemView)).apply {
				lName.text = data.name
				root.setOnClickListener { clickExecutor.onItemClick(data, layoutPosition) }
			}
		}
	}

}