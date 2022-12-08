package com.trdz.dictionary.view.segment_favor

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import com.trdz.dictionary.base_utility.EFFECT_DROP
import com.trdz.dictionary.databinding.FragmentFavorListBinding
import com.trdz.dictionary.model.DataLine
import com.trdz.dictionary.view.Navigation
import com.trdz.dictionary.view.segment_word.WindowWordListImp
import com.trdz.dictionary.view_model.FavorViewModel
import com.trdz.dictionary.view_model.ViewModelFactories
import org.koin.android.ext.android.inject
import java.util.ArrayList

class WindowFavorListImpl: Fragment(), WindowFavorListOnClick {

	//region Elements

	private var _binding: FragmentFavorListBinding? = null
	private val binding get() = _binding!!
	private val adapter = WindowFavorListRecycle(this)

	//endregion

	//region Injected

	private val navigation: Navigation by inject()
	private val factory: ViewModelFactories by inject()

	private val viewModel: FavorViewModel by viewModels {
		factory
	}

	//endregion

	//region Base realization

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		_binding = FragmentFavorListBinding.inflate(inflater, container, false)
		ItemTouchHelper(WindowFavorTouch(adapter)).attachToRecyclerView(binding.recyclerView)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		vmSetup()
		bindings()
	}

	//endregion

	//region Main functional

	private fun vmSetup() {
		val observer = Observer<List<DataLine>> { initialize(it) }
		viewModel.getData().observe(viewLifecycleOwner, observer)
	}

	private fun bindings() {
		binding.recyclerView.adapter = adapter
	}

	private fun initialize(list: List<DataLine>) {
		adapter.setList(list)
	}

	//endregion

	//region Realization adapter/touch

	override fun onItemMove(fromPosition: Int, toPosition: Int) {
		adapter.setMoveInList(viewModel.itemMove(fromPosition, toPosition), fromPosition, toPosition)
	}

	override fun onItemRemove(position: Int) {
		adapter.setRemoveFromList(viewModel.itemRemove(position), position)
	}

	override fun onItemClick(data: DataLine, position: Int) {
		navigation.replace(requireActivity().supportFragmentManager, WindowWordListImp.newInstance(data.name), false, effect = EFFECT_DROP)
	}

	//endregion

	companion object {
		fun newInstance() = WindowFavorListImpl()
	}
}