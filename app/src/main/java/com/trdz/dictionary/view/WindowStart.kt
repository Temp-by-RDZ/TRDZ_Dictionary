package com.trdz.dictionary.view

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import androidx.fragment.app.Fragment
import com.trdz.dictionary.MyApp
import com.trdz.dictionary.base_utility.EFFECT_RISE
import com.trdz.dictionary.databinding.FragmentWindowStartBinding
import com.trdz.dictionary.view.segment_users.WindowWordListImp
import javax.inject.Inject

class WindowStart: Fragment() {

	//region Elements

	private var _binding: FragmentWindowStartBinding? = null
	private val binding get() = _binding!!

	//endregion

	//region Injected

	override fun onAttach(context: Context) {
		MyApp.instance.appComponent.inject(this)
		super.onAttach(context)
	}

	@Inject
	lateinit var navigation: Navigation

	//endregion

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		_binding = FragmentWindowStartBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		binding.firstView.animate().apply {
			alpha(0.0f)
			duration = 2900L
			withEndAction { requireActivity().supportFragmentManager.beginTransaction().detach(this@WindowStart).commit() }
			start()
		}
		createMainWindow()
	}

	private fun createMainWindow() {
		Handler(Looper.getMainLooper()).postDelayed({
			navigation.replace(requireActivity().supportFragmentManager, WindowWordListImp(), false, effect = EFFECT_RISE)
		}, 100L)
	}

	companion object {
		fun newInstance() = WindowStart()
	}
}

