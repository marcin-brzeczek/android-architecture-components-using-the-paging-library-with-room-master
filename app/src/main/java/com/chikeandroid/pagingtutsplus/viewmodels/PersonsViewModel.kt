package com.chikeandroid.pagingtutsplus.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveDataReactiveStreams
import android.arch.lifecycle.MediatorLiveData
import com.chikeandroid.pagingtutsplus.data.AppDatabase
import com.chikeandroid.pagingtutsplus.data.Dog
import com.chikeandroid.pagingtutsplus.data.DogDao
import com.chikeandroid.pagingtutsplus.data.Person
import com.chikeandroid.pagingtutsplus.data.PersonDao
import com.chikeandroid.pagingtutsplus.data.SingleLiveEvent
import com.chikeandroid.pagingtutsplus.data.TableItem
import io.reactivex.Flowable
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.zipWith
import java.util.concurrent.Executors

sealed class MergedData
data class DogsData(val dogsItems: List<Dog>): MergedData()
data class PersonData(val personItems: List<Person>): MergedData()

class PersonsViewModel constructor(application: Application) : AndroidViewModel(application) {

    //    private var personLiveData: LiveData<PagedList<MergePersonOrDog>>
    val executorService = Executors.newSingleThreadExecutor();

    private var personDao: PersonDao

    private var dogDao: DogDao

    init {
        personDao = AppDatabase.getInstance(getApplication()).personDao()

        dogDao = AppDatabase.getInstance(getApplication()).dogDao()
//        val factory: DataSource.Factory<Int, MergePersonOrDog> = AppDatabase.getInstance(getApplication()).personDao().getPersonAndDog()
//        val pagedListBuilder: LivePagedListBuilder<Int, MergePersonOrDog> = LivePagedListBuilder<Int, MergePersonOrDog>(factory, 50)
//        personLiveData = pagedListBuilder.build()
    }
//
//    fun getItemLiveData() = LiveDataReactiveStreams.fromPublisher(Flowable.zip<List<Dog>, List<Person>,List<TableItem>>(dogDao.getAll(), personDao.getPersons(), BiFunction { t1, t2 ->
//        listOf(t1, t2).flatten()
//    }))
//
//    fun mergeFlowable(dogs: Flowable<List<Dog>>, persons: Flowable<List<Person>>): Flowable<List<TableItem>> {
//        val list = mutableListOf<TableItem>()
//        dogs.map { list.addAll(it) }
//        persons.map { list.addAll(it) }
//        return Flowable.fromArray(list)
//    }

    fun fetchData(): SingleLiveEvent<MergedData> {
        val liveDataMerger = SingleLiveEvent<MergedData>()
        liveDataMerger.addSource(dogDao.getAll(), {
            if (it != null) {
                liveDataMerger.value = DogsData(it)
            }
        })
        liveDataMerger.addSource(personDao.getPersons(), {
            if (it != null) {
                liveDataMerger.value = PersonData(it)
            }
        })
        return liveDataMerger
    }

    fun deleteItem(item: TableItem) = executorService.execute {
        when (item) {
            is Dog -> dogDao.delete(item)
            is Person -> personDao.delete(item)
        }
    }
}