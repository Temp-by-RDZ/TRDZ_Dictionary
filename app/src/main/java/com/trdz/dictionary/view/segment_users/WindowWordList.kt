package com.trdz.dictionary.view.segment_users

import com.trdz.dictionary.model.DataWord
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface WindowWordList: MvpView {
	fun errorCatch()
	fun refresh(list: List<DataWord>)
	fun changeState(list: List<DataWord>, position: Int, count: Int)
	fun loadingState(state: Boolean)
}