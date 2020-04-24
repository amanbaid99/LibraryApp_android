package com.example.myapplication.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.example.myapplication.R;
import com.example.myapplication.User.MainActivity;
import com.example.myapplication.User.SearchPage;
import com.example.myapplication.signin.login.Login;
import com.google.android.material.navigation.NavigationView;

public class AdminHome extends AppCompatActivity {
    NavigationView navbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle ntoggle;
    View header;
    Button updatebtn,addbooksbtn,viewbooks;
    private boolean mToolBarNavigationListenerIsRegistered = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminhome);
        updatebtn=(Button) findViewById(R.id.Update_deletebooks);
        addbooksbtn=(Button) findViewById(R.id.AddBooks);
        viewbooks=(Button) findViewById(R.id.viewbtopbooks);
        navbar = (NavigationView) findViewById(R.id.draweradmin);
        navbar.bringToFront();
        header = navbar.getHeaderView(0);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayadmin);
        ntoggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerLayout.addDrawerListener(ntoggle);
        ntoggle.syncState();

        viewbooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nb=new Intent(getApplicationContext(), MainActivity.class);
                startActivity(nb);
            }
        });

        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), SearchPage.class);
                intent.putExtra("id","Adminsearch");
                   startActivity(intent);      }
        });
        addbooksbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abd=new Intent(getApplicationContext(),Addbooks.class);
                startActivity(abd);

            }
        });
        navbar.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                drawerLayout.closeDrawers();
                if(id== R.id.home) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                }


                else if(id== R.id.Logoutbtn) {
                    Intent intent = new Intent(getApplicationContext(), Login.class);
                    intent.putExtra("finish", true); // if you are checking for this in your other Activities
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK |
                            Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (ntoggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


}
