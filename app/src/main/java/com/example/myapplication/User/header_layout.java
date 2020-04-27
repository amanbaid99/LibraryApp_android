package com.example.myapplication.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.signin.login.Login;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class header_layout extends Login {

    TextView Uname,Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header_layout);
        DatabaseReference reff= FirebaseDatabase.getInstance().getReference().child("UserDB");

        Uname=(TextView) findViewById(R.id.unamedisplay);
        Email=(TextView) findViewById(R.id.emaildisplay);
        Uname.setText("test");
        Intent j=getIntent();
        String id=j.getStringExtra("ID");
                Toast.makeText(this, "Login Successfull"+id, Toast.LENGTH_SHORT).show();

        if(id!=null) {

            reff.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String mname = dataSnapshot.child("fullName").getValue().toString();
                    Uname.setText(mname);
                    String Memail = dataSnapshot.child("email").getValue().toString();
                    Email.setText(Memail);
//                        Picasso.get().load()
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }}
}
