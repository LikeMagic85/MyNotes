package ui;

import androidx.annotation.MenuRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ActionMenuView;
import android.widget.Button;
import android.widget.TextView;

import com.example.mynotes.R;
import com.google.android.material.navigation.NavigationView;

import model.ListAdapter;
import model.LoginDialog;
import model.Note;

public class NotesActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        mainInit();
        initDrawer(isPortrait());

    }


    private void mainInit(){
        ListFragment mainFragment = new ListFragment();

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
                        case R.id.login:
                            showLoginDialog();
                            return true;
                        case R.id.drawer_about:
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .addToBackStack("")
                                    .replace(R.id.notes_container, new AboutFragment())
                                    .commit();
                            return true;

                        case R.id.drawer_exit:
                            new AlertDialog.Builder(NotesActivity.this).setTitle("Выход").setMessage("Вы желаете выйти?").setPositiveButton("Дa", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    System.exit(0);
                                }
                            }).setNegativeButton("Нет", null).show();
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

    private void showLoginDialog(){
        new LoginDialog().show(getSupportFragmentManager(), "LOGIN_DIALOG");
        TextView user = findViewById(R.id.user_info);
        getSupportFragmentManager().setFragmentResultListener("LOGIN_RESULT", this,new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                user.setText(result.getString("USER_NAME"));
            }
        });
    }
}