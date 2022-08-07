package ui;


import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;

import com.example.mynotes.R;

import model.Note;

public class FullNoteFragment extends Fragment {

    static final String ARG_INDEX = "index";


    public FullNoteFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
            requireActivity().getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_full_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        Bundle arguments = getArguments();

        if(arguments != null) {
            Note note = arguments.getParcelable(ARG_INDEX);
            TextView title = requireActivity().findViewById(R.id.full_note_title);
            TextView text = requireActivity().findViewById(R.id.full_note_text);
            TextView date = requireActivity().findViewById(R.id.full_note_date);
            if(title != null && text != null && date != null){
            title.setText(note.getNoteTitle());
            text.setText(note.getNoteText());
            date.setText(note.getNoteCompDate());
            }
        }
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            if(savedInstanceState == null){
                setHasOptionsMenu(true);
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        requireActivity().getMenuInflater().inflate(R.menu.note_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Bundle arguments = getArguments();
        Note note = arguments.getParcelable(ARG_INDEX);
        switch (id){
            case R.id.edit_item:
                editNote(note);
                break;

            case R.id.del_item:
                deleteNote(note);
                requireActivity().getSupportFragmentManager().popBackStack();
                Toast.makeText(getContext(), "Заметка удалена", Toast.LENGTH_LONG).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public static FullNoteFragment newInstance(Note note){
        FullNoteFragment fragment = new FullNoteFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_INDEX, note);
        fragment.setArguments(args);
        return fragment;
    }

    private void deleteNote(Note note){
        Note.getAll().remove(note);
    }

    private void editNote(Note note){
        EditFragment fragment = EditFragment.editFragmentNote(note);
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.notes_container, fragment)
                .addToBackStack("")
                .commit();
    }

}