package com.trdz.dictionary

import com.trdz.dictionary.model.data.ADataSource
import com.trdz.dictionary.model.data.RequestResults
import com.trdz.dictionary.model.source_basis.DataSourceBasis
import com.trdz.dictionary.utility.TYPE_CARD
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SourceBasisTest {

	private lateinit var source: ADataSource

	@Before
	fun setUp() {
		 source = DataSourceBasis()
	}

	@Test
	fun sourceIsBasis() {
		assertNotNull("Theres no response", source.loadWords(""))
	}

	@Test
	fun sourceNotFail() {
		val answer = source.loadWords("")
		assertTrue("Response is failed", answer is RequestResults.SuccessWords)
	}

	private fun getResult() = (source.loadWords("") as RequestResults.SuccessWords).data.dataWord!!

	@Test
	fun sourceIsFull() {
		val answer = getResult()
		assertFalse("Response is empty", answer.isEmpty())
	}

	@Test
	fun taskedEquals() {
		val answer = getResult()
		assertEquals(answer[0].id,0)
	}
	@Test
	fun taskedNotEquals() {
		val answer = getResult()
		assertNotEquals(answer[0].visual.type, TYPE_CARD)

	}
	@Test
	fun taskedArrayEquals() {
		val answer1 = getResult()
		val answer2 = getResult()
		assertArrayEquals(answer1.toTypedArray(),answer2.toTypedArray())
	}
	@Test
	fun taskedSame() {
		val answer1 = getResult()
		val answer2 = getResult()
		assertSame(answer1[0].visual,answer2[0].visual)
	}

}
