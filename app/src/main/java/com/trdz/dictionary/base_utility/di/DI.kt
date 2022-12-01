package com.trdz.dictionary.base_utility.di

import kotlin.reflect.KClass
import kotlin.reflect.KFunction

interface DI {
	fun <T: Any> get(clazz: KClass<T>): T
	fun <T: Any> add(clazz: KClass<T>, dependency: KFunction<T>)
}

class DIImpl: DI {
	private val holder = HashMap<KClass<*>, KFunction<Any>>()

	override fun <T: Any> get(clazz: KClass<T>): T {
		if (holder.containsKey(clazz)) return holder[clazz]?.call() as T
		else throw IllegalArgumentException("Required class don't exist in DI holder")
	}

	override fun <T: Any> add(clazz: KClass<T>, dependency: KFunction<T>) {
		holder[clazz] = dependency
	}

}