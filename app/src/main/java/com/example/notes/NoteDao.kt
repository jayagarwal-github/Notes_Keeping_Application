package com.example.notes

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note:Note)     // if a Function is a suspend function then it is only call by background thread (in short not use the main thread which thing make smoother app)

    @Delete
   suspend fun delete(note: Note)

    @Query("select * from notes_table order by id ASC")
    fun getAllNotes(): LiveData<List<Note>> // Through Live data Main Activity knows that's how & when Data is change (Method to track this function Through Main Activity)
}