package com.example.myapplication.User;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
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

import com.example.myapplication.Admin.Addbooks;
import com.example.myapplication.R;
import com.example.myapplication.signin.login.Login;
import com.example.myapplication.signin.login.UserDB;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class MainActivity extends Login  {
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
    String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cardView = (CardView) findViewById(R.id.bookcardview);
        reference = FirebaseDatabase.getInstance().getReference();
        reference.keepSynced(true);
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        loading = (ProgressBar) findViewById(R.id.loading);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlay);
        navbar = (NavigationView) findViewById(R.id.drawer);
        navbar.bringToFront();
        header = navbar.getHeaderView(0);
        mtoggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(mtoggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mtoggle.syncState();
        uid=getIntent().getStringExtra("ID");
        fAuth= FirebaseAuth.getInstance();
        final String UserId = getIntent().getStringExtra("ID");
        options = new FirebaseRecyclerOptions.Builder<Bookdeets>()
                .setQuery(reference.child("TopbooksDB"), Bookdeets.class).build();
        adapter = new FirebaseRecyclerAdapter<Bookdeets, Bkhomeholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull Bkhomeholder holder, final int position, @NonNull Bookdeets model) {

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String key = getRef(position).getKey().toString();
                        Intent i = new Intent(getApplicationContext(), Bookdetailslayouthome.class);
                        i.putExtra("uid",uid);
                        i.putExtra("key", key);
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
        navbar.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                drawerLayout.closeDrawers();

                if(id== R.id.home) {

                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                else if(id== R.id.browse) {
                    Toast.makeText(getApplicationContext(),"hello",Toast.LENGTH_LONG).show();
//                    Intent h = new Intent(getApplicationContext(), MainActivity.class);
//                    startActivity(h);
                }
                else if(id== R.id.search) {

                     Intent search = new Intent(getApplicationContext(), SearchPage.class);
                     search.putExtra("uid",uid);
                startActivity(search);
                }
                else if(id== R.id.Profile) {
                    Intent sendtopro=new Intent(getApplicationContext(), Profile.class);
                    sendtopro.putExtra("Ukey",UserId);
                    startActivity(sendtopro);
                }
                else if(id== R.id.addbkbtn) {

                    Intent adbk = new Intent(getApplicationContext(), Addbooks.class);
                    adbk.putExtra("UID","User");
                    startActivity(adbk);
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
        if (adapter != null)
            adapter.startListening();
        loading.setVisibility(View.VISIBLE);
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




