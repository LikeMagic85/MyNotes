package ui;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
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
    private Note currentNote = new Note();

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
        Button newNoteBtn = getActivity().findViewById(R.id.new_note_btn);
        if(savedInstanceState != null){
            currentNote = savedInstanceState.getParcelable(NOTE);
        }
        notesListShow(view);

        if(isLandscape()){
            showFullNoteLandscape(currentNote);
        }else{
            newNoteBtn.setVisibility(View.VISIBLE);
        }


    }


    private void notesListShow(View view){
        LinearLayout linearLayout = (LinearLayout) view;

        if(Note.getAll().size() != 0){
            for (int i = 0; i < Note.getAll().size(); i++) {
                TextView textView = new TextView(getContext());
                textView.setTextSize(26);
                textView.setTextColor(getResources().getColor(R.color.black));
                textView.setText(Note.getNote(i).getNoteTitle());
                final int index = i;
                intiPopupMenu(linearLayout, textView, index);
                textView.setOnClickListener(v -> {
                    if (isLandscape()) {
                        showFullNoteLandscape(Note.getNote(index));
                    }else {
                        showFullNote(Note.getNote(index));
                    }
                    currentNote = Note.getNote(index);
                });
                linearLayout.addView(textView);
            }

        }else {
            TextView textView = new TextView(getContext());
            textView.setTextSize(26);
            textView.setTextColor(getResources().getColor(R.color.black));
            textView.setText("Список заметок пуст");
            linearLayout.addView(textView);
        }
    }

    private void intiPopupMenu(LinearLayout ll, TextView tv, int index){
        tv.setOnLongClickListener(v ->{
            PopupMenu popupMenu = new PopupMenu(requireActivity(), tv);
            requireActivity().getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    switch (menuItem.getItemId()){
                        case R.id.del_item:
                            Note.getAll().remove(index);
                            ll.removeAllViews();
                            notesListShow(ll);
                            break;
                    }
                    return true;
                }
            });
            popupMenu.show();
            return true;
        });
    }

    private void showFullNoteLandscape(Note note) {
        FullNoteFragment fullNoteFragment = FullNoteFragment.newInstance(note);

        requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.notes_detail, fullNoteFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
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

    private boolean isLandscape(){
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(NOTE, currentNote);
        super.onSaveInstanceState(outState);
    }
}