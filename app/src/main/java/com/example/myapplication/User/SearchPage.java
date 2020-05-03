package com.example.myapplication.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;
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
    RecyclerView recyclerView;
    DatabaseReference reference;
    ArrayList<String> BookNameList;
    ArrayList<String> AuthorNameList;
    ArrayList<String> PicList;
    ArrayList<String> PublisherList;
    ArrayList<String> LinkList;
    ArrayList<String> DescriptionList;
    FirebaseAnalytics mFirebaseAnalytics;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);
        searchbar = (EditText) findViewById(R.id.searchbar);
        reference = FirebaseDatabase.getInstance().getReference();
        reference.keepSynced(true);
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        BookNameList = new ArrayList<>();
        PublisherList = new ArrayList<>();
        AuthorNameList = new ArrayList<>();
        LinkList = new ArrayList<>();
        PicList = new ArrayList<>();
        DescriptionList=new ArrayList<>();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


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
                    PublisherList.clear();
                    DescriptionList.clear();

                }
            }

            private void setAdapter(final String searchedString) {
                reference.child("BookDB").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        BookNameList.clear();
                        AuthorNameList.clear();
                        PicList.clear();
                        PublisherList.clear();
                        DescriptionList.clear();

                        int counter=0;
                        for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                            String uid = snapshot.getKey();
                            String desc = snapshot.child("Desc").getValue(String.class);
                            String bookname = snapshot.child("bookname").getValue(String.class);
                            String author = snapshot.child("author").getValue(String.class);
                            String image = snapshot.child("image").getValue(String.class);
                            String publisher = snapshot.child("Publisher").getValue(String.class);


                            try {

                                if ((bookname.toLowerCase().contains(searchedString.toLowerCase())) || (author.toLowerCase().contains(searchedString.toLowerCase()))) {
                                    BookNameList.add(bookname);
                                    AuthorNameList.add(author);
                                    PublisherList.add(publisher);
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
                            SearchAdapter searchAdapter = new SearchAdapter(SearchPage.this, BookNameList, AuthorNameList, PicList, PublisherList,DescriptionList,LinkList);
                            recyclerView.setAdapter(searchAdapter);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });
    }
}
