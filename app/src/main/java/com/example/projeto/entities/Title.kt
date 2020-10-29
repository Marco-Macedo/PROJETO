package com.example.projeto.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.format.DateTimeFormatterBuilder

@Entity(tableName = "title_table")

class Title(

    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "title") val titulo: String,
    @ColumnInfo(name = "notes") val texto: String,
    @ColumnInfo(name = "date") val data: String

)