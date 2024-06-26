package com.example.notes

import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() , INotesRVAdapter{

    private lateinit var viewModel: NoteViewModel

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.addItemDecoration(SpacesItemDecoration(21))    // It provide space between recycler view item
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = NotesRvAdapter(this, this)
        recyclerView.adapter = adapter

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[NoteViewModel::class.java]
        viewModel.allNotes.observe(this) { list ->
            list?.let {
                adapter.updateList(it)
            }

        }

    }

    class SpacesItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            outRect.bottom = space
        }
    }

        override fun onItemClicked(note : Note){
        viewModel.deleteNote(note)
        Toast.makeText(this ,"${note.text} DELETED", Toast.LENGTH_LONG).show()
    }

    fun submitData(view : View) {
        val input : EditText = findViewById(R.id.input)
        var noteText = input.text.toString()
        if (noteText.isNotEmpty()){
            viewModel.insertNote(Note(noteText))
//            Toast.makeText(this , "$noteText INSERTED", Toast.LENGTH_LONG).show()
            input.text.clear()
        }
    }
}