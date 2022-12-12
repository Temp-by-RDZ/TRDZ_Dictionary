package com.trdz.dictionary.view.segment_word

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.trdz.dictionary.R
import com.trdz.dictionary.databinding.FragmentNavigationBinding
import com.trdz.dictionary.model.data.DataWord
import com.trdz.dictionary.utility.BUNDLE_SEARCH
import com.trdz.dictionary.utility.EFFECT_DROP
import com.trdz.dictionary.utility.hideKeyboard
import com.trdz.dictionary.view.Navigation
import com.trdz.dictionary.view.segment_favor.WindowFavorListImpl
import com.trdz.dictionary.view.segment_history.WindowHistoryListImpl
import com.trdz.dictionary.view_model.StatusProcess
import com.trdz.dictionary.view_model.ViewModelFactories
import com.trdz.dictionary.view_model.WordsViewModel
import org.koin.android.ext.android.inject


class WindowWordListImp: Fragment(), WindowWordListOnClick {

	//region Elements

	private var _binding: FragmentNavigationBinding? = null
	private val binding get() = _binding!!
	private val adapter = WindowWordListRecycle(this)

	//endregion

	//region Injected

	private val navigation: Navigation by inject()
	private val factory: ViewModelFactories by inject()

	private val viewModel: WordsViewModel by viewModels {
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
		setMenu()
		bindings()
		restore()
	}

	private fun vmSetup() {
		val observer = Observer<StatusProcess> { renderData(it) }
		viewModel.getData().observe(viewLifecycleOwner, observer)
	}

	private fun bindings() {
		with(binding) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
				list.recyclerView.setOnScrollChangeListener { _, _, _, _, _ ->
					list.naming.isSelected = binding.list.recyclerView.canScrollVertically(-1)
				}
			}
			list.recyclerView.adapter = adapter
			disabler.setOnClickListener { toViewMode() }
			enabler.setOnClickListener { toKeyboardMode() }
			target.isIconified = false
			target.setOnCloseListener { true }
			target.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
				override fun onQueryTextSubmit(query: String?): Boolean {
					query?.let { viewModel.setSearch(it) }
					return true
				}

				override fun onQueryTextChange(newText: String): Boolean {
					viewModel.setSearch(newText)
					return true
				}
			})
		}
	}

	//endregion

	//region Menu realization

	private fun setMenu() {
		setHasOptionsMenu(true)
	}

	override fun onPrepareOptionsMenu(menu: Menu) {
		val history = menu.findItem(R.id.app_bar_history)
		history.setIcon(R.drawable.ic_baseline_books)
		val fav = menu.findItem(R.id.app_bar_fav)
		fav.setIcon(R.drawable.ic_baseline_favorite_24)
		val dell = menu.findItem(R.id.app_bar_delete)
		dell.isVisible = false
		super.onPrepareOptionsMenu(menu)
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		when (item.itemId) {
			R.id.app_bar_history -> {
				navigation.replace(requireActivity().supportFragmentManager, WindowHistoryListImpl(), true, effect = EFFECT_DROP)
			}
			R.id.app_bar_fav -> {
				navigation.replace(requireActivity().supportFragmentManager, WindowFavorListImpl(), false, effect = EFFECT_DROP)
			}
		}
		return super.onOptionsItemSelected(item)
	}

	//endregion

	//region Instance

	private fun restore() {
		if (arguments != null) {
			arguments!!.getString(BUNDLE_SEARCH)?.let {
				forceSet(it)
			}
		}
		else viewModel.getSaved()
	}

	//endregion

	//region Adapter realization

	override fun onItemClickSpecial(data: DataWord, position: Int) {
		viewModel.visualChange(data, position)
	}

	override fun onItemClick(data: DataWord, position: Int, type: Boolean) {
		if (type) viewModel.favAdd(data)
		else viewModel.favRemove(data)
	}

	//endregion

	//region Command realization

	private fun toKeyboardMode() {
		with(binding) {
			val memory = target.query
			target.isIconified = true
			disabler.visibility = View.VISIBLE
			enabler.visibility = View.GONE
			target.isIconified = false
			target.requestFocus()
			target.setQuery(memory, false)
		}
	}

	private fun toViewMode() {
		with(binding) {
			hideKeyboard()
			enabler.visibility = View.VISIBLE
			disabler.visibility = View.GONE
			target.clearFocus()
		}
	}

	private fun renderData(material: StatusProcess) {
		when (material) {
			StatusProcess.Loading -> loadingState(true)
			is StatusProcess.Error -> errorCatch()
			is StatusProcess.Success -> {
				with(material.data) {
					refresh(data)
					if (data.isNotEmpty()) loadingState(loadState)
				}
			}
			is StatusProcess.Change -> changeState(material.data, material.position, material.count)
			is StatusProcess.ForceSet -> forceSet(material.data)
		}
	}

	private fun errorCatch() {
		//("Not yet implemented")
	}

	private fun refresh(list: List<DataWord>) {
		adapter.setList(list)
	}

	private fun changeState(list: List<DataWord>, position: Int, count: Int) {
		adapter.stackControl(list, position, count)
	}

	private fun forceSet(string: String) {
		toViewMode()
		binding.target.setQuery(string, true)
	}

	private fun loadingState(state: Boolean) {
		if (state) {
			binding.list.naming.text = getString(R.string.word_list_alter)
			binding.list.loadingLayout.visibility = View.VISIBLE
		}
		else {
			binding.list.naming.text = getString(R.string.word_list_title)
			binding.list.loadingLayout.visibility = View.INVISIBLE
		}
	}

	//endregion

	companion object {
		@JvmStatic
		fun newInstance(search: String) = WindowWordListImp().apply {
			arguments = Bundle().apply {
				putString(BUNDLE_SEARCH, search)
			}
		}
	}
}