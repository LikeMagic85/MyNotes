package ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mynotes.R;

import model.Note;

public class NotesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        MainFragment mainFragment = new MainFragment();
        Button newNoteBtn = findViewById(R.id.new_note_btn);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.notes_detail, FullNoteFragment.newInstance(Note.getNote(0)))
                    .commit();
        }else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.notes_container, mainFragment)
                    .commit();
            newNoteBtn.setOnClickListener(v -> {
                NewNoteFragment fragment = NewNoteFragment.createNewNote();
                newNoteBtn.setVisibility(View.INVISIBLE);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.notes_container, fragment)
                        .addToBackStack("")
                        .commit();
            });
        }

    }
}