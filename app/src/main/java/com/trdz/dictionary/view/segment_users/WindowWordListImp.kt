package com.trdz.dictionary.view.segment_users

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.trdz.dictionary.MyApp
import com.trdz.dictionary.base_utility.hideKeyboard
import com.trdz.dictionary.databinding.FragmentNavigationBinding
import com.trdz.dictionary.model.DataWord
import com.trdz.dictionary.view_model.MainViewModel
import com.trdz.dictionary.view_model.StatusProcess
import com.trdz.dictionary.view_model.ViewModelFactory
import javax.inject.Inject


class WindowWordListImp: Fragment(), WindowWordListOnClick {

	//region Elements

	private var _binding: FragmentNavigationBinding? = null
	private val binding get() = _binding!!
	private val adapter = WindowWordListRecycle(this)
	//private val viewModel: MainViewModel by lazy  { ViewModelProvider(this).get(MainViewModel::class.java)}

	//endregion

	//region Injected

	@Inject
	lateinit var factory: ViewModelFactory

	override fun onAttach(context: Context) {
		MyApp.instance.appComponent.inject(this)
		super.onAttach(context)
	}

	private val viewModel: MainViewModel by viewModels {
		factory
	}


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
			}
		}
	}
	//endregion

	//region Instance

// Сособ сохранения состояния через VM

	private fun restore(bundle: Bundle?) {
		viewModel.getSaved()
	}

	private fun basicRestore(list: List<DataWord>) {
		//Не используется в методе востановления через VM
	}

/* Базовый способ сохранеия состояния через сохранение

	private var lastUsed: List<DataWord> = listOf()

	private fun basicRestore(list: List<DataWord>){
		lastUsed = list
	}

	private fun restore(bundle: Bundle?) {
		bundle?.getParcelableArray(KEY_BANDL)?.let { refresh(it.toList() as List<DataWord>) }
	}

	override fun onSaveInstanceState(outState: Bundle) {
		super.onSaveInstanceState(outState)
		binding.run {
			outState.putParcelableArray(KEY_BANDL, last_used.toTypedArray())
		}
	}

*/

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
			is StatusProcess.Change -> changeState(material.data, material.position, material.count)
		}
	}

	private fun errorCatch() {
		//("Not yet implemented")
	}

	private fun refresh(list: List<DataWord>) {
		basicRestore(list)
		adapter.setList(list)
	}

	private fun changeState(list: List<DataWord>, position: Int, count: Int) {
		basicRestore(list)
		adapter.stackControl(list, position, count)
	}

	private fun loadingState(state: Boolean) {
		if (state) binding.list.loadingLayout.visibility = View.VISIBLE
		else binding.list.loadingLayout.visibility = View.INVISIBLE
	}

	//endregion

	companion object {
		@JvmStatic
		fun newInstance() = WindowWordListImp()
	}
}