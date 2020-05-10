package com.example.myapplication.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Admin.Addbooks;
import com.example.myapplication.R;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class SearchPage extends AppCompatActivity {
    EditText searchbar;
    TextView addbks;
    RecyclerView recyclerView;
    DatabaseReference reference;
    ArrayList<String> BookNameList;
    ArrayList<String> AuthorNameList;
    ArrayList<String> PicList;
    ArrayList<String>IdList;
    FirebaseAnalytics mFirebaseAnalytics;
    ActionBarDrawerToggle mtoggle;
    DrawerLayout drawerLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);
        addbks=(TextView) findViewById(R.id.adbksearch);
        searchbar = (EditText) findViewById(R.id.searchbar);
        reference = FirebaseDatabase.getInstance().getReference();
        reference.keepSynced(true);
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mtoggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        BookNameList = new ArrayList<>();
        AuthorNameList = new ArrayList<>();
        PicList = new ArrayList<>();
        IdList=new ArrayList<>();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        addbks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gtabks=new Intent(getApplicationContext(), Addbooks.class);
                gtabks.putExtra("UID","User");
                startActivity(gtabks);

            }
        });



            searchbar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    setAdapter(s.toString());
                }
                else{
                    BookNameList.clear();
                    AuthorNameList.clear();
                    PicList.clear();
                    IdList.clear();

                }
            }


            private void setAdapter(final String searchedString) {
                reference.child("BookDB").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        BookNameList.clear();
                        AuthorNameList.clear();
                        PicList.clear();
                        IdList.clear();

                        int counter=0;
                        for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                            String uid = snapshot.getKey();
                            String desc = snapshot.child("Desc").getValue(String.class);
                            String bookname = snapshot.child("bookname").getValue(String.class);
                            String author = snapshot.child("author").getValue(String.class);
                            String image = snapshot.child("image").getValue(String.class);
                            String bookid = snapshot.child("id").getValue(String.class);


                            try {

                                if ((bookname.toLowerCase().contains(searchedString.toLowerCase())) || (author.toLowerCase().contains(searchedString.toLowerCase()))) {

                                    BookNameList.add(bookname);
                                    AuthorNameList.add(author);
                                    IdList.add(bookid);
                                    PicList.add(image);

                                    counter++;
                                }
                                if(BookNameList.isEmpty() && AuthorNameList.isEmpty())
                                {




//                                    Toast.makeText(getApplicationContext(),"empty",Toast.LENGTH_LONG).show();
                                }
                            }
                            catch (Exception e){
                            }
                            if(counter==15){
                                break;
                            }
                            SearchAdapter searchAdapter = new SearchAdapter(SearchPage.this, BookNameList, AuthorNameList, PicList, IdList);
                            recyclerView.setAdapter(searchAdapter);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent gob=new Intent(getApplicationContext(),MainActivity.class);
//        startActivity(gob);
    }
    }
