package ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.mynotes.R;

import java.util.List;

import model.ListAdapter;
import model.Note;
import model.OnItemClickListener;


public class ListFragment extends Fragment {

    public ListFragment() {
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
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        RecyclerView recyclerView =  view.findViewById(R.id.recycle_view_lines);

        List<Note> notes = Note.getAll();
        initRecyclerView(recyclerView, notes);

        return view;
    }

    private void initRecyclerView(RecyclerView recyclerView, List<Note> notes){
        ListAdapter listAdapter = new ListAdapter(notes);
        recyclerView.setAdapter(listAdapter);
        Button newNoteBtn = requireActivity().findViewById(R.id.new_note_btn);
        newNoteBtn.setVisibility(View.VISIBLE);
        listAdapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                showFullNote(Note.getNote(position));
            }
        });

    }

    private void showFullNote(Note note){
        FullNoteFragment fullNoteFragment = FullNoteFragment.newInstance(note);

        requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.notes_container, fullNoteFragment)
                .addToBackStack("")
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
}