package com.chikeandroid.pagingtutsplus.data

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.paging.PageKeyedDataSource

class PersonDataSource(val personDaoFirst: PersonDao, val personDaoSecond: PersonDao) : PageKeyedDataSource<Int, MergePersonOrDog>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, MergePersonOrDog>) {
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, MergePersonOrDog>) {
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, MergePersonOrDog>) {
    }
}