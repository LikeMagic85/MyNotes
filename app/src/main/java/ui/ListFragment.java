package ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.example.mynotes.R;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import model.ListAdapter;
import model.Note;
import model.OnItemClickListener;


public class ListFragment extends Fragment {

    private RecyclerView recyclerView;
    private ListAdapter listAdapter;
    private SharedPreferences sharedPreferences;
    public static final String KEY = "KEY";

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.cards_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add:
                NewNoteFragment fragment = NewNoteFragment.createNewNote();
                requireActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.notes_container, fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                        .addToBackStack("")
                        .commit();
                listAdapter.notifyItemInserted(Note.getAll().size() - 1);
                recyclerView.scrollToPosition(Note.getAll().size() - 1);
                return true;

        }

        return super.onOptionsItemSelected(item);
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

        recyclerView =  view.findViewById(R.id.recycle_view_lines);

        List<Note> notes = Note.getAll();
        initRecyclerView(recyclerView, notes);
        setHasOptionsMenu(true);

        return view;
    }

    private void initRecyclerView(RecyclerView recyclerView, List<Note> notes){
        listAdapter = new ListAdapter(notes, this);
        recyclerView.setAdapter(listAdapter);

        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);

        String saveData = sharedPreferences.getString(KEY, null);

        try {
            Type type = new TypeToken<List<Note>>(){}.getType();
            new GsonBuilder().create().fromJson(saveData, type);
            listAdapter.setData(new GsonBuilder().create().fromJson(saveData, type));
        } catch (Exception e){
            Toast.makeText(getContext(),"Список заметок пуст", Toast.LENGTH_LONG).show();
        }

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

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = requireActivity().getMenuInflater();
        menuInflater.inflate(R.menu.popup_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.del_item:
                    delNote(listAdapter.getMenuPosition());
                    listAdapter.notifyItemRemoved(listAdapter.getMenuPosition());
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void delNote(int position){

        Note.getAll().remove(position);
        listAdapter.notifyItemRemoved(position);

        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        String jsonDel = new GsonBuilder().create().toJson(Note.getAll());
        sharedPreferences.edit().putString(KEY, jsonDel).apply();
    }
}