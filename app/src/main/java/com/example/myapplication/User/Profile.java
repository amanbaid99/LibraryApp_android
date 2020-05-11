
package com.example.myapplication.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.signin.login.Login;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {
    View header;
    NavigationView navbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle mtoggle;
    FirebaseAuth fAuth;
    TextView Greetings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlay);
        navbar = (NavigationView) findViewById(R.id.drawer);
        navbar.bringToFront();
        header = navbar.getHeaderView(0);
        mtoggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(mtoggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mtoggle.syncState();
        fAuth = FirebaseAuth.getInstance();
        Greetings = (TextView) findViewById(R.id.greetings);
        DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("UserDB");
        final String UserIdP = getIntent().getStringExtra("Ukey");
        Toast.makeText(getApplicationContext(), "" + UserIdP, Toast.LENGTH_SHORT).show();


        if (UserIdP != null) {

            reff.child(UserIdP).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String mname = dataSnapshot.child("fullName").getValue().toString();
                    Greetings.setText("Hello " + mname);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            navbar.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    int id = item.getItemId();
                    drawerLayout.closeDrawers();
                    if (id == R.id.Profile) {
                        drawerLayout.closeDrawer(GravityCompat.START);
                    }
                    else if (id == R.id.browse) {
                        Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_LONG).show();
//                    Intent h = new Intent(getApplicationContext(), MainActivity.class);
//                    startActivity(h);
                    }
                    else if (id == R.id.search) {
                        Intent search = new Intent(getApplicationContext(), SearchPage.class);
                        startActivity(search);
                    }
                    else if (id == R.id.home) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                    else if (id == R.id.Logoutbtn) {
                        fAuth.signOut();
                        Intent intent = new Intent(getApplicationContext(), Login.class);
                        startActivity(intent);
                        finish();
                    }
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return true;

                }

            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (mtoggle.onOptionsItemSelected(item)) {
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
