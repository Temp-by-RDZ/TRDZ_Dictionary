package com.trdz.dictionary

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.geekbrains.tests.TEST_ERROR_TEXT1
import com.geekbrains.tests.TEST_ERROR_TEXT2
import com.geekbrains.tests.TEST_ERROR_TEXT3
import com.trdz.dictionary.model.Repository
import com.trdz.dictionary.model.data.DataWord
import com.trdz.dictionary.model.data.VisualState
import com.trdz.dictionary.utility.TYPE_CARD
import com.trdz.dictionary.utility.TYPE_TITLE
import com.trdz.dictionary.view_model.ModelResult
import com.trdz.dictionary.view_model.SingleLiveData
import com.trdz.dictionary.view_model.StatusProcess
import com.trdz.dictionary.view_model.WordsViewModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ViewModelsTestBasic {

	@get:Rule
	val instantExecutorRule = InstantTaskExecutorRule()

	private lateinit var vm: WordsViewModel

	@Mock
	private lateinit var repository: Repository

	@Before
	fun setUp() {
		//Обязательно для аннотаций "@Mock"
		//Раньше было @RunWith(MockitoJUnitRunner.class) в аннотации к самому классу (SearchPresenterTest)
		MockitoAnnotations.initMocks(this)
		vm = WordsViewModel(repository, SingleLiveData())
	}

	@Test
	fun testSavedRestoreWhenEmpty() {
		val answerEmpty = "Order: search last position"
		Mockito.`when`(repository.checkLast()).thenReturn(answerEmpty)
		vm.getSaved()
		val actual = vm.getData().value
		val expected = StatusProcess.ForceSet(answerEmpty)
		assertEquals(TEST_ERROR_TEXT3,actual,expected)
	}

	private fun setForceData() = mutableListOf(
		DataWord("Title 1","Desc for 1",0,"", VisualState(TYPE_TITLE,0,0,false)),
		DataWord("Card 1","Desc for 1",0,"", VisualState(TYPE_CARD,1,0,false)),
		DataWord("Title 2","Desc for 2",0,"", VisualState(TYPE_TITLE,2,0,false)),
		DataWord("Card 1","Desc for 1",0,"", VisualState(TYPE_CARD,3,0,false)),
		DataWord("Card 2","Desc for 2",0,"", VisualState(TYPE_CARD,3,0,false)),
	)

	@Test
	fun testSavedRestoreWhenFull() {
		val answerFull = setForceData()
		//FORCEFUL DATA SET NOT ALLOWED IN ACTUAL PROGRAM THIS JUST FOR TEST DEMONSTRATION
		vm.controlledSet(answerFull)
		vm.getSaved()
		val actual = vm.getData().value
		val expected = StatusProcess.Success(ModelResult(answerFull))
		assertEquals(TEST_ERROR_TEXT2,actual,expected)
	}
	@Test
	fun testStateChange() {
		val answerFull = setForceData()
		//FORCEFUL DATA SET NOT ALLOWED IN ACTUAL PROGRAM THIS JUST FOR TEST DEMONSTRATION
		vm.controlledSet(answerFull)
		val id = 0
		vm.visualChange(answerFull[id],id)
		val actual = vm.getData().value
		answerFull[id].visual.state=1
		answerFull[id+1].visual.state=1 //count +1 because this is sub folder
		val expected = StatusProcess.Change(answerFull,id+1,1)
		assertEquals(TEST_ERROR_TEXT1,actual,expected)
	}

}