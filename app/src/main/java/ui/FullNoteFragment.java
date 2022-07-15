package ui;


import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;

import com.example.mynotes.R;

import model.Note;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FullNoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FullNoteFragment extends Fragment {

    static final String ARG_INDEX = "index";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FullNoteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FullNoteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FullNoteFragment newInstance(String param1, String param2) {
        FullNoteFragment fragment = new FullNoteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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

        Button newNoteBtn = getActivity().findViewById(R.id.new_note_btn);
        if(arguments != null){
            Note note = arguments.getParcelable(ARG_INDEX);

        if(arguments != null){
            int index = arguments.getInt(ARG_INDEX);
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
            newNoteBtn.setVisibility(View.INVISIBLE);
        }
        Button delBtn = getActivity().findViewById(R.id.del_btn);
        if(delBtn != null) {
            delBtn.setOnClickListener(v -> {
                Note note = arguments.getParcelable(ARG_INDEX);
                getActivity().getSupportFragmentManager().popBackStack();
                deleteNote(note);
            });
        }
        Button editBtn = getActivity().findViewById(R.id.edit_btn);
        if(editBtn != null) {
            editBtn.setOnClickListener(v -> {
                Note note = arguments.getParcelable(ARG_INDEX);
                editNote(note);
            });
        }
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
            title.setText(Note.getAll().get(index).getNoteTitle());
            text.setText(Note.getAll().get(index).getNoteText());
            date.setText(Note.getAll().get(index).getNoteCompDate());
            }
        }

    }


    public static FullNoteFragment newInstance(int index){
        FullNoteFragment fragment = new FullNoteFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_INDEX, index);
        fragment.setArguments(args);
        return fragment;
    }
}