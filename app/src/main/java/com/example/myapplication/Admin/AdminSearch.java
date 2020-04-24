package com.example.myapplication.Admin;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class AdminSearch extends AppCompatActivity {
    EditText searchbarr;
    RecyclerView recyclerVieww;
    DatabaseReference referencee;
    ArrayList<String> BookNameLists;
    ArrayList<String> AuthorNameLists;
    ArrayList<String> PicLists;
    ArrayList<String> PublisherLists;
    ArrayList<String> LinkLists;
    ArrayList<String> DescriptionLists;
    ArrayList<String>UidList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminsearch);
        searchbarr = (EditText) findViewById(R.id.searchbar);
        referencee = FirebaseDatabase.getInstance().getReference();
        referencee.keepSynced(true);
        recyclerVieww = (RecyclerView) findViewById(R.id.rv);
        recyclerVieww.setHasFixedSize(true);
        recyclerVieww.setLayoutManager(new LinearLayoutManager(this));
        recyclerVieww.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        BookNameLists = new ArrayList<>();
        PublisherLists = new ArrayList<>();
        AuthorNameLists = new ArrayList<>();
        LinkLists = new ArrayList<>();
        PicLists = new ArrayList<>();
        DescriptionLists=new ArrayList<>();
        UidList=new ArrayList<>();
        searchbarr.addTextChangedListener(new TextWatcher() {
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
                    BookNameLists.clear();
                    AuthorNameLists.clear();
                    PicLists.clear();
                    PublisherLists.clear();
                    DescriptionLists.clear();
                    UidList.clear();
                }
            }

            private void setAdapter(final String searchedString) {
                referencee.child("books").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        BookNameLists.clear();
                        AuthorNameLists.clear();
                        PicLists.clear();
                        PublisherLists.clear();
                        DescriptionLists.clear();
                        UidList.clear();

                        int counter=0;
                        for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                            String uid = snapshot.getKey();
                            String desc = snapshot.child("Desc").getValue(String.class);
                            String bookname = snapshot.child("bookname").getValue(String.class);
                            String author = snapshot.child("author").getValue(String.class);
                            String image = snapshot.child("image").getValue(String.class);
                            String publisher = snapshot.child("Publisher").getValue(String.class);
                            String link=snapshot.child("link").getValue(String.class);
                            String decscription=snapshot.child("Desc").getValue(String.class);

                            try {

                                if ((bookname.toLowerCase().contains(searchedString.toLowerCase())) || (author.toLowerCase().contains(searchedString.toLowerCase()))) {
                                    UidList.add(uid);
                                    BookNameLists.add(bookname);
                                    AuthorNameLists.add(author);
                                    PublisherLists.add(publisher);
                                    PicLists.add(image);
                                    DescriptionLists.add(decscription);
                                    LinkLists.add(link);
                                    counter++;
                                }
                                if(BookNameLists.isEmpty() && AuthorNameLists.isEmpty()){
                                    Toast.makeText(getApplicationContext(),"not found",Toast.LENGTH_LONG).show();
                                }
                            }
                            catch (Exception e){
                            }
                            if(counter==15){
                                break;
                            }
                            AdminSearchAdapter adminSearchAdapter = new AdminSearchAdapter(AdminSearch.this, BookNameLists, AuthorNameLists, PicLists, PublisherLists,DescriptionLists,LinkLists,UidList);
                            recyclerVieww.setAdapter(adminSearchAdapter);
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
