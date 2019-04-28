package com.chikeandroid.pagingtutsplus.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "dogs")
data class Dog(
        @PrimaryKey override val id: String,
        val name: String
) : TableItem {
    override fun toString() = name
}