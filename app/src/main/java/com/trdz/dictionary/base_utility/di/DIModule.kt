package com.trdz.dictionary.base_utility.di

import androidx.room.Room
import com.google.gson.GsonBuilder
import com.trdz.dictionary.MyApp
import com.trdz.dictionary.base_utility.DOMAIN
import com.trdz.dictionary.model.data_source_room.database.DataBase
import com.trdz.dictionary.model.data_source_room.database.WordDao
import com.trdz.dictionary.model.data_source_server.ServerRetrofitApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DIModule(di: DI, val instance: MyApp) {

	private var db: DataBase? = null

	fun getHistoryDao(): WordDao {
		if (db == null) db = Room.databaseBuilder(instance, DataBase::class.java, "test").build()
		return db!!.userDao()
	}

	private var retrofitGitUsers: ServerRetrofitApi? = null

	private fun createRetrofit() {
		retrofitGitUsers = Retrofit.Builder().apply {
			baseUrl(DOMAIN)
			addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
		}.build().create(ServerRetrofitApi::class.java)
	}

	fun getRetrofit(): ServerRetrofitApi {
		if (retrofitGitUsers == null) createRetrofit()
		return retrofitGitUsers!!
	}

	init {
		di.add(WordDao::class, this::getHistoryDao)
		di.add(ServerRetrofitApi::class, this::getRetrofit)
	}
}