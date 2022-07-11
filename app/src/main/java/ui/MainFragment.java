package ui;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mynotes.R;

import java.util.ArrayList;

import model.Note;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String NOTE = "note";
    private int currentNote = 0;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TitleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(savedInstanceState != null){
            currentNote = savedInstanceState.getInt(NOTE,0);
        }
        notesListShow(view);

        if(isLandscape()){
            showFullNoteLandscape(currentNote);
        }
    }


    private void notesListShow(View view){
        LinearLayout linearLayout = (LinearLayout) view;
        ArrayList<Note> notes = new ArrayList<>();
        for (int i = 0; i < getResources().getTextArray(R.array.notes_title).length; i++) {
            TextView textView = new TextView(getContext());
            Note note = new Note();
            note.setNoteTitle(getResources().getTextArray(R.array.notes_title)[i].toString());
            note.setNoteText(getResources().getTextArray(R.array.notes_text)[i].toString());
            note.setNoteCompDate(getResources().getTextArray(R.array.notes_date)[i].toString());
            notes.add(note);
            textView.setText(note.getNoteTitle());
            textView.setTextSize(30);
            textView.setTextColor(getResources().getColor(R.color.black));
            linearLayout.addView(textView);
            final int index = i;
            textView.setOnClickListener(v -> {
                currentNote = index;
                if (isLandscape()) {
                    showFullNoteLandscape(index);
                }else{
                    showFullNote(index);
                }
            });
        }
        Note.saveAll(notes);
    }

    private void showFullNoteLandscape(int index) {
        FullNoteFragment fullNoteFragment = FullNoteFragment.newInstance(index);

        requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.notes_detail, fullNoteFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    private void showFullNote(int index){
        FullNoteFragment fullNoteFragment = FullNoteFragment.newInstance(index);

        requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.notes_container, fullNoteFragment)
                .addToBackStack("")
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    private boolean isLandscape(){
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(NOTE, currentNote);
        super.onSaveInstanceState(outState);
    }
}