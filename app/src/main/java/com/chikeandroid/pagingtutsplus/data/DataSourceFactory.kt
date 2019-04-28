package com.chikeandroid.pagingtutsplus.data

import android.arch.paging.DataSource

class DataSourceFactory(val personDaoFirst: PersonDao, val personDaoSecond: PersonDao) : DataSource.Factory<Int, MergePersonOrDog>() {

    override fun create(): DataSource<Int, MergePersonOrDog> {

        return PersonDataSource(personDaoFirst, personDaoSecond)
    }

//    val newsDataSourceLiveData = MutableLiveData<NewsDataSource>()

//    override fun create(): DataSource<Int, News> {
//        val newsDataSource = NewsDataSource(networkService, compositeDisposable)
//        newsDataSourceLiveData.postValue(newsDataSource)
//        return newsDataSource
//    }
}