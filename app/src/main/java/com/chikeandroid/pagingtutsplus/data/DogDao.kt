package com.chikeandroid.pagingtutsplus.data

import android.arch.lifecycle.LiveData
import android.arch.paging.DataSource
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import io.reactivex.Flowable

@Dao
interface DogDao {

    @Query("SELECT * FROM dogs")
    fun getAllPaged(): DataSource.Factory<Int, Dog>

    @Query("SELECT * FROM dogs")
    fun getAll(): LiveData<List<Dog>>

    @Insert
    fun insertAll(persons: List<Dog>)

    @Delete
    fun delete(person: Dog)
}