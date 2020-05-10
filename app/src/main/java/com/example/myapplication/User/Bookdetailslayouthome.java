package com.example.myapplication.User;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.myapplication.R;
import com.example.myapplication.signin.login.Login;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class Bookdetailslayouthome extends SearchPage {


    TextView title, author, pub;
    ImageView image;
    View header;
    NavigationView navbar1;
    DrawerLayout drawerLayout1;
    ActionBarDrawerToggle mtoggle1;
    String uid,key;
    DatabaseReference databaseReference;
    RadioGroup radioGroup;
    RadioButton rread,rreading,rtoread;
    String bkname,authorname,bkid,bkimage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookdetailslayout);

        rread = (RadioButton) findViewById(R.id.radioread);
        rreading = (RadioButton) findViewById(R.id.radioreading);
        rtoread = (RadioButton) findViewById(R.id.radiotoread);
        image = (ImageView) findViewById(R.id.img);
        title = (TextView) findViewById(R.id.bkname);
        author = (TextView) findViewById(R.id.aname);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        navbar1 = (NavigationView) findViewById(R.id.drawer);
        navbar1.bringToFront();
        drawerLayout1 = (DrawerLayout) findViewById(R.id.drawerlay);
        header = navbar1.getHeaderView(0);
        mtoggle1 = new ActionBarDrawerToggle(this, drawerLayout1, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        drawerLayout1.addDrawerListener(mtoggle1);
        mtoggle1.syncState();
        key = getIntent().getStringExtra("key");
        uid=getIntent().getStringExtra("ID");
        Toast.makeText(this, ""+uid, Toast.LENGTH_SHORT).show();
//        String readingcheck=databaseReference.child("UserDB").child(uid).child("bookinfo").child("reading").child(key).getKey();
//        String readcheck=databaseReference.child("UserDB").child(uid).child("bookinfo").child("read").child(key).getKey();
//        String toreadcheck=databaseReference.child("UserDB").child(uid).child("bookinfo").child("toread").child(key).getKey();
////
//        if( readingcheck==key){
//       rreading.setChecked(true);
//        }
//        else if(readcheck==key){
//
//            rread.setChecked(true);
//        }
//        else if(toreadcheck==key){
//
//            rtoread.setChecked(true);
//        }


//     retriving  from search  page
         final String mtitle = getIntent().getStringExtra("booknames");
            title.setText("Book Name:"+mtitle);
            bkname=mtitle;
            final String mauthor = getIntent().getStringExtra("author_name");
            author.setText("By Author:"+mauthor);
            authorname=mauthor;
            final String mimgs = getIntent().getStringExtra("Image");
            Picasso.get().load(mimgs).into(image);
            bkimage=mimgs;



               if(key!=null) {
                   databaseReference.child("TopbooksDB").child(key).addValueEventListener(new ValueEventListener() {
                       @Override
                       public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                          final String mtitle = dataSnapshot.child("bookname").getValue().toString();
                          bkname=mtitle;
                           title.setText(mtitle);
                           String mauthor = dataSnapshot.child("author").getValue().toString();
                           authorname=mauthor;
                           author.setText("by " + mauthor);
                           String mimgs = dataSnapshot.child("image").getValue().toString();
                           Picasso.get().load(mimgs).into(image);
                           bkimage=mimgs;
                           String mid=dataSnapshot.child("id").getValue().toString();
                           bkid=mid;
                       }
                       @Override
                       public void onCancelled(@NonNull DatabaseError databaseError) {
                       }
                   });
               }


        rread.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(Bookdetailslayouthome.this, "read", Toast.LENGTH_SHORT).show();


                HashMap<String, Object> map = new HashMap<>();
                map.put("bookname", bkname);
                map.put("author", authorname);
                map.put("id", bkid);
                map.put("image", bkimage);
                databaseReference.child("UserDB").child(uid).child("bookinfo").child("read").child(bkid).setValue(map);
                databaseReference.child("UserDB").child(uid).child("bookinfo").child("reading").child(bkid).setValue(null);
                databaseReference.child("UserDB").child(uid).child("bookinfo").child("toread").child(bkid).setValue(null);


            }
        });
        rreading.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(Bookdetailslayouthome.this, "read", Toast.LENGTH_SHORT).show();


                HashMap<String, Object> map = new HashMap<>();
                map.put("bookname", bkname);
                map.put("author", authorname);
                map.put("id", bkid);
                map.put("image", bkimage);
                databaseReference.child("UserDB").child(uid).child("bookinfo").child("reading").child(bkid).setValue(map);
                databaseReference.child("UserDB").child(uid).child("bookinfo").child("read").child(bkid).setValue(null);
                databaseReference.child("UserDB").child(uid).child("bookinfo").child("toread").child(bkid).setValue(null);
                rreading.setChecked(true);


            }
        });

        rtoread.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(Bookdetailslayouthome.this, "read", Toast.LENGTH_SHORT).show();


                HashMap<String, Object> map = new HashMap<>();
                map.put("bookname", bkname);
                map.put("author", authorname);
                map.put("id", bkid);
                map.put("image", bkimage);
                databaseReference.child("UserDB").child(uid).child("bookinfo").child("toread").child(bkid).setValue(map);
                databaseReference.child("UserDB").child(uid).child("bookinfo").child("read").child(bkid).setValue(null);
                databaseReference.child("UserDB").child(uid).child("bookinfo").child("reading").child(bkid).setValue(null);


            }
        });
               navbar1.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    int id = item.getItemId();
                    drawerLayout1.closeDrawers();
                    if(id== R.id.home) {
                        Intent search = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(search);
                    }
                    else if(id== R.id.browse) {
                        Toast.makeText(getApplicationContext(),"hello",Toast.LENGTH_LONG).show();
                  }
                    else if(id== R.id.search) {
                        Intent search = new Intent(getApplicationContext(), SearchPage.class);
                        startActivity(search);
                    }
                    else if(id== R.id.Profile) {
                        Intent intent = new Intent(getApplicationContext(), Profile.class);
                        startActivity(intent);
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
                    drawerLayout1.closeDrawer(GravityCompat.START);
                    return true;
                }

            });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (mtoggle1.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout1.isDrawerOpen(GravityCompat.START)) {
            drawerLayout1.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}
