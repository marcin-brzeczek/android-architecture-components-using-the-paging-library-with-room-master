package com.chikeandroid.pagingtutsplus.data

import android.arch.lifecycle.LiveData
import android.arch.paging.DataSource
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface PersonDao {

    @Query("SELECT * FROM persons")
    fun getPersons(): LiveData<List<Person>>

    @Query("SELECT * FROM persons LIMIT 5")
    fun getAllPaged(): DataSource.Factory<Int, Person>

    @Query("SELECT p.id as personId , d.id as dogId, p.name as namePerson, d.name as nameDog  FROM persons p LEFT JOIN  dogs d USING(name) UNION ALL SELECT p.id as personId , d.id as dogId, p.name as namePerson, d.name as nameDog  FROM dogs d LEFT JOIN  persons p USING(name) WHERE p.name IS NULL")
    fun getPersonAndDog(): DataSource.Factory<Int,MergePersonOrDog>

    @Insert
    fun insertAll(persons: List<Person>)

    @Delete
    fun delete(person: Person)
}