package com.trdz.dictionary.view.segment_history

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.trdz.dictionary.R
import com.trdz.dictionary.databinding.FragmentFavorListBinding
import com.trdz.dictionary.model.data.DataLine
import com.trdz.dictionary.utility.*
import com.trdz.dictionary.view.CustomOnBackPressed
import com.trdz.dictionary.view.MenuController
import com.trdz.dictionary.view.Navigation
import com.trdz.dictionary.view.segment_favor.WindowFavorListImpl
import com.trdz.dictionary.view.segment_word.WindowWordListImp
import com.trdz.dictionary.view_model.HistoryViewModel
import com.trdz.dictionary.view_model.ViewModelFactories
import org.koin.android.ext.android.inject

class WindowHistoryListImpl: Fragment(), WindowHistoryListOnClick, CustomOnBackPressed {

	//region Elements

	private var _binding: FragmentFavorListBinding? = null
	private val binding get() = _binding!!
	private val adapter = WindowHistoryListRecycle(this)

	//endregion

	//region Injected

	private val navigation: Navigation by inject()
	private val factory: ViewModelFactories by inject()

	private val viewModel: HistoryViewModel by viewModels {
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

	private fun vmSetup() {
		val observer = Observer<List<DataLine>> { initialize(it) }
		viewModel.getData().observe(viewLifecycleOwner, observer)
	}

	private fun bindings() {
		binding.naming.text = getString(R.string.history)
		binding.recyclerView.adapter = adapter
	}

	private fun setMenu() {
		setHasOptionsMenu(true)
	}

	override fun onPrepareOptionsMenu(menu: Menu) {
		MenuController().apply {
			itemDeactivate(menu, MENU_HISTORY)
			itemActive(menu, MENU_FAVOR, ICON_FAVOR)
			subItem(menu,true)
		}
		super.onPrepareOptionsMenu(menu)
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		when (item.itemId) {
			R.id.app_bar_fav -> {
				navigation.replace(requireActivity().supportFragmentManager, WindowFavorListImpl(), false, effect = EFFECT_MOVER)
			}
			R.id.app_bar_history -> {
				onBackPressed()
			}
		}
		return super.onOptionsItemSelected(item)
	}

	private fun initialize(list: List<DataLine>) {
		adapter.setList(list)
	}

	//endregion

	//region Realization adapter

	override fun onItemClick(data: DataLine, position: Int) {
		navigation.replace(requireActivity().supportFragmentManager, WindowWordListImp.newInstance(data.name), false, effect = EFFECT_DROP)
	}

	//endregion

	companion object {
		fun newInstance() = WindowHistoryListImpl()
	}

}