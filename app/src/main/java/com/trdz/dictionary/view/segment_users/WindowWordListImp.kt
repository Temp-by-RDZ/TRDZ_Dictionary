package com.trdz.dictionary.view.segment_users

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.trdz.dictionary.base_utility.hideKeyboard
import com.trdz.dictionary.databinding.FragmentNavigationBinding
import com.trdz.dictionary.model.DataWord
import com.trdz.dictionary.model.RepositoryExecutor
import com.trdz.dictionary.presenter.MainPresenter
import com.trdz.dictionary.view.Leader
import com.trdz.dictionary.view.MainActivity
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter


class WindowWordListImp: MvpAppCompatFragment(), WindowWordListOnClick, WindowWordList {

	//region Elements

	private var _binding: FragmentNavigationBinding? = null
	private val binding get() = _binding!!
	private var _executors: Leader? = null
	private val executors get() = _executors!!
	private val adapter = WindowWordListRecycle(this)
	private val presenter by moxyPresenter { MainPresenter(RepositoryExecutor()) }

	//endregion

	//region Base realization

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
		_executors = null
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		_binding = FragmentNavigationBinding.inflate(inflater, container, false)
		_executors = (requireActivity() as MainActivity)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			binding.list.recyclerView.setOnScrollChangeListener { _, _, _, _, _ ->
				binding.list.naming.isSelected = binding.list.recyclerView.canScrollVertically(-1)
			}
		}
		binding.list.recyclerView.adapter = adapter
		buttonBindings()
	}

	private fun buttonBindings() {
		binding.target.requestFocus()
		binding.floatButton.setOnClickListener {
			val text = binding.target.text.toString()
			if ((text != "") && (text != " ")) {
				presenter.startSearch(text)
				hideKeyboard()
			}
			else {
				binding.target.requestFocus()
			}
		}
	}

	//endregion

	//region Adapter realization

	override fun onItemClickSpecial(data: DataWord, position: Int) {
		presenter.visualChange(data, position)
	}

	//endregion

	//region Presenter command realization

	override fun errorCatch() {
		//("Not yet implemented")
	}

	override fun refresh(list: List<DataWord>) {
		adapter.setList(list)
	}

	override fun changeState(list: List<DataWord>, position: Int, count: Int) {
		adapter.stackControl(list, position, count)
	}

	override fun loadingState(state: Boolean) {
		binding.list.loadingLayout.visibility = if (state) View.VISIBLE
		else View.GONE
	}

	//endregion

	companion object {
		@JvmStatic
		fun newInstance() = WindowWordListImp()
	}
}