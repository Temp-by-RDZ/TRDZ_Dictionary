package com.trdz.dictionary

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.trdz.dictionary.model.Repository
import com.trdz.dictionary.model.data.DataWord
import com.trdz.dictionary.model.data.RequestResults
import com.trdz.dictionary.model.data.ServersResult
import com.trdz.dictionary.model.data.VisualState
import com.trdz.dictionary.utility.TYPE_CARD
import com.trdz.dictionary.utility.TYPE_TITLE
import com.trdz.dictionary.view_model.SingleLiveData
import com.trdz.dictionary.view_model.StatusProcess
import com.trdz.dictionary.view_model.WordsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.*
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class ViewModelsTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var testCoroutineRule = TestCoroutineRule()

    private lateinit var vm: WordsViewModel

    @Mock
    private lateinit var repository: Repository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        vm = WordsViewModel(repository, SingleLiveData())
    }

    private fun setForceData() = mutableListOf(
        DataWord("Title 1","Desc for 1",0,"", VisualState(TYPE_TITLE,0,0,false)),
        DataWord("Card 1","Desc for 1",0,"", VisualState(TYPE_CARD,1,0,false)),
        DataWord("Title 2","Desc for 2",0,"", VisualState(TYPE_TITLE,2,0,false)),
        DataWord("Card 1","Desc for 1",0,"", VisualState(TYPE_CARD,3,0,false)),
        DataWord("Card 2","Desc for 2",0,"", VisualState(TYPE_CARD,3,0,false)),
    )

    @Test
    fun coroutines_TestLoading() {
        testCoroutineRule.runBlockingTest {
            val observer = Observer<StatusProcess> {}
            val liveData = vm.getData()

            try {
                liveData.observeForever(observer)
                vm.setSearch("dog")

                Assert.assertEquals(liveData.value, StatusProcess.Loading)
            }
            finally {
                liveData.removeObserver(observer)
            }
       }
    }

    lateinit var value: StatusProcess.Success
    @Test
    fun coroutines_TestData() {
        testCoroutineRule.runBlockingTest {
            val observer = Observer<StatusProcess> {}
            val liveData = vm.getData()

            try {
                `when`(repository.initWordList("dog")).thenReturn(
                    RequestResults.SuccessWords(ServersResult(200, setForceData()))
                )

                liveData.observeForever(observer)
                vm.setSearch("dog")
                while (true) {
                    if (liveData.value is StatusProcess.Success) {
                        value = liveData.value as StatusProcess.Success
                        break
                    }
                }

                Assert.assertEquals(value.data.data[0].name, "Title 1")
            }
            finally {
                liveData.removeObserver(observer)
            }
        }
    }

    @After
    fun close() {
        stopKoin()
    }
}
