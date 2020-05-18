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

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.User.Bookdeets;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import io.grpc.InternalWithLogId;

public class AdminBookdetails extends AdminSearch {
    EditText titles, authors, bookid, Imglnk,category;
    Bookdeets bookdeets;
    Button update, delete,addtotop,addtomain;
    ImageView imageView;
    String id="tt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminbooksdetails);

        imageView = (ImageView)findViewById(R.id.imgview);
        titles = (EditText) findViewById(R.id.bknames);
        category = (EditText) findViewById(R.id.category);
        authors = (EditText) findViewById(R.id.anames);
        Imglnk = (EditText) findViewById(R.id.imglinks);
        update = (Button) findViewById(R.id.updatebtn);
        addtotop = (Button) findViewById(R.id.AddToTopBooks);
        delete = (Button) findViewById(R.id.deltebtn);
        bookid = (EditText) findViewById(R.id.uid);
        addtomain = (Button) findViewById(R.id.AddtoMain);
        bookdeets = new Bookdeets();
        id = getIntent().getStringExtra("id");
        String key = getIntent().getStringExtra("key");


        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        final String mid = getIntent().getStringExtra("bookid");
        bookid.setText( mid);
//        bkid = mid;
        String mtitle = getIntent().getStringExtra("booknames");
        titles.setText(mtitle);
        final String mcategory = getIntent().getStringExtra("category");
        category.setText(mcategory);
        String mauthor = getIntent().getStringExtra("author_name");
        authors.setText(mauthor);
        String imgs = getIntent().getStringExtra("Image");
        Imglnk.setText(imgs);
        Picasso.get().load(imgs).into(imageView);


if(id!=null) {
    if (id.equals("bookdetails")) {

        databaseReference.child("BookDB").child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String bkid = dataSnapshot.child("id").getValue().toString();
                bookid.setText(bkid);
                String mtitle = dataSnapshot.child("bookname").getValue().toString();
                titles.setText(mtitle);
                String mauthor = dataSnapshot.child("author").getValue().toString();
                authors.setText(mauthor);
                String mcategory = dataSnapshot.child("category").getValue().toString();
                category.setText(mcategory);
                String imglnk = dataSnapshot.child("image").getValue().toString();
                Imglnk.setText(imglnk);
                Picasso.get().load(imglnk).into(imageView);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
      }
    else if (id.equals("tempbooks")) {
        if (key != null) {
            databaseReference.child("TempBookDB").child(key).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String bkid = dataSnapshot.child("id").getValue().toString();
                    bookid.setText(bkid);
                    String mtitle = dataSnapshot.child("bookname").getValue().toString();
                    titles.setText(mtitle);
                    String mauthor = dataSnapshot.child("author").getValue().toString();
                    authors.setText(mauthor);
                    String mcategory = dataSnapshot.child("category").getValue().toString();
                    category.setText(mcategory);
                    String imglnk = dataSnapshot.child("image").getValue().toString();
                    Imglnk.setText(imglnk);
                    Picasso.get().load(imglnk).into(imageView);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    } else {
        databaseReference.child("BookDB").child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String bkid = dataSnapshot.child("id").getValue().toString();
                bookid.setText(bkid);
                String mtitle = dataSnapshot.child("bookname").getValue().toString();
                titles.setText(mtitle);
                String mauthor = dataSnapshot.child("author").getValue().toString();
                authors.setText(mauthor);
                String mcategory = dataSnapshot.child("category").getValue().toString();
                category.setText(mcategory);
                String imglnk = dataSnapshot.child("image").getValue().toString();
                Imglnk.setText(imglnk);
                Picasso.get().load(imglnk).into(imageView);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}


        addtomain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Title = titles.getText().toString().trim();
                String Author = authors.getText().toString().trim();
                final String Uid = bookid.getText().toString().trim();
                String Img = Imglnk.getText().toString().trim();
                String ccategory = category.getText().toString().trim();

                HashMap<String, Object> map = new HashMap<>();
                map.put("bookname", Title);
                map.put("author", Author);
                map.put("id", Uid);
                map.put("image", Img);
                map.put("Category",ccategory);
                if (databaseReference.child("BookDB").child(Uid).child("id").equals(Uid)) {
                    Toast.makeText(AdminBookdetails.this, "Book already exits", Toast.LENGTH_SHORT).show();
                } else {

                    databaseReference.child("BookDB").child(Uid).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                databaseReference.child("TempBookDB").child(Uid).setValue(null);
                                Toast.makeText(AdminBookdetails.this, "Added to Main  Database Successfully", Toast.LENGTH_SHORT).show();


                            } else {
                                Toast.makeText(AdminBookdetails.this, "Task failed try again", Toast.LENGTH_SHORT).show();

                            }


                        }
                    });


                    if (TextUtils.isEmpty(Title) || TextUtils.isEmpty(Author)) {
                        titles.setError("Field cant be empty");
                    }
                }
            }


        });


            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String Title = titles.getText().toString().trim();
                    String Author = authors.getText().toString().trim();
                    String Uid = bookid.getText().toString().trim();
                    String Img = Imglnk.getText().toString().trim();
                    String ccategory = category.getText().toString().trim();


                    HashMap<String, Object> map = new HashMap<>();
                    map.put("bookname", Title);
                    map.put("author", Author);
                    map.put("id", Uid);
                    map.put("image", Img);
                    map.put("Category",ccategory);


                    databaseReference.child("BookDB").child(Uid).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(AdminBookdetails.this, "Update Successfull", Toast.LENGTH_SHORT).show();

                            }
                            else{
                                Toast.makeText(AdminBookdetails.this, "Update failed", Toast.LENGTH_SHORT).show();

                            }


                        }
                    });



                    if (TextUtils.isEmpty(Title) || TextUtils.isEmpty(Author)) {
                        titles.setError("Field cant be empty");
                    }
                }
            });
            addtotop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String Title = titles.getText().toString().trim();
                    String Author = authors.getText().toString().trim();
                    String Uid = bookid.getText().toString().trim();
                    String Img = Imglnk.getText().toString().trim();
                    String ccategory = category.getText().toString();




                    HashMap<String, Object> map = new HashMap<>();
                    map.put("bookname", Title);
                    map.put("author", Author);
                    map.put("id", Uid);
                    map.put("image", Img);
                    map.put("Category",ccategory);



                    if(databaseReference.child("TopBooksDB").equals(Uid)){
                        Toast.makeText(getApplicationContext(),"Book laready exists in Top Books database",Toast.LENGTH_LONG).show();
                    }
                    else {

                        databaseReference.child("TopbooksDB").child(Uid).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(AdminBookdetails.this, "Added to top books succesfully", Toast.LENGTH_SHORT).show();

                                }
                                else{
                                    Toast.makeText(AdminBookdetails.this, "Failed to add to top books", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
                    }

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
                    databaseReference.child("TopBooksDB").child(Uid).setValue(null);
                    databaseReference.child("TempBookDB").child(Uid).setValue(null);
                    Toast.makeText(getApplicationContext(), "Book deleted succesfully", Toast.LENGTH_SHORT).show();
                    Intent gbck=new Intent(getApplicationContext(),TempBooksLayout.class);
                    startActivity(gbck);
                }
            });
        }

    }


