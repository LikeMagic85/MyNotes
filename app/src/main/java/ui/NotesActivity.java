package ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;

import com.example.mynotes.FullNoteFragment;
import com.example.mynotes.R;

public class NotesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        MainFragment mainFragment = new MainFragment();

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.notes_detail, FullNoteFragment.newInstance(0))
                    .commit();
        }else {
            getSupportFragmentManager().
                    beginTransaction().
                    replace(R.id.notes_container, mainFragment)
                    .commit();
        }
    }
}