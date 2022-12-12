package com.trdz.dictionary.view.segment_favor

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import com.trdz.dictionary.R
import com.trdz.dictionary.databinding.FragmentFavorListBinding
import com.trdz.dictionary.model.data.DataLine
import com.trdz.dictionary.utility.EFFECT_DROP
import com.trdz.dictionary.utility.EFFECT_MOVEL
import com.trdz.dictionary.utility.EFFECT_SHOW
import com.trdz.dictionary.view.CustomOnBackPressed
import com.trdz.dictionary.view.Navigation
import com.trdz.dictionary.view.segment_history.WindowHistoryListImpl
import com.trdz.dictionary.view.segment_word.WindowWordListImp
import com.trdz.dictionary.view_model.FavorViewModel
import com.trdz.dictionary.view_model.ViewModelFactories
import org.koin.android.ext.android.inject

class WindowFavorListImpl: Fragment(), WindowFavorListOnClick, CustomOnBackPressed {

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

	override fun onBackPressed(): Boolean {
		navigation.replace(requireActivity().supportFragmentManager, WindowWordListImp(), false, effect = EFFECT_SHOW)
		return true
	}

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
		setMenu()
	}

	//endregion

	//region Main functional
	private fun setMenu() {
		setHasOptionsMenu(true)
	}

	override fun onPrepareOptionsMenu(menu: Menu) {
		val curr = menu.findItem(R.id.app_bar_fav)
		curr.setIcon(R.drawable.ic_baseline_search_24)
		val dell = menu.findItem(R.id.app_bar_delete)
		dell.isVisible = true
		super.onPrepareOptionsMenu(menu)
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		when (item.itemId) {
			R.id.app_bar_fav -> {
				navigation.replace(requireActivity().supportFragmentManager, WindowWordListImp(), false, effect = EFFECT_SHOW)
			}
			R.id.app_bar_history -> {
				navigation.replace(requireActivity().supportFragmentManager, WindowHistoryListImpl(), false, effect = EFFECT_MOVEL)
			}
		}
		return super.onOptionsItemSelected(item)
	}

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

	override fun onItemRemove(data: DataLine, position: Int) {
		adapter.setRemoveFromList(viewModel.itemRemove(data, position), position)
	}

	override fun onItemClick(data: DataLine, position: Int) {
		navigation.replace(requireActivity().supportFragmentManager, WindowWordListImp.newInstance(data.name), false, effect = EFFECT_DROP)
	}

	//endregion

	companion object {
		fun newInstance() = WindowFavorListImpl()
	}
}