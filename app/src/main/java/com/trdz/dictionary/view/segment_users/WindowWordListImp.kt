package com.trdz.dictionary.view.segment_users

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.trdz.dictionary.base_utility.KEY_BANDL
import com.trdz.dictionary.base_utility.hideKeyboard
import com.trdz.dictionary.databinding.FragmentNavigationBinding
import com.trdz.dictionary.model.DataWord
import com.trdz.dictionary.view_model.MainViewModel
import com.trdz.dictionary.view_model.StatusProcess


class WindowWordListImp: Fragment(), WindowWordListOnClick {

	//region Elements

	private var _binding: FragmentNavigationBinding? = null
	private val binding get() = _binding!!
	private val adapter = WindowWordListRecycle(this)
	private val viewModel: MainViewModel by lazy  { ViewModelProvider(this).get(MainViewModel::class.java)}
	private var last_used: List<DataWord> = listOf()

	//endregion

	//region Base realization

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		_binding = FragmentNavigationBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		vmSetup()
		bindings()
		restore(savedInstanceState)
	}

	private fun vmSetup() {
		val observer = Observer<StatusProcess> { renderData(it) }
		viewModel.getPodData().observe(viewLifecycleOwner, observer)
	}

	private fun bindings() {
		with(binding) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
				list.recyclerView.setOnScrollChangeListener { _, _, _, _, _ ->
					list.naming.isSelected = binding.list.recyclerView.canScrollVertically(-1)
				}
			}
			list.recyclerView.adapter = adapter
			target.requestFocus()
			floatButton.setOnClickListener {
				val text = binding.target.text.toString()
				if ((text != "") && (text != " ")) {
					viewModel.startSearch(text)
					hideKeyboard()
				}
				else {
					binding.target.requestFocus()
				}
			}}
	}
	private fun restore(bundle: Bundle?) {
		bundle?.getParcelableArray(KEY_BANDL)?.let { refresh(it.toList() as List<DataWord>) }
	}

	//endregion

	//region Adapter realization

	override fun onItemClickSpecial(data: DataWord, position: Int) {
		viewModel.visualChange(data, position)
	}

	//endregion

	//region ViewModel command realization

	private fun renderData(material: StatusProcess) {
		when (material) {
			StatusProcess.Loading -> loadingState(true)
			is StatusProcess.Error -> errorCatch()
			is StatusProcess.Success -> {
				refresh(material.data.data)
				loadingState(material.data.loadState)
			}
			is StatusProcess.Change -> changeState(material.data,material.position,material.count)
		}
	}

	private fun errorCatch() {
		//("Not yet implemented")
	}

	private fun refresh(list: List<DataWord>) {
		last_used = list
		adapter.setList(list)
	}

	private fun changeState(list: List<DataWord>, position: Int, count: Int) {
		last_used = list
		adapter.stackControl(list, position, count)
	}

	private fun loadingState(state: Boolean) {
		binding.list.loadingLayout.visibility = if (state) View.VISIBLE
		else View.GONE
	}

	override fun onSaveInstanceState(outState: Bundle) {
		super.onSaveInstanceState(outState)
		binding.run {
			outState.putParcelableArray(KEY_BANDL, last_used.toTypedArray())
		}
	}

	//endregion

	companion object {
		@JvmStatic
		fun newInstance() = WindowWordListImp()
	}
}