package ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.example.mynotes.R;
import com.google.android.material.navigation.NavigationView;

public class NotesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        mainInit();
        initDrawer(isPortrait());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch(id) {
            case R.id.item_about:
            Button newNoteBtn = findViewById(R.id.new_note_btn);
            if(newNoteBtn != null){
            newNoteBtn.setVisibility(View.INVISIBLE);}
            getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack("")
                    .replace(R.id.notes_container, new AboutFragment())
                    .commit();
            break;

            case R.id.exit:
                System.exit(0);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void mainInit(){
        MainFragment mainFragment = new MainFragment();
        Button newNoteBtn = findViewById(R.id.new_note_btn);

        if(!isPortrait()){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.notes_container, mainFragment)
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

    private void initDrawer(boolean isPortrait){

        if(isPortrait){
            final DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
            drawerLayout.addDrawerListener(actionBarDrawerToggle);
            NavigationView navigationView = findViewById(R.id.navigation_view);
            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    drawerLayout.close();
                    int id = item.getItemId();
                    switch(id) {
                        case R.id.drawer_about:
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .addToBackStack("")
                                    .replace(R.id.notes_container, new AboutFragment())
                                    .commit();
                            return true;

                        case R.id.drawer_exit:
                            System.exit(0);
                            return true;
                    }
                    return false;
                }
            });
        }
    }

    private boolean isPortrait(){
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }
}