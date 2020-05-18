package com.example.myapplication.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.User.Bkhomeholder;
import com.example.myapplication.User.Bookdeets;
import com.example.myapplication.signin.authentication.Login;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class TempBooksLayout extends AppCompatActivity {
    public CardView cardView;
    RecyclerView recyclerView;
    DatabaseReference reference;
    FirebaseRecyclerAdapter<Bookdeets, Bkhomeholder> adapter;
    FirebaseRecyclerOptions<Bookdeets> options;
    ProgressBar loading;
    View header;
    NavigationView navbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle mtoggle;
    FirebaseAuth fAuth;
    String Checker="notdefined";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_books_layout);
        cardView = (CardView) findViewById(R.id.bookcardview);
        reference = FirebaseDatabase.getInstance().getReference();
        reference.keepSynced(true);
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        loading = (ProgressBar) findViewById(R.id.loading);
        navbar = (NavigationView) findViewById(R.id.drawer);
        navbar.bringToFront();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlay);
        header = navbar.getHeaderView(0);
        mtoggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        drawerLayout.addDrawerListener(mtoggle);
        mtoggle.syncState();
        fAuth= FirebaseAuth.getInstance();
        reference.child("TempBookDB").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Checker="Exists";



                }
                  else {
                    Checker="NotExists";

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });
        options = new FirebaseRecyclerOptions.Builder<Bookdeets>()
                .setQuery(reference.child("TempBookDB"), Bookdeets.class).build();


        if(Checker.equals("Exists")) {
            adapter = new FirebaseRecyclerAdapter<Bookdeets, Bkhomeholder>(options) {
                @Override
                protected void onBindViewHolder(@NonNull Bkhomeholder holder, final int position, @NonNull Bookdeets model) {

                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final String key = getRef(position).getKey().toString();
                            Toast.makeText(getApplicationContext(), "" + key, Toast.LENGTH_LONG).show();

                            Intent i = new Intent(getApplicationContext(), AdminBookdetails.class);
                            i.putExtra("key", key);
                            i.putExtra("id", "tempbooks");
                            startActivity(i);
                        }
                    });
                    Picasso.get().load(model.getImage()).into(holder.bookimg, new Callback() {
                        @Override
                        public void onSuccess() {
                            loading.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError(Exception e) {
                            Toast.makeText(getApplicationContext(), "could not get the image", Toast.LENGTH_LONG).show();
                            loading.setVisibility(View.GONE);
                        }
                    });
                    holder.title.setText(model.getBookname());
                }

                @NonNull
                @Override
                public Bkhomeholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardviewlay, parent, false);
                    return new Bkhomeholder(view);
                }
            };


            GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 3);
            recyclerView.setLayoutManager(gridLayoutManager);
            adapter.startListening();
            recyclerView.setAdapter(adapter);
        }
        else{
            Toast.makeText(this, "no books found in Temp Database", Toast.LENGTH_SHORT).show();
            Intent goback=new Intent(getApplicationContext(),AdminHome.class);
                startActivity(goback);


        }
        navbar.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                drawerLayout.closeDrawers();

                if(id== R.id.home) {
                    Intent h = new Intent(getApplicationContext(), AdminHome.class);
                    startActivity(h);

                }
                else if(id== R.id.EditBooks) {
                    Intent h = new Intent(getApplicationContext(), AdminSearch.class);
                    startActivity(h);
                }

                else if(id== R.id.tempdb) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                else if(id== R.id.addbooksadmin) {
                    Intent ad = new Intent(getApplicationContext(), Addbooks.class);
                    ad.putExtra("admid","Admin");
                    startActivity(ad);
                }

                else if(id== R.id.Logoutbtn) {
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
    @Override
    protected void onStart() {

        super.onStart();
        if (adapter != null) {
            adapter.startListening();
            loading.setVisibility(View.VISIBLE);

        }

    }
    @Override
    protected void onStop() {
        super.onStop();
        if (adapter != null)
            adapter.stopListening();
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null)
            adapter.startListening();
    }
}
