package com.trdz.dictionary.view.segment_word

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.ImageRequest
import com.trdz.dictionary.R
import com.trdz.dictionary.base_utility.TYPE_CARD
import com.trdz.dictionary.base_utility.TYPE_NONE
import com.trdz.dictionary.base_utility.TYPE_TITLE
import com.trdz.dictionary.databinding.ElementCardBinding
import com.trdz.dictionary.databinding.ElementHiderBinding
import com.trdz.dictionary.databinding.ElementLiderBinding
import com.trdz.dictionary.model.DataWord

class WindowWordListRecycle(private val clickExecutor: WindowWordListOnClick): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

	private var list: List<DataWord> = emptyList()

	fun stackControl(newList: List<DataWord>, first: Int, count: Int) {
		this.list = newList
		notifyItemRangeChanged(first, count)
	}

	@SuppressLint("NotifyDataSetChanged")
	fun setList(newList: List<DataWord>) {
		this.list = newList
		notifyDataSetChanged()
	}

	override fun getItemViewType(position: Int): Int {
		return when (getItemViewState(position)) {
			2 -> TYPE_NONE
			else -> list[position].visual.type
		}
	}

	private fun getItemViewState(position: Int): Int {
		return list[position].visual.state
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
		return when (viewType) {
			TYPE_CARD -> {
				val view = ElementCardBinding.inflate(LayoutInflater.from(parent.context))
				Element(view.root)
			}
			TYPE_TITLE -> {
				val view = ElementLiderBinding.inflate(LayoutInflater.from(parent.context))
				ElementLider(view.root)
			}
			else -> {
				val view = ElementHiderBinding.inflate(LayoutInflater.from(parent.context))
				ElementNone(view.root)
			}
		}

	}

	override fun onBindViewHolder(
		holder: RecyclerView.ViewHolder,
		position: Int,
		payloads: MutableList<Any>,
	) {
		if (payloads.isEmpty()) {
			super.onBindViewHolder(holder, position, payloads)
		}
	}

	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
		(holder as ListElement).myBind(list[position])
	}

	override fun getItemCount(): Int {
		return list.size
	}

	abstract inner class ListElement(view: View): RecyclerView.ViewHolder(view) {
		abstract fun myBind(data: DataWord)
	}

	inner class Element(view: View): ListElement(view) {

		private fun subExpand(data: DataWord) {
			(ElementCardBinding.bind(itemView)).apply {
				if (data.visual.expand) {
					secondBox.visibility = View.VISIBLE
					pic.setBackgroundResource(R.drawable.plaseholder)
					pic.load("https:" + data.iconUrl) {
						listener(
							onSuccess = { _, _ ->
								Log.d("@@@", "App - Load comp")
								// do nothing
							},
							onError = { request: ImageRequest, throwable: Throwable ->
								Log.d("@@@", "App - coil error $throwable")
								pic.setBackgroundResource(R.drawable.nofile)
							})
					}
				}
				else {
					secondBox.visibility = View.GONE
				}
			}
		}

		override fun myBind(data: DataWord) {
			(ElementCardBinding.bind(itemView)).apply {
				subExpand(data)
				root.setOnClickListener {
					data.visual.expand = !data.visual.expand
					subExpand(data)
				}
				val sb = StringBuilder(data.name)
				if (data.subName != "") {
					sb.append(" (")
					sb.append(data.subName)
					sb.append(")")
				}
				main.text = sb
			}
		}
	}

	inner class ElementLider(view: View): ListElement(view) {

		private fun subExpand(data: DataWord) {
			(ElementLiderBinding.bind(itemView)).apply {
				if (data.visual.expand) {
					fav.setBackgroundResource(R.drawable.ic_favorite_active)
				}
				else {
					fav.setBackgroundResource(R.drawable.ic_favourite)
				}
			}
		}

		override fun myBind(data: DataWord) {
			(ElementLiderBinding.bind(itemView)).apply {
				subExpand(data)
				if (data.visual.state == 1) ObjectAnimator.ofFloat(blockImage, View.ROTATION, -90f, 0f).setDuration(250).start()
				title.text = data.name
				trancript.text = data.subName
				root.setOnClickListener {
					if (data.visual.state == 1) ObjectAnimator.ofFloat(blockImage, View.ROTATION, 0f, -90f).setDuration(500).start()
					else ObjectAnimator.ofFloat(blockImage, View.ROTATION, -90f, 0f).setDuration(500).start()
					clickExecutor.onItemClickSpecial(data, layoutPosition)
				}
				favorZone.setOnClickListener {
					data.visual.expand = !data.visual.expand
					subExpand(data)
					clickExecutor.onItemClick(data, layoutPosition, data.visual.expand)
				}
			}
		}
	}

	inner class ElementNone(view: View): ListElement(view) {
		override fun myBind(data: DataWord) {
		}
	}
}