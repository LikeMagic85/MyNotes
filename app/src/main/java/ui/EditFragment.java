package ui;

import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.example.mynotes.R;
import com.google.gson.GsonBuilder;

import model.ListAdapter;
import model.Note;


public class EditFragment extends Fragment {


    static final String ARG_INDEX = "index";
    public static final String KEY = "KEY";
    SharedPreferences sharedPreferences;


    public EditFragment() {
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
        return inflater.inflate(R.layout.fragment_edit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle arguments = getArguments();
        EditText title = getActivity().findViewById(R.id.edit_title);
        EditText text = getActivity().findViewById(R.id.edit_text);
        EditText date = getActivity().findViewById(R.id.edit_date);
        if(arguments != null) {
            Note note = arguments.getParcelable(ARG_INDEX);
            title.setText(note.getNoteTitle(), TextView.BufferType.EDITABLE);
            text.setText(note.getNoteText(), TextView.BufferType.EDITABLE);
            date.setText(note.getNoteCompDate(), TextView.BufferType.EDITABLE);
            Button editSaveBtn = getActivity().findViewById(R.id.edit_save_btn);
            editSaveBtn.setOnClickListener(v ->{
                note.setNoteTitle(title.getText().toString());
                note.setNoteText(text.getText().toString());
                note.setNoteCompDate(date.getText().toString());
                requireActivity().getSupportFragmentManager().popBackStack();
                Toast.makeText(getContext(), "Сохранено", Toast.LENGTH_LONG).show();
                ListAdapter listAdapter = new ListAdapter(Note.getAll(), this);
                listAdapter.notifyItemChanged(listAdapter.getMenuPosition());
                String jsonEdit = new GsonBuilder().create().toJson(Note.getAll());
                sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
                sharedPreferences.edit().putString(KEY, jsonEdit).apply();
            });
        }


    }

    public static EditFragment editFragmentNote(Note note){
        EditFragment fragment = new EditFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_INDEX, note);
        fragment.setArguments(args);
        return fragment;
    }
}