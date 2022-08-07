package ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.mynotes.R;

import java.util.ArrayList;

import model.ListAdapter;
import model.Note;


public class NewNoteFragment extends Fragment {

    ListAdapter adapter = new ListAdapter(Note.getAll(), this);
    RecyclerView recyclerView;

    public NewNoteFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_note, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button saveButton = getActivity().findViewById(R.id.save_btn);

        saveButton.setOnClickListener(v -> {
            EditText title = getActivity().findViewById(R.id.new_note_title);
            EditText text = getActivity().findViewById(R.id.new_note_text);
            EditText date = getActivity().findViewById(R.id.new_note_date);
            Note note = new Note(title.getText().toString(), text.getText().toString(), date.getText().toString());
            Note.saveNote(note);
            getActivity().getSupportFragmentManager().popBackStack();
            adapter.notifyItemInserted(Note.getAll().size() - 1);
        });

    }

    public static NewNoteFragment createNewNote(){
        NewNoteFragment newNoteFragment = new NewNoteFragment();
        return newNoteFragment;
    }
}