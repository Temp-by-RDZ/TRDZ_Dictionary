package com.trdz.dictionary.view.segment_favor

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import com.trdz.dictionary.R
import com.trdz.dictionary.databinding.FragmentMemoryBinding
import com.trdz.dictionary.model.data.DataLine
import com.trdz.dictionary.utility.*
import com.trdz.dictionary.view.CustomOnBackPressed
import com.trdz.dictionary.view.MenuController
import com.trdz.dictionary.view.Navigation
import com.trdz.dictionary.view.segment_history.WindowHistoryListImpl
import com.trdz.dictionary.view.segment_word.WindowWordListImp
import com.trdz.dictionary.view_model.FavorViewModel
import com.trdz.dictionary.view_model.ViewModelFactories
import org.koin.android.ext.android.inject

class WindowFavorListImpl: Fragment(), WindowFavorListOnClick, CustomOnBackPressed {

	//region Elements

	private var _binding: FragmentMemoryBinding? = null
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
		_binding = FragmentMemoryBinding.inflate(inflater, container, false)
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
		MenuController().apply {
			itemDeactivate(menu, MENU_FAVOR)
			itemActive(menu, MENU_HISTORY, ICON_HISTORY)
			subItem(menu,true)
		}
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
		with(binding) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
				recyclerView.setOnScrollChangeListener { _, _, _, _, _ ->
					naming.isSelected = binding.recyclerView.canScrollVertically(-1)
				}
			}
			recyclerView.adapter = adapter
		}
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