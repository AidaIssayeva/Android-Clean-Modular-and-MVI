package com.cupsofcode.cache

import com.gojuno.koptional.rxjava2.filterSome
import com.gojuno.koptional.toOptional
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject

open class InMemoryCache<V : Any> @Inject constructor() {

    private val concurrentMap = ConcurrentHashMap<String, V>()

    private val cacheSubject = BehaviorSubject.create<Map<String, V>>()

    fun isEmpty() = Single.just(concurrentMap.isEmpty())

    fun putAll(objects: Map<String, V>): Completable {
        return Completable.fromCallable {
            concurrentMap.putAll(objects)
            cacheSubject.onNext(concurrentMap)
        }
    }

    fun putAll(objects: List<V>, getIdBlock: (V) -> String): Completable {
        return Completable.fromCallable {
            val newMap = objects
                .map { getIdBlock.invoke(it) to it }
                .toMap()
            concurrentMap.putAll(newMap)
            cacheSubject.onNext(concurrentMap)
        }
    }

    fun put(value: V, id: String): Completable {
        return Completable.fromCallable {
            concurrentMap[id] = value
            cacheSubject.onNext(concurrentMap)
        }
    }

    fun allObservable(): Observable<List<V>> {
        return cacheSubject.hide()
            .map { it.values.toList() }
    }

    fun allSingle(): Single<List<V>> {
        return Single.just(concurrentMap)
            .map { it.values.toList() }
    }

    fun oneObservable(id: String): Observable<V> {
        return cacheSubject
            .hide()
            .map {
                it[id].toOptional()
            }
            .filterSome()
            .map { it }
    }


    fun oneSingle(id: String): Single<V> {
        return Single.just(concurrentMap)
            .flatMap {
                val value = it[id]
                if (value != null) {
                    Single.just(value)
                } else {
                    Single.error(NoSuchElementException())
                }
            }
    }

    fun clearAll(): Completable {
        return Completable.fromCallable { concurrentMap.clear() }
    }
}

