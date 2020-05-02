package com.example.myapplication.Admin;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.myapplication.R;
import com.example.myapplication.User.Bookdeets;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class AdminBookdetails extends AdminSearch {
    EditText titles, authors, pubs, bookid, Imglnk;
    Bookdeets bookdeets;
    Button update, delete;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageView = (ImageView) findViewById(R.id.imgview);
        setContentView(R.layout.activity_adminbooksdetails);
        titles = (EditText) findViewById(R.id.bknames);
        authors = (EditText) findViewById(R.id.anames);
        Imglnk = (EditText) findViewById(R.id.imglinks);
        update = (Button) findViewById(R.id.updatebtn);
        delete = (Button) findViewById(R.id.deltebtn);
        bookid = (EditText) findViewById(R.id.uid);
        bookdeets = new Bookdeets();
        String id = getIntent().getStringExtra("id");
        String key = getIntent().getStringExtra("key");


        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        String uid = getIntent().getStringExtra("uid");

        bookid.setText(uid, TextView.BufferType.EDITABLE);

        String Bname = getIntent().getStringExtra("booknamess");
        titles.setText(Bname, TextView.BufferType.EDITABLE);

        final String imglink = getIntent().getStringExtra("Image");
        Imglnk.setText(imglink, TextView.BufferType.EDITABLE);

        String Author = getIntent().getStringExtra("author_names");
        authors.setText(Author, TextView.BufferType.EDITABLE);
        {
            if (key != null) {
                databaseReference.child("TempBookDB").child(key).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String mtitle = dataSnapshot.child("bookname").getValue().toString();
                        titles.setText(mtitle);
                        String mauthor = dataSnapshot.child("author").getValue().toString();
                        authors.setText("by " + mauthor);
                        String imgs = dataSnapshot.child("image").getValue().toString();
                        Picasso.get().load(imgs).into(imageView);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }


            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String Title = titles.getText().toString().trim();
                    String Author = authors.getText().toString().trim();
                    String Uid = bookid.getText().toString().trim();
                    String Img = Imglnk.getText().toString().trim();

                    HashMap<String, Object> map = new HashMap<>();
                    map.put("bookname", Title);
                    map.put("author", Author);
                    map.put("id", Uid);
                    map.put("image", Img);

                    databaseReference.child("BooksDB").child(Uid).updateChildren(map);

                    if (TextUtils.isEmpty(Title) || TextUtils.isEmpty(Author)) {
                        titles.setError("Field cant be empty");
                    }
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String Uid = bookid.getText().toString().trim();
                    databaseReference.child("BooksDB").child(Uid).setValue(null);
                    Toast.makeText(getApplicationContext(), "Book deleted succesfully", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}

